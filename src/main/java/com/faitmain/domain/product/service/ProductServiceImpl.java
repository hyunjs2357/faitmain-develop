package com.faitmain.domain.product.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.faitmain.domain.product.domain.Product;
import com.faitmain.domain.product.mapper.InquiryMapper;
import com.faitmain.domain.product.mapper.ProductMapper;
import com.faitmain.domain.product.mapper.ReviewMapper;
import com.faitmain.global.common.Image;
import com.faitmain.global.common.Search;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("productServiceImpl")
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

	@Value("${upload-path}")
	private String fileStorageLocation;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ReviewMapper reviewMapper;

	@Autowired
	private InquiryMapper inquiryMapper;

	public ProductServiceImpl(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	@Override
	public void addProduct(Product product, MultipartHttpServletRequest mRequest) throws Exception {

		MultipartFile mainFile = mRequest.getFile("mainImage");
		List<MultipartFile> subFile = mRequest.getFiles("subImage");

		System.out.println("fileStorageLocation : " + fileStorageLocation);

		if (!mainFile.isEmpty()) {
			System.out.println("mainFile");
			String fileName = storeFile(mainFile);
			product.setProductMainImage(fileName);
		}

		productMapper.addProduct(product);
		System.out.println("groupNumber : " + product.getProductGroupNumber());

		if (!subFile.isEmpty()) {
			if (subFile.size() > 1) {
				System.out.println("subFile");
				Image image = new Image();
				image.setImageClassificationNumber(product.getProductGroupNumber());

				for (MultipartFile mf : subFile) {

					String fileName = storeFile(mf);
					image.setImageName(fileName);
					productMapper.addProductImage(image);

				}
			}
		}

		if (product.getProductOptions() != null) {
			for (Product option : product.getProductOptions()) {
				option.setCategoryCode(product.getCategoryCode());
				option.setProductPrice(product.getProductPrice());
				option.setProductMainImage(product.getProductMainImage());
				option.setProductGroupNumber(product.getProductGroupNumber());
				option.setStore(product.getStore());
				productMapper.addProduct(option);
			}
		}
//		for(int i=0; i<product.getProductOptions().size(); i++) {
//			product.getProductOptions().get(i).setProductGroupNumber(product.getProductGroupNumber());
//			productMapper.addProduct(product.getProductOptions().get(i));
//		}

	}

	@Override
	public void addProductImage(Image image) throws Exception {
		productMapper.addProductImage(image);
	}

	@Override
	public Product getProduct(int productNumber) throws Exception {
		
		// 상품 정보 조회
		Product product = productMapper.getProduct(productNumber);
		
		// 상품에 속한 옵션 상품 정보 조회
		product.setProductExtraImage(productMapper.getImage(productNumber));
		product.setProductOptions(productMapper.getProductOption(productNumber));
		
		// 상품 관련 리뷰 조회
		Search search = new Search();
		search.setSearchCondition("productGroupNumber");
		search.setSearchKeyword(product.getProductNumber() + "");
		search.setCurrentPage(1); //페이지 나중에 처리
		search.setPageSize(10);
		product.setReviewList(reviewMapper.getReviewList(search));
		product.setReviewCount(reviewMapper.getTotalCount(search)); //리뷰 개수
		log.info("review List = {}", product.getReviewList());

		// 상품 관련 문의 조회
		search.setSearchCondition("productNumber");
		product.setInquiryList(inquiryMapper.getInquiryList(search));
		product.setInquiryCount(inquiryMapper.getTotalCount(search)); //문의 개수
//		log.info("inquiry List = {}", product.getInquiryList());

//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("mainProduct", product);
//		map.put("productOptions", productOptions);

		return product;
	}

	@Override
	public Product getLiveProduct(int productNumber) throws Exception {
		Product product = productMapper.getProduct(productNumber);
		product.setProductExtraImage(productMapper.getImage(productNumber));
		product.setProductOptions(productMapper.getProductOption(productNumber));
		return product;
	}

	@Override
	public Map<String, Object> getProductList(Map<String, Object> map) throws Exception {
		
//		log.info("serviceImpl");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int totalCount;
		List<Product> list;
		
		if(map.get("forStore") != null) {
			
			list = productMapper.getProductListForStore(map);			
			totalCount = productMapper.getTotalCountForStore(map);						
			
		}else {
			
			list = productMapper.getProductList(map);			
			totalCount = productMapper.getTotalCount(map);
			
		}
		
//		log.info("list : {}", list);
//		log.info("totalCount : {}", totalCount);
		
		resultMap.put("list", list);
		resultMap.put("totalCount", new Integer(totalCount));
		/**/
		return resultMap;
	}

	@Override
	public Map<String, Object> getProductListByStoreId(String storeId) throws Exception {
		List<Product> list = productMapper.getProductListByStoreId(storeId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list", list);
		return resultMap;
	}

	@Override
	public void updateProduct(Product product, MultipartHttpServletRequest mRequest) throws Exception {

		MultipartFile mainFile = mRequest.getFile("mainImage");
		List<MultipartFile> subFile = mRequest.getFiles("subImage");

		System.out.println("fileStorageLocation : " + fileStorageLocation);

		if (!mainFile.isEmpty()) {
			System.out.println("mainFile");
			String fileName = storeFile(mainFile);
			product.setProductMainImage(fileName);
		}

		if (!subFile.isEmpty()) {
			if (subFile.size() > 1) {
				System.out.println("subFile");
				Image image = new Image();
				image.setImageClassificationNumber(product.getProductNumber());

				for (MultipartFile mf : subFile) {

					String fileName = storeFile(mf);
					image.setImageName(fileName);
					productMapper.addProductImage(image);

				}
			}
		}

		if (product.getProductOptions() != null) {
			for (Product option : product.getProductOptions()) {
				option.setCategoryCode(product.getCategoryCode());
				option.setProductPrice(product.getProductPrice());
				option.setProductMainImage(product.getProductMainImage());
				option.setProductGroupNumber(product.getProductNumber());
				option.setStore(product.getStore());
				productMapper.addProduct(option);
			}
		}

		productMapper.updateProduct(product);

	}

	@Override
	public void updateProductOption(Product product) throws Exception {
		productMapper.updateProductOption(product);

	}

	@Override
	public void updateProductImage(Image image) throws Exception {
		productMapper.updateProductImage(image);

	}

	@Override
	public void updateProductStatus(Product product) throws Exception {
		productMapper.updateProductStatus(product);

	}

	@Override
	public int deleteProduct(int productNumber) throws Exception {
		
		int result;
		
		if((productMapper.getOrderCount(productNumber)) == 0) {
		 	result = productMapper.deleteProduct(productNumber);			
		}else {
			result = 0;
		}
		
		return result;

	}

	@Override
	public void deleteProductOption(int productNumber) throws Exception {
		productMapper.deleteProductOption(productNumber);

	}

	@Override
	public void deleteProductImage(int imageNumber) throws Exception {
		productMapper.deleteProductImage(imageNumber);
	}

	@Override
	public int getProductQuantity(int productNumber) throws Exception {
		Product product = productMapper.getProduct(productNumber);
		return product.getProductQuantity();
	}

	public String storeFile(MultipartFile file) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss_");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String timeStamp = sdf.format(timestamp);

		String originalFileName = file.getOriginalFilename(); // 오리지날 파일명
		String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 파일 확장자

//		System.out.println("fileStorageLocation : " + fileStorageLocation);
		String fileName = timeStamp + UUID.randomUUID() + extension;

		Path targetLocation = (Paths.get(fileStorageLocation).toAbsolutePath().normalize()).resolve(fileName);
		System.out.println("targetLocation : " + targetLocation);
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//		FileCopyUtils.copy(file.getBytes(), new File(targetLocation.toString(), fileName));
//		File files = new File(targetLocation.toString());

//		FileCopyUtils.copy(file.getBytes(), new File(fileStorageLocation, fileName));

//		file.transferTo(files);
		return fileName;
	}

	@Override
	public List<String> getProductNameList(String keyword) throws Exception {
		return productMapper.getProductNameList(keyword);
	}

	/*
	@Override
	public int getOrderCount(int productNumber) throws Exception {
		return productMapper.getOrderCount(productNumber);
	}
	*/
}