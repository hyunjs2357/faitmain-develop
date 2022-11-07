package com.faitmain.global.util;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.faitmain.domain.customer.constant.Method;

@Controller
public class UiUtils {
//Customer
	public String showMessageWithRedirect(@RequestParam(value = "message", required = false) String message,
											@RequestParam(value = "redirectUri", required = false) String redirectUri,
											@RequestParam(value = "method", required = false) Method method,
											@RequestParam(value = "map", required = false) Map<String, Object> params, Model model) {
		
		 System.out.println("=======showMessageWithRedirect===시작 ==");

		 System.out.println("여기에 왔니");

		
		model.addAttribute("message", message);
		model.addAttribute("redirectUri", redirectUri);
		model.addAttribute("method", method);
		model.addAttribute("params", params);
		
		System.out.println("message"+message);
		System.out.println("redirectUri"+redirectUri);
		System.out.println("method"+method);
		System.out.println("params"+params);
		 System.out.println("=======showMessageWithRedirect===끝 ==");
		
		return "utils/message-redirect";
	}
}
