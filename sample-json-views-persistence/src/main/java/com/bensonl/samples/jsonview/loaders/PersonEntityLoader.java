package com.bensonl.samples.jsonview.loaders;

import com.bensonl.samples.jsonview.Gender;
import com.bensonl.samples.jsonview.entities.PersonEntity;
import com.bensonl.samples.jsonview.repositories.PersonEntityRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bensonliu on 4/6/17.
 */
@Component
public class PersonEntityLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private PersonEntityRepository personEntityRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		String lastName = "Doe";

		PersonEntity father = new PersonEntityBuilder("Papa", lastName, Gender.MALE).build();
		PersonEntity mother = new PersonEntityBuilder("Mama", lastName, Gender.FEMALE).build();

		PersonEntity sister = new PersonEntityBuilder("Jane", lastName, Gender.FEMALE)
				.withFather(father)
				.withMother(mother)
				.build();

		PersonEntity johnDoe = new PersonEntityBuilder("John", lastName, Gender.MALE)
				.withFather(father)
				.withMother(mother)
				.withSibling(sister)
				.build();

		sister.addSibling(johnDoe);

		personEntityRepository.save(Arrays.asList(father, mother, sister, johnDoe));

	}

	public static final class PersonEntityBuilder  {

		private String first;
		private String last;

		private Gender gender;

		private PersonEntity father;
		private PersonEntity mother;

		private Set<PersonEntity> siblings;

		public PersonEntityBuilder(String first, String last, Gender gender) {
			Validate.isTrue(StringUtils.isNotBlank(first));
			Validate.isTrue(StringUtils.isNotBlank(last));
			Validate.notNull(gender);

			this.first = first;
			this.last = last;
			this.gender = gender;
		}

		public PersonEntityBuilder withFather(PersonEntity father) {
			this.father = father;
			return this;
		}

		public PersonEntityBuilder withMother(PersonEntity mother) {
			this.mother = mother;
			return this;
		}

		public PersonEntityBuilder withSibling(PersonEntity sibling) {
			if (this.siblings == null) {
				this.siblings = new HashSet<>();
			}
			this.siblings.add(sibling);
			return this;
		}

		public PersonEntity build() {
			PersonEntity entity = new PersonEntity();

			entity.setFirst(first);
			entity.setLast(last);
			entity.setGender(gender);
			entity.setFather(father);
			entity.setMother(mother);
			entity.setSiblings(siblings);

			return entity;
		}

	}


}
