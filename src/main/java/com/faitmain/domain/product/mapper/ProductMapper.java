package com.faitmain.domain.product.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.faitmain.domain.product.domain.Product;
import com.faitmain.global.common.Image;

@Mapper
public interface ProductMapper {
	
	//INSERT - 상품 등록
	public void addProduct(Product product) throws Exception;
	
	//INSERT - 상품 추가 이미지 등록
	public void addProductImage(Image image) throws Exception;
	
	//SELECT - 마지막 상품번호 조회
	public int getProductNumber() throws Exception;
	
	//SELECT - 상품 상세 조회
	public Product getProduct(int productNumber) throws Exception;
	
	//SELECT - 상품 추가 이미지 조회
	public List<Image> getImage(int productNumber) throws Exception;
	
	//SELECT - 상품 옵션 조회
	public List<Product> getProductOption(int productGroupNumber) throws Exception;
	
	//SELECT - 상품 주문 내역 조회
	public int getOrderCount(int productNumber) throws Exception;
	
	//SELECT - 판매자가 리스트 조회시
	public List<Product> getProductListForStore(Map<String, Object> map) throws Exception;
	public int getTotalCountForStore(Map<String, Object> map) throws Exception;
	
	//SELECT - 상품 목록 조회
	public List<Product> getProductList(Map<String, Object> map) throws Exception;
	
	//SELECT - 상품 목록 조회
	public List<Product> getProductListByStoreId(String storeId) throws Exception;
	
	//SELECT - 상품 이름 목록 조회
	public List<String> getProductNameList(String keyword) throws Exception;
	
	//SELECT - 상품 count
	public int getTotalCount(Map<String, Object> map) throws Exception;
	
	//UPDATE - 상품 수정
	public void updateProduct(Product product) throws Exception;
	
	public void updateProductOption(Product product) throws Exception;
	
	//UPDATE - 상품 추가 이미지 수정
	public void updateProductImage(Image image) throws Exception;
	
	//UPDATE - 상품 상태 수정
	public void updateProductStatus(Product product) throws Exception;
		
	//DELETE - 상품 삭제
	public int deleteProduct(int productNumber) throws Exception;
	
	public void deleteProductOption(int productNumber) throws Exception;
	
	//DELETE - 상품 추가 이미지 삭제
	public void deleteProductImage(int imageNumber) throws Exception;
}