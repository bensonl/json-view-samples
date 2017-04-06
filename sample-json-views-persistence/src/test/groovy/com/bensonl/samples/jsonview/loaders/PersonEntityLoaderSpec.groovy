package com.bensonl.samples.jsonview.loaders

import com.bensonl.samples.jsonview.Gender
import com.bensonl.samples.jsonview.entities.PersonEntity
import com.bensonl.samples.jsonview.repositories.PersonEntityRepository
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

/**
 * Created by bensonliu on 4/10/17.
 */
class PersonEntityLoaderSpec extends Specification {


	def "validate building entities with PersonEntityBuilder "() {

		setup:
			def entity
			def father = Mock(PersonEntity)
			def mother = Mock(PersonEntity)
			def brother = Mock(PersonEntity)
			def sister = Mock(PersonEntity)
			Exception e

		when: "entity is built with invalid basic data"
			entity = new PersonEntityLoader.PersonEntityBuilder(null, null, null).build()

		then: "exception is thrown"
			e = thrown()

		when:
			entity = new PersonEntityLoader.PersonEntityBuilder("John", "", null).build()

		then: "exception is thrown"
			e = thrown()

		when:
			entity = new PersonEntityLoader.PersonEntityBuilder("John", "doe", null).build()

		then: "exception is thrown"
			e = thrown()

		when: "entity is built using valid basic data"
			entity = new PersonEntityLoader.PersonEntityBuilder("John", "Doe", Gender.MALE).build()

		then: "PersonEntity is returned"
			entity.first == "John"
			entity.last == "Doe"
			entity.gender == Gender.MALE
			entity.father == null
			entity.mother == null
			entity.siblings == null

		when: "entity is built with mother/father relationships"
			entity = new PersonEntityLoader.PersonEntityBuilder("John", "Doe", Gender.MALE)
					.withFather(father)
					.withMother(mother)
					.withSibling(brother)
					.withSibling(sister)
					.build()

		then: "entity is created with mother/father relationships"
			entity.father == father
			entity.mother == mother
			entity.siblings.size() == 2

	}

	def "validate loading of entities on application startup"() {
		setup:
			def repository = Mock(PersonEntityRepository)
			def loader = new PersonEntityLoader()
			ReflectionTestUtils.setField(loader, "personEntityRepository", repository)
			def event = Mock(ContextRefreshedEvent)

		when: "validate setup"
			loader.onApplicationEvent(event)

		then: "personEntityRepository is called to save the family"
			1 * repository.save(_) >> { args ->
				assert args[0] instanceof Collection<PersonEntityRepository>
				assert args[0].size() == 4
			}

	}
}
