package com.hectorlopezfernandez.toilet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public final class PaginationDataArgumentResolver implements HandlerMethodArgumentResolver {

	private static final Logger logger = LoggerFactory.getLogger(PaginationDataArgumentResolver.class);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return PaginationData.class.equals(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		logger.debug("Resolving a PageRequest object");
		int page = 1;
		String pageParameter = webRequest.getParameter(PaginationData.PAGE_PARAMETER_NAME);
		if (pageParameter != null) {
			try {
				page = Integer.parseInt(pageParameter);
			} catch (NumberFormatException nfe) {
				// nothing to do
			}
		}
		return new PaginationData().withPage(page);
	}

}
