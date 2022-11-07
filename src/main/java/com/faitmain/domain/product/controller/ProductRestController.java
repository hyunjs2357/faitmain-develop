package com.faitmain.domain.product.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.faitmain.domain.product.domain.Product;
import com.faitmain.domain.product.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/product/*")
@Slf4j
public class ProductRestController {
	
	@Value("${upload-path}")
	private String fileStorageLocation;
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

//	/product/json/uploadSummernoteImageFile
	@PostMapping(value = "json/uploadSummernoteImageFile", produces = "application/json")
	@ResponseBody
	public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
		
		System.out.println("uploadSummernoteImageFile");
		
		JSONObject jsonObject = new JSONObject();
		
		String fileRoot = "C:\\summernote_image\\";	//저장될 파일 경로
				
		String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
		
        // 랜덤 UUID+확장자로 저장될 savedFileName
        String savedFileName = UUID.randomUUID() + extension;	
		
        File targetFile = new File(fileRoot + savedFileName);
		
		try {
			InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
//            jsonObject.addProperty("url", "/summernoteImage/"+fileName);
//            jsonObject.addProperty("responseCode", "good");
            jsonObject.put("url", "/summernoteImage/" + savedFileName);
            jsonObject.put("responseCode", "success");
		}catch(IOException e) {
			FileUtils.deleteQuietly(targetFile);	// 실패시 저장된 파일 삭제
//            jsonObject.addProperty("responseCode", "fail");
            e.printStackTrace();
		}
		return jsonObject.toJSONString();
	}
	
	@PostMapping("json/updateProductOption")
	public String updateProductOption(@RequestBody Product productOption) throws Exception{
		System.out.println("updateProductOption : " + productOption.getProductName());
		
		JSONObject jsonObject = new JSONObject();
		
		System.out.println("productQuantity : " + productOption.getProductQuantity());
		if(productOption.getProductQuantity() > 0) {
			productOption.setProductStatus("01");			
		}else {
			productOption.setProductStatus("03");
		}
		productService.updateProductOption(productOption);
		
		jsonObject.put("responseCode", "success");
		
		return jsonObject.toJSONString();
	}
	
	@GetMapping("json/deleteProductOption/{productOptionNumber}")
	public String deleteProductOption(@PathVariable int productOptionNumber) throws Exception{
		
		JSONObject jsonObject = new JSONObject();
		productService.deleteProductOption(productOptionNumber);
		jsonObject.put("responseCode", "success");
		
		return jsonObject.toJSONString();
	}
	
	@GetMapping("json/deleteProductExtraImage/{imageNumber}")
	public String deleteProductExtraImage(@PathVariable int imageNumber) throws Exception{
		
		JSONObject jsonObject = new JSONObject();
		productService.deleteProductImage(imageNumber);
		jsonObject.put("responseCode", "success");
		
		return jsonObject.toJSONString();
	}
	
	@GetMapping("json/deleteProduct/{productNumber}")
	public String deleteProductCheck(@PathVariable int productNumber) throws Exception{
		
		JSONObject jsonObject = new JSONObject();
		int result = productService.deleteProduct(productNumber);
		
		System.out.println("상품 삭제 : " + result);
		
		if(result > 0) {
			jsonObject.put("responseCode", "success");
		}else {
			jsonObject.put("responseCode", "fail");
		}
		
		
		return jsonObject.toJSONString();
	}
	
	@GetMapping("json/getProductNameList/{keyword}")
	public List<String> getProductNameList(@PathVariable String keyword) throws Exception{
		
		System.out.println("/product/json/getProductNameList");
		System.out.println("keyword : " + keyword);
		
		List<String> productNames = productService.getProductNameList(keyword);
		System.out.println("productNames : " + productNames);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("productNames", productNames);
		
		return productNames;
	}
}