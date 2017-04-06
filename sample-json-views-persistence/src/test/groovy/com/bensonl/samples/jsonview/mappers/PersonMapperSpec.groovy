package com.bensonl.samples.jsonview.mappers

import com.bensonl.samples.jsonview.Gender
import com.bensonl.samples.jsonview.entities.PersonEntity
import spock.lang.Specification

/**
 * Created by bensonliu on 4/7/17.
 */
class PersonMapperSpec extends Specification {

	def mapper = Spy(PersonMapperImpl)

	def "validate mapWithRelationship method"() {

		setup:
			def entity
			def person

		when: "entity is null"
			person = mapper.mapWithoutRelationship(entity)

		then: "null is returned"
			person == null

		when: "entity is not null"
			entity = new PersonEntity(id: 1000,
			                          first: "first", last: "last", gender: Gender.MALE,
			                          father: new PersonEntity(id: 1001),
			                          mother: new PersonEntity(id: 1002))
			person = mapper.mapWithoutRelationship(entity)

		then: "only basic attributes are mapped"
			person.id == 1000
			person.first == "first"
			person.last == "last"
			person.gender == Gender.MALE
			person.father == null
			person.mother == null
	}

	def "validate mapSiblings method"() {
		setup:
			def siblings
			def result

		when: "there are no siblings"
			result = mapper.mapSiblings(siblings)

		then: "result is null"
			result == null

		when: "siblings is an empty set"
			siblings = [] as Set
			result = mapper.mapSiblings(siblings)

		then: "result is null"
			result == null

		when: "there are siblings"
			siblings = [new PersonEntity(id: 100),
			            new PersonEntity(id: 101),
			            new PersonEntity(id: 102)] as Set
			result = mapper.mapSiblings(siblings)

		then: "siblings are mapped and returned"
			result.size() == 3

	}
}
