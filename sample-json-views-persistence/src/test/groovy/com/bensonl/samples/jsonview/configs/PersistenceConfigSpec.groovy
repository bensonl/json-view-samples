package com.bensonl.samples.jsonview.configs

import com.bensonl.samples.jsonview.services.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * Created by bensonliu on 4/10/17.
 */
@EnableAutoConfiguration
@ContextConfiguration(classes = [PersistenceConfig.class])
class PersistenceConfigSpec extends Specification {

	@Autowired
	private PersonService personService

	def "validate context startup"() {
		when: "application context is initialized"
			true

		then: "beans are created"
			personService
	}
}
