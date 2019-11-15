package com.developer.resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class DefaultControllerAdvice {

	@ModelAttribute("currentUser")
	User currentUser() {
		
		String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		
		User currentUser = new User();
		currentUser.setFirstName(username);
		currentUser.setLastName("Hello");
		
		return currentUser;
	}

//	@ExceptionHandler(Exception.class)
//	ModelAndView handleException(Exception ex) {
//		return errorView("An error occurred on the WebClient response -> [Status: " +
//				ex.getStatusCode() + "] " + ex.getStatusText());
//	}

	@ExceptionHandler(Exception.class)
	ModelAndView handleException(Exception ex) {
		return errorView("An error occurred: " + ex.getMessage());
	}

	private ModelAndView errorView(String errorMessage) {
		Map<String, Object> model = new HashMap<>();
		model.put("errorMessage", errorMessage);
		return new ModelAndView("error", model);
	}
}