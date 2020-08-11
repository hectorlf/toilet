package com.hectorlopezfernandez.blog;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CustomErrorViewResolver implements ErrorViewResolver {

	private static final Logger logger = LoggerFactory.getLogger(CustomErrorViewResolver.class);

	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
		ModelAndView response;
		if (status == HttpStatus.NOT_FOUND) {
			response = new ModelAndView("web/pages/404-error");
		} else if (status.is5xxServerError()) {
			response = new ModelAndView("web/pages/500-error");
		} else {
			logger.warn("Arrived at a 418 error, WTF! Actual status: {}, path used to get here: {}", status.value(), request.getPathInfo());
			response = new ModelAndView("web/pages/418-error");
		}
		return response;
	}

}
