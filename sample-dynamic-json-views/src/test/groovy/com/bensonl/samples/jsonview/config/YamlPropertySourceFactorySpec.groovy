package com.bensonl.samples.jsonview.config

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.support.EncodedResource
import spock.lang.Specification

/**
 * Created by bensonliu on 4/12/17.
 */
class YamlPropertySourceFactorySpec extends Specification {

	def factory = new YamlPropertySourceFactory()

	def "validate createProperySource method"() {
		setup:
			def name = "propertySource"
			def resource = Mock(EncodedResource)
			def propertySource

		when: "the resource is invalid"
			propertySource = factory.createPropertySource(name, resource)

		then: "Exception is thrown"
			resource.getResource() >> { throw new IOException() }
			Exception e = thrown()

		when: "resource can be loaded"
			resource = new EncodedResource(new ClassPathResource("json-view-mappings.yml"))
			propertySource = factory.createPropertySource(name, resource)

		then: "property source is successfully created"
			propertySource
	}
}
