package com.bensonl.samples.jsonview.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * Created by bensonliu on 4/12/17.
 */
@ContextConfiguration(classes = [TestConfig.class, JsonViewMappingsConfigurationProperty.class])
class JsonViewMappingsConfigurationPropertySpec extends Specification {

	@Autowired
	private JsonViewMappingsConfigurationProperty jsonViewMappings;

	def "validate context startup"() {

		when:
			true

		then: "jsonViewMappings are parsed and loaded"
			jsonViewMappings
			jsonViewMappings.mappings.empty == false
			jsonViewMappings.findMapping("parents")

	}
}
