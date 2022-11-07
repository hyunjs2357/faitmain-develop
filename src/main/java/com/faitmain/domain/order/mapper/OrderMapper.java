package com.faitmain.domain.order.mapper;

import com.faitmain.domain.order.domain.Order;
import com.faitmain.domain.order.domain.OrderPageProduct;
import com.faitmain.domain.order.domain.OrderProduct;
import com.faitmain.domain.product.domain.Product;
import com.faitmain.domain.user.domain.User;
import com.faitmain.global.common.Paging;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface OrderMapper{

    /* SELECT */

    /* 주문자 주소 정보 */
    User selectBuyer( String id );

    /* 주문 상품 정보 (주문 페이지) */
    OrderPageProduct selectOrderPageProduct(  int productNumber );

    /* 주문 상품 정보 (주문 처리) */
    OrderProduct selectOrderProduct( int productNumber );

    List<Order> selectBuyerOrders( String buyerId );



    /* INSERT */

    /* 주문 테이블 등록 */
    int insertOrder( Order order );

    /* 주문 상품 테이블 등록 */
    int insertOrderProduct( OrderProduct orderProduct );



    /* DELETE */

    /* 주문 취소 */
    int deleteOrder( int orderNumber );

    /* 주문 상품 정보 (주문취소) */
    List<OrderProduct> selectOrderProductList( int orderNumber );

    /* 주문 정보 (주문취소) */
    Order selectOrder( int orderNumber );




    /* UPDATE */

    /* 주문 금액 차감 */
    int updatePoint( User user );

    /* 주문 재고 차감 */
    int updateStock( Product product );





    /* 주문 현황 */
    /* 주문 상품 리스트 */
    List<Order> selectOrderList( Paging paging );

    /* 주문 총 개수 */
    int selectOrderTotal( Paging paging );






}
