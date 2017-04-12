package com.bensonl.samples.jsonview.config

import com.bensonl.samples.jsonview.PersonViews
import spock.lang.Specification

/**
 * Created by bensonliu on 4/12/17.
 */
class JsonViewMappingSpec extends Specification {

	def "validate getSerializationView from mapping"() {
		setup:
			def mapping
			def viewClass

		when: "serialization view exists"
			mapping = new JsonViewMappingsConfigurationProperty.JsonViewMapping(name: "unknown",
			                                                                    className: "DoesNotExist")
			viewClass = mapping.getSerializationViewClass()


		then: "null is returned without exception"
			viewClass == null
			noExceptionThrown()


		when: "serialization view exists"
			mapping = new JsonViewMappingsConfigurationProperty.JsonViewMapping(name: "parents",
			                                                                    className: PersonViews.WithParents.class.name)
			viewClass = mapping.getSerializationViewClass()

		then: "class is returned"
			viewClass == PersonViews.WithParents.class

	}
}
