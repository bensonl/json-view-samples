package com.bensonl.samples.jsonview;

import com.bensonl.samples.jsonview.entities.PersonEntity;
import com.bensonl.samples.jsonview.repositories.PersonEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by bensonliu on 4/6/17.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

	@Autowired
	private PersonEntityRepository personEntityRepository;

	@Test
	public void applicationShouldStart() {
	}

	@Test
	@Transactional
	public void whenApplicationStarted_ThenPeopleAreLoaded() {
		// given the application has started
		//
		// When an attempt is made to search for John Doe
		PersonEntity person = personEntityRepository.findOne(4L);

		// Then John Doe is found and returned
		assertNotNull(person);
		assertNotNull(person.getFather());
		assertNotNull(person.getMother());
		assertTrue(person.getSiblings().isEmpty() == false);

	}
}
