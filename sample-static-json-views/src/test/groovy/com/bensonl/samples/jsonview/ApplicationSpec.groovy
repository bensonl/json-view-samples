package com.bensonl.samples.jsonview

import com.bensonl.samples.jsonview.repositories.PersonEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by bensonliu on 4/6/17.
 */
class ApplicationSpec extends Specification {

	def "validate application startup"() {
		when: "application starts"
			Application.main()

		then: "context is started"
			true
	}

}
