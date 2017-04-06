package com.bensonl.samples.jsonview.services

import com.bensonl.samples.jsonview.entities.PersonEntity
import com.bensonl.samples.jsonview.exceptions.PersonServiceException
import com.bensonl.samples.jsonview.mappers.PersonMapperImpl
import com.bensonl.samples.jsonview.repositories.PersonEntityRepository
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

/**
 * Created by bensonliu on 4/7/17.
 */
class PersonServiceImplSpec extends Specification {

	def repository = Mock(PersonEntityRepository)
	def personMapper = Spy(PersonMapperImpl)

	def service = Spy(PersonServiceImpl)

	def setup() {
		ReflectionTestUtils.setField(service, "personEntityRepository", repository)
		ReflectionTestUtils.setField(service, "personMapper", personMapper)
	}

	def "validate getPerson method"() {
		setup:
			def id
			def person
			PersonServiceException pse

		when: "id is null"
			person = service.getPerson(id)

		then: "exception is thrown"
			pse = thrown()

		when: "no person is found for id"
			id = 1000
			person = service.getPerson(id)

		then: "exception is thrown"
			1 * repository.findOne(_)
			pse = thrown()

		when: "person is found"
			person = service.getPerson(id)

		then: "person is returned"
			1 * repository.findOne(_) >> {
				new PersonEntity(id: id, first: "first", last: "last")
			}
			1 * personMapper.entityToDto(_)
			person
			person.id == id
	}

}
