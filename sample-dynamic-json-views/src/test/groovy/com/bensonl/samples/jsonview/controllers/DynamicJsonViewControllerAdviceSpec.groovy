package com.bensonl.samples.jsonview.controllers

import com.bensonl.samples.jsonview.PersonViews
import com.bensonl.samples.jsonview.config.JsonViewMappingsConfigurationProperty
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJacksonValue
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

/**
 * Created by bensonliu on 4/12/17.
 */
class DynamicJsonViewControllerAdviceSpec extends Specification {

	def jsonViewMappingConfigurationProperties = Mock(JsonViewMappingsConfigurationProperty)

	def advice = Spy(DynamicJsonViewControllerAdvice)

	def setup() {
		ReflectionTestUtils.setField(advice, "jsonViewMappings", jsonViewMappingConfigurationProperties)
	}

	def "validate beforeBodyWriteInternal method"() {

		MappingJacksonValue bodyContainer = Spy(MappingJacksonValue)

		def contentType = MediaType.APPLICATION_JSON
		def returnType = Mock(MethodParameter)
		def request = Mock(ServletServerHttpRequest)
		def response = Mock(ServerHttpResponse)
		def mockHttpRequest = Mock(HttpServletRequest)

		setup:
			jsonViewMappingConfigurationProperties.requestParameterName >> "view"

		when: "request does not contain view parameter"
			advice.beforeBodyWriteInternal(bodyContainer, contentType, returnType, request, response)

		then: "serializationView is set to null"
			1 * request.getServletRequest() >> mockHttpRequest
			1 * jsonViewMappingConfigurationProperties.findMapping(null)
			1 * bodyContainer.setSerializationView(null)


		when: "request view parameter is empty"
			advice.beforeBodyWriteInternal(bodyContainer, contentType, returnType, request, response)

		then: "serializationView is set to null"
			1 * request.getServletRequest() >> mockHttpRequest
			1 * mockHttpRequest.getParameter(_)
			1 * jsonViewMappingConfigurationProperties.findMapping(null)
			1 * bodyContainer.setSerializationView(null)

		when: "request view parameter is an undefined view"
			advice.beforeBodyWriteInternal(bodyContainer, contentType, returnType, request, response)

		then: "serializationView is set to null"
			1 * request.getServletRequest() >> mockHttpRequest
			1 * mockHttpRequest.getParameter(_) >> "undefinedView"
			1 * jsonViewMappingConfigurationProperties.findMapping(_)
			1 * bodyContainer.setSerializationView(null)

		when: "request view parameter is a valid view name"
			advice.beforeBodyWriteInternal(bodyContainer, contentType, returnType, request, response)

		then: "serializationView is set to returned value"
			1 * request.getServletRequest() >> mockHttpRequest
			1 * mockHttpRequest.getParameter(_) >> "parents"
			1 * jsonViewMappingConfigurationProperties.findMapping(_) >> {
				new JsonViewMappingsConfigurationProperty.JsonViewMapping(name: "parents",
				                                                          className: PersonViews.WithParents.class.name)
			}
			1 * bodyContainer.setSerializationView(PersonViews.WithParents.class)

	}
}
