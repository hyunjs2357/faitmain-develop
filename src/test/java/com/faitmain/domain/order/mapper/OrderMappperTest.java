package com.faitmain.domain.order.mapper;

import com.faitmain.domain.order.domain.OrderProduct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith( SpringExtension.class )
@SpringBootTest
@Slf4j
class OrderMappperTest{

    @Autowired
    private OrderMapper orderMapper;


    /* 상품 정보 (주문처리) */
    @Test
    public void getOrdetInfoTest(){

        OrderProduct orderInfo = orderMapper.selectOrderProduct( 10001 );
        log.info( "orderInfo = {}" , orderInfo );

    }

    /* 주문 테이블 등록 */
/*
    @Test
    public void enrollOrderTest(){

        Order insertOrder = new Order();
        List<OrderOne> orderProductList = new ArrayList<>();

        OrderOne orderOne = new OrderOne();
        orderOne.setOrderOneNumber(0);
        orderOne.setOrderNumber("");
        orderOne.setProductNumber(0);
        orderOne.setProductOrderCount(0);
        orderOne.setProductPrice(0);
        orderOne.setProductDiscount(0.0D);
        orderOne.setRewardPoint(0);
        orderOne.setSalePrice(0);
        orderOne.setTotalPrice(0);
        orderOne.setTotalRewardPoint(0);

        insertOrder.setOrderProductList( orderProductList );

        insertOrder.setOrderNumber(0);
        insertOrder.setProductNumber(0);
        insertOrder.setBuyerId("");
        insertOrder.setStoreId("");
        insertOrder.setOrderStatus("");
        insertOrder.setOrderProductList(Lists.newArrayList());
        insertOrder.setDeliveryCharge(0);
        insertOrder.setOrderDate(new Date(new java.util.Date().getTime()));
        insertOrder.setReceiverName("");
        insertOrder.setReceiverPhone("");
        insertOrder.setReceiverAddress1("");
        insertOrder.setReceiverAddress2("");
        insertOrder.setReceiverAddress3("");
        insertOrder.setUsingPoint(0);
        insertOrder.setOrderSalePrice(0);
        insertOrder.setOrderRewardPoint(0);
        insertOrder.setOrderFinalSalePrice(0);

        orderMapper.insertOrder( insertOrder );
    }
*/

    /*    *//* vam_itemorder 테이블 등록 *//*
    @Test
    public void enrollOrderItemTest() {

        OrderItemDTO oid = new OrderItemDTO();

        oid.setOrderId("2021_test1");
        oid.setProductNumber(61);
        oid.setBookCount(1);
        oid.setBookPrice(70000);
        oid.setBookDiscount(0.1);

        oid.initSaleTotal();

        mapper.enrollOrderItem(oid);

    }

    *//* 회원 돈, 포인트 정보 변경 *//*
    @Test
    public void deductMoneyTest() {

        MemberVO member = new MemberVO();

        member.setMemberId("admin");
        member.setMoney(500000);
        member.setPoint(10000);

        mapper.deductMoney(member);
    }

    *//* 상품 재고 변경 *//*
    @Test
    public void deductStockTest() {
        BookVO book = new BookVO();

        book.setProductNumber(61);
        book.setBookStock(77);

        mapper.updateStock(book);
    }*/


















   /* @Test
    @DisplayName( "주문등록 테스트" )
    public void addOrderTest(){

        System.out.println( "addOrderTest Start" );

        Order insertOrder = new Order();

//        insertOrder.setProductNumber( 10001 );
//        insertOrder.setBuyerId( "user01@naver.com" );
//        insertOrder.setStoreId( "store03@naver.com" );
//        insertOrder.setOrderBundleNumber( new Timestamp( new java.util.Date().getTime() ) );
//        insertOrder.setOrderDate( new Timestamp( new java.util.Date().getTime() ) );
//        insertOrder.setOrderQuantity( 1 );
//        insertOrder.setOrderStatus( "주문접수" );
//        insertOrder.setReceiverName( "홍길동" );
//        insertOrder.setReceiverPhone( "01039372812" );
//        insertOrder.setReceiverAddress( "어쩌구 어쩔동 어쩔아파트" );
//        insertOrder.setReceiverRequest( "어쩔티비 저쩔티비" );
//        insertOrder.setDeliveryTrackingNumber( 1 );
//        insertOrder.setDeliveryCompanyCode( "ABCDE" );
//        insertOrder.setPaymentOption( "카카오페이" );
//        insertOrder.setTotalPaymentPrice( 12345 );
//        insertOrder.setRewardPoint( 1 );
//        insertOrder.setUsingPoint( 0 );
//        insertOrder.setOrderClaimRequestDate( new Date( new java.util.Date().getTime() ) );
//        insertOrder.setOrderClaimResponseDate( new Date( new java.util.Date().getTime() ) );
//        insertOrder.setOrderClaimReason( 0 );


        System.out.println( "result = " + orderMapper.insertOrder( insertOrder ) );
        System.out.println( "orderNumber = " + insertOrder.getOrderNumber() );

        insertOrder = orderMapper.selectOrder( insertOrder.getOrderNumber() );
        System.out.println( "insertOrder = " + insertOrder );

//        assertThat( insertOrder.getProductNumber() ).isEqualTo( 10001 );
//        assertThat( insertOrder.getBuyerId() ).isEqualTo( "user01@naver.com" );
//        assertThat( insertOrder.getStoreId() ).isEqualTo( "store03@naver.com" );
////        assertThat( insertOrder.getOrderBundleNumber() ).isEqualTo( new Timestamp( new java.util.Date().getTime() ) );
////        assertThat( insertOrder.getOrderDate() ).isEqualTo( new Timestamp( new java.util.Date().getTime() ) );
//        assertThat( insertOrder.getOrderQuantity() ).isEqualTo( 1 );
//        assertThat( insertOrder.getOrderStatus() ).isEqualTo( "주문접수" );
//        assertThat( insertOrder.getReceiverName() ).isEqualTo( "홍길동" );
//        assertThat( insertOrder.getReceiverPhone() ).isEqualTo( "01039372812" );
//        assertThat( insertOrder.getReceiverAddress() ).isEqualTo( "어쩌구 어쩔동 어쩔아파트" );
//        assertThat( insertOrder.getReceiverRequest() ).isEqualTo( "어쩔티비 저쩔티비" );
//        assertThat( insertOrder.getDeliveryTrackingNumber() ).isEqualTo( 1 );
//        assertThat( insertOrder.getDeliveryCompanyCode() ).isEqualTo( "ABCDE" );
//        assertThat( insertOrder.getPaymentOption() ).isEqualTo( "카카오페이" );
//        assertThat( insertOrder.getTotalPaymentPrice() ).isEqualTo( 12345 );
//        assertThat( insertOrder.getRewardPoint() ).isEqualTo( 1 );
//        assertThat( insertOrder.getUsingPoint() ).isEqualTo( 0 );
////        assertThat( insertOrder.getOrderClaimRequestDate() ).isEqualTo( "2022-06-02" );
////        assertThat( insertOrder.getOrderClaimResponseDate() ).isEqualTo( "2022-06-02" );
//        assertThat( insertOrder.getOrderClaimReason() ).isEqualTo( 0 );
    }

    @Test
    @DisplayName( "주문상태 업데이트 테스트" )
    public void updateOrderTest(){

        System.out.println( "updateOrderTest Start" );

        //given
        Order insertOrder = new Order();

        insertOrder.setOrderNumber( 10001 );
        insertOrder.setOrderStatus( "주문접수" );
        insertOrder.setReceiverName( "홍길동" );
        insertOrder.setReceiverPhone( "01039372812" );
//        insertOrder.setReceiverAddress( "어쩌구 어쩔동 어쩔아파트" );
//        insertOrder.setReceiverRequest( "어쩔티비 저쩔티비" );
//        insertOrder.setDeliveryTrackingNumber( 1 );
//        insertOrder.setDeliveryCompanyCode( "ABCDE" );
//        insertOrder.setOrderClaimReason( 0 );

        //when
        System.out.println( "result = " + orderMapper.updateOrder( insertOrder ) );
        System.out.println( "orderNumber = " + insertOrder.getOrderNumber() );

        insertOrder = orderMapper.selectOrder( insertOrder.getOrderNumber() );
        System.out.println( "insertOrder = " + insertOrder );

        //then
        assertThat( insertOrder.getOrderStatus() ).isEqualTo( "주문접수" );
        assertThat( insertOrder.getReceiverName() ).isEqualTo( "홍길동" );
        assertThat( insertOrder.getReceiverPhone() ).isEqualTo( "01039372812" );
//        assertThat( insertOrder.getReceiverAddress() ).isEqualTo( "어쩌구 어쩔동 어쩔아파트" );
//        assertThat( insertOrder.getReceiverRequest() ).isEqualTo( "어쩔티비 저쩔티비" );
//        assertThat( insertOrder.getDeliveryTrackingNumber() ).isEqualTo( 1 );
//        assertThat( insertOrder.getDeliveryCompanyCode() ).isEqualTo( "ABCDE" );
//        assertThat( insertOrder.getOrderClaimReason() ).isEqualTo( 0 );
    }

    @Test
    @DisplayName( " 주문조회 테스트" )
    public void getOrderTest(){

        //given
        int orderNumber = 10001;

        //when
        Order insertOrder = orderMapper.selectOrder( 10001 );
        System.out.println( "insertOrder = " + insertOrder );

        //then
        assertThat( insertOrder.getOrderNumber() ).isEqualTo( 10001 );
    }

    @Test
    @DisplayName( "전체 주문조회 테스트" )
    public void getOrderListTest(){

        List< Order > orderList = orderMapper.selectOrderList();
        for ( Order insertOrder : orderList ) {
            System.out.println( "insertOrder = " + insertOrder );
        }

    }*/


}
