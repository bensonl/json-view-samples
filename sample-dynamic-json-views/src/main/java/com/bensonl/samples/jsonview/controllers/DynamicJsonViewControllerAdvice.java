package com.bensonl.samples.jsonview.controllers;

import com.bensonl.samples.jsonview.config.JsonViewMappingsConfigurationProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.util.Optional;

/**
 * Created by bensonliu on 4/12/17.
 */
@ControllerAdvice
public class DynamicJsonViewControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice {

	@Autowired
	private JsonViewMappingsConfigurationProperty jsonViewMappings;

	@Override
	protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer,
	                                       MediaType contentType, MethodParameter returnType,
	                                       ServerHttpRequest request, ServerHttpResponse response) {

		bodyContainer.setSerializationView(resolveSerializationView(request));

	}

	private Class<?> resolveSerializationView(ServerHttpRequest request) {

		String requestedViewName = parseViewNameFromRequest(request);
		return Optional.ofNullable(jsonViewMappings.findMapping(requestedViewName))
		               .map(JsonViewMappingsConfigurationProperty.JsonViewMapping::getSerializationViewClass)
		               .orElse(null);

	}

	private String parseViewNameFromRequest(ServerHttpRequest request) {
		return ((ServletServerHttpRequest)request).getServletRequest()
		                                          .getParameter(jsonViewMappings.getRequestParameterName());

	}


}
