package com.bensonl.samples.jsonview.config

import com.bensonl.samples.jsonview.PersonViews
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
	}

	def "validate findMappings method"() {
		setup:
			def viewName
			def mapping

		when: "viewName is null"
			mapping = jsonViewMappings.findMapping(viewName)

		then: "no mapping is returned"
			mapping == null

		when: "mapping does not exist for viewName"
			viewName = "unmapped-view"
			mapping = jsonViewMappings.findMapping(viewName)

		then: "no mapping is returned"
			mapping == null

		when: "mapping exists"
			viewName = "default"
			mapping = jsonViewMappings.findMapping(viewName)

		then: "mapping is returned"
			mapping.name == viewName
			mapping.className == PersonViews.Default.class.name

	}
}
