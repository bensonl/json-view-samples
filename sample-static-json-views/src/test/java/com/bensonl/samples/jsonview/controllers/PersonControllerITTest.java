package com.bensonl.samples.jsonview.controllers;

import com.bensonl.samples.jsonview.Gender;
import com.bensonl.samples.jsonview.Person;
import com.bensonl.samples.jsonview.entities.PersonEntity;
import com.bensonl.samples.jsonview.loaders.PersonEntityLoader;
import com.bensonl.samples.jsonview.repositories.PersonEntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;

/**
 * Created by bensonliu on 4/10/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerITTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private PersonEntityRepository personEntityRepository;

	@Test
	public void applicationShouldStart() {
	}

	@Test()
	public void whenIdIsNull_thenPathIsNotFound() {

		given(personEntityRepository.findOne(anyLong()))
				.willReturn(null);

		URI uri = UriComponentsBuilder.fromPath("/person/").build().toUri();
		ResponseEntity<Person> response = restTemplate.getForEntity(uri, Person.class);

		assertNotNull(response);
		assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND);

	}

	@Test()
	public void whenPersonDoesNotExist_thenExceptionIsThrown() {

		given(personEntityRepository.findOne(anyLong()))
				.willReturn(null);

		URI uri = UriComponentsBuilder.fromPath("/person").pathSegment("-1").build().toUri();
		ResponseEntity<Person> response = restTemplate.getForEntity(uri, Person.class);

		assertNotNull(response);
		assertTrue(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Test
	public void whenPersonExists_thenPersonIsReturnedUsingNoJsonView() {
		given(personEntityRepository.findOne(anyLong()))
				.willReturn(createPersonWithFamily());

		URI uri = UriComponentsBuilder.fromPath("/person").pathSegment("3").build().toUri();
		ResponseEntity<Person> response = restTemplate.getForEntity(uri, Person.class);

		assertNotNull(response);
		assertTrue(response.getStatusCode() == HttpStatus.OK);

		Person p = response.getBody();
		assertNotNull(p);
		// assert all basic data is returned
		assertTrue("John".equals(p.getFirst()));
		assertTrue("Doe".equals(p.getLast()));
		assertTrue(Gender.MALE == p.getGender());

		// assert relationship data is returned
		assertNotNull(p.getFather());
		assertTrue("Papa".equals(p.getFather().getFirst()));
		assertNotNull(p.getMother());
		assertTrue("Mama".equals(p.getMother().getFirst()));
		assertNotNull(p.getSiblings());
		assertTrue(p.getSiblings().size() == 1);
	}

	@Test
	public void whenPersonExistsAndRetrievedWithParents_thenPersonIsReturnedWithParents() {
		given(personEntityRepository.findOne(anyLong()))
				.willReturn(createPersonWithFamily());

		URI uri = UriComponentsBuilder.fromPath("/person")
		                              .pathSegment("3")
		                              .pathSegment("/parents")
		                              .build().toUri();

		ResponseEntity<Person> response = restTemplate.getForEntity(uri, Person.class);

		assertNotNull(response);
		assertTrue(response.getStatusCode() == HttpStatus.OK);

		Person p = response.getBody();
		assertNotNull(p);
		// assert all basic data is returned
		assertTrue("John".equals(p.getFirst()));
		assertTrue("Doe".equals(p.getLast()));
		assertTrue(Gender.MALE == p.getGender());

		// assert relationship data is returned
		assertNotNull(p.getFather());
		assertTrue("Papa".equals(p.getFather().getFirst()));
		assertNotNull(p.getMother());
		assertTrue("Mama".equals(p.getMother().getFirst()));

		// assert that sibilings were not returned
		assertNull(p.getSiblings());

	}

	// TODO: Exercise - fix this test after implementing the controller method
	@Test()
	public void whenPersonRetrievedWithSiblings_thenPersonIsReturnedWithSiblings() {

		URI uri = UriComponentsBuilder.fromPath("/person")
		                              .pathSegment("3")
		                              .pathSegment("/siblings")
		                              .build().toUri();

		ResponseEntity<Person> response = restTemplate.getForEntity(uri, Person.class);

		assertNotNull(response);
		assertTrue(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);

	}

	private PersonEntity createPersonWithFamily() {
		String lastName = "Doe";

		PersonEntity father = new PersonEntityLoader.PersonEntityBuilder("Papa", lastName, Gender.MALE).build();
		PersonEntity mother = new PersonEntityLoader.PersonEntityBuilder("Mama", lastName, Gender.FEMALE).build();

		PersonEntity brother =
				new PersonEntityLoader.PersonEntityBuilder("Brother", lastName, Gender.MALE)
						.withFather(father)
						.withMother(mother)
						.build();

		PersonEntity john =
				new PersonEntityLoader.PersonEntityBuilder("John", lastName, Gender.MALE)
						.withFather(father)
						.withMother(mother)
						.withSibling(brother)
						.build();

		brother.addSibling(john);

		return john;
	}

}
