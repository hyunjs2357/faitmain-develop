package com.faitmain.domain.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faitmain.domain.cart.domain.Cart;
import com.faitmain.domain.cart.mapper.CartMapper;
import com.faitmain.domain.order.domain.Order;
import com.faitmain.domain.order.domain.OrderCancel;
import com.faitmain.domain.order.domain.OrderPageProduct;
import com.faitmain.domain.order.domain.OrderProduct;
import com.faitmain.domain.order.mapper.OrderMapper;
import com.faitmain.domain.product.domain.Product;
import com.faitmain.domain.product.mapper.ProductMapper;
import com.faitmain.domain.user.domain.User;
import com.faitmain.global.common.Paging;
import com.faitmain.global.util.log.LogTrace;
import com.faitmain.global.util.log.TraceId;
import com.faitmain.global.util.log.TraceStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderMapper orderMapper;
	@Autowired
	ProductMapper productMapper;

	@Autowired
	CartMapper cartMapper;

	@Autowired
	private LogTrace trace;

	/* 주문자 주소 정보 */
	@Override
	public User getBuyer(TraceId traceId, String buyerId) {

		TraceStatus status = null;
		try {
			status = trace.beginSync(traceId, "OrderService.getBuyer()");
			User buyer = orderMapper.selectBuyer(buyerId);
			trace.end(status);
			return buyer;
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}

	/* 주문정보 */
	@Override
	public List<OrderPageProduct> getOrderPageProductList(TraceId traceId,
			List<OrderPageProduct> orderPageProductList) {

		TraceStatus status = null;
		try {
			status = trace.beginSync(traceId, "OrderService.getOrderPageProductList()"); // 저장 로직
			List<OrderPageProduct> oppList = new ArrayList<>();
			for (OrderPageProduct orderPageProduct : orderPageProductList) {
				OrderPageProduct opp = orderMapper.selectOrderPageProduct(orderPageProduct.getProductNumber());
				opp.setProductOrderCount(orderPageProduct.getProductOrderCount());
				opp.setProductMainImage(orderPageProduct.getProductMainImage());
				opp.initSaleTotal();
				oppList.add(opp);
			}
			sleep(1000);
			trace.end(status);
			return oppList;
		} catch (Exception e) {
			trace.exception(status, e);
			throw e;
		}
	}

	/* 주문 */
	@Override
	@Transactional
	public void addOrder(Order order) throws Exception {

		TraceStatus status = null;

		log.info("/* 주문 서비스로직 시작 */");

		/* 회원정보 */
		User user = orderMapper.selectBuyer(order.getBuyerId());
		log.info("/* 회원정보 */");
		log.info("user = {}", user);

		/* 주문정보 */
		List<OrderProduct> orderProductList = new ArrayList<>();

		for (OrderProduct orderProduct : order.getOrderProductList()) {

			OrderProduct op = orderMapper.selectOrderProduct(orderProduct.getProductNumber());

			/* 주문번호 */
			op.setOrderNumber(order.getOrderNumber());

			/* 수량세팅 */
			op.setProductOrderCount(orderProduct.getProductOrderCount());

			/* 기본정보 세팅 */
			op.initSaleTotal();

			/* LIST 객체 추가 */
			orderProductList.add(op);
			log.info("op = {}", op);
		}

		log.info("orderProductList = {}", orderProductList);

		/* ORDER 세팅 */
		order.setOrderProductList(orderProductList);
		order.getOrderPriceInfo();

		log.info("order = {}", order);

		/* DB 넣기 */
		try {
			/* ORDER 등록 */
			log.info("/* ORDER 등록 */");
			orderMapper.insertOrder(order);

			/* ORDER PRODUCT 등록 */
			log.info("/* ORDER PRODUCT 등록 */");
			for (OrderProduct orderProduct : order.getOrderProductList()) {
				orderMapper.insertOrderProduct(orderProduct);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* 포인트 변동 적용 */
		int calTotalPoint = user.getTotalPoint();
		calTotalPoint = calTotalPoint - order.getUsingPoint() + order.getOrderRewardPoint();
		user.setTotalPoint(calTotalPoint);

		/* 포인트 DB 적용 */
		orderMapper.updatePoint(user);

		log.info("/* 재고 변동 적용 */");
		for (OrderProduct orderProduct : order.getOrderProductList()) {
			/* 변동 재고 값 구하기 */
			Product product = productMapper.getProduct(orderProduct.getProductNumber());
			product.setProductQuantity(product.getProductQuantity() - orderProduct.getProductOrderCount());
			/* 변동 값 DB 적용 */
			orderMapper.updateStock(product);
		}

		log.info("/* 장바구니 제거 */");
		for (OrderProduct orderProduct : order.getOrderProductList()) {
			Cart cart = new Cart();
			cart.setBuyerId(order.getBuyerId());
			cart.setProductNumber(orderProduct.getProductNumber());

			cartMapper.deleteOrderCart(cart);
		}

	}

	/* 주문 취소 */
	@Override
	@Transactional
	public void cancelOrder(OrderCancel orderCancel) throws Exception {

		TraceStatus status = null;
		log.info("orderCancel = {}", orderCancel); /* 주문 & 주문상품 객체 */

		/* 회원 */
		User user = orderMapper.selectBuyer((orderMapper.selectOrder(orderCancel.getOrderNumber())).getBuyerId());
		log.info("user = {}", user);

		/* 주문상품 */
		List<OrderProduct> orderProductList = orderMapper.selectOrderProductList(orderCancel.getOrderNumber());
		for (OrderProduct orderProduct : orderProductList) {
			orderProduct.initSaleTotal();
		}

		/* 주문 */
		Order order = orderMapper.selectOrder(orderCancel.getOrderNumber());
		order.setOrderProductList(orderProductList);
		order.getOrderPriceInfo();

		/* 주문상품 취소 DB */
		log.info("deleteOrder 실행 전");
		orderMapper.deleteOrder(order.getOrderNumber());
		log.info("deleteOrder 실행 후");

		/* 포인트 & 재고 변환 */

		/* 포인트 */
		int calTotalPoint = user.getTotalPoint();
		calTotalPoint = calTotalPoint - order.getUsingPoint() + order.getOrderRewardPoint();
		user.setTotalPoint(calTotalPoint);
		log.info("user = {}", user);

		/* DB 적용 */
		orderMapper.updatePoint(user);
		log.info("user = {}", user);

		/* 재고 */
		for (OrderProduct orderProduct : order.getOrderProductList()) {
			Product product = productMapper.getProduct(orderProduct.getProductNumber());
			product.setProductQuantity(product.getProductQuantity() + orderProduct.getProductOrderCount());
			orderMapper.updateStock(product);
		}
	}

	/* 주문 상품 리스트 */
	@Override
	public List<Order> getOrderList(Paging paging) {
		return orderMapper.selectOrderList(paging);
	}

	/* 주문 총 개수 */
	@Override
	public int getOrderTotal(Paging paging) {
		return orderMapper.selectOrderTotal(paging);
	}

	@Override
	public List<Order> getMyOrders(String buyerId) {
		return orderMapper.selectBuyerOrders(buyerId);
	}

	@Override
	public Order paymentCompleteOrderInfoByDB(Order order) {

		order = orderMapper.selectOrder(order.getOrderNumber());
		order.setOrderProductList(orderMapper.selectOrderProductList(order.getOrderNumber()));

		return order;

	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
