package com.bensonl.samples.jsonview.services;

import com.bensonl.samples.jsonview.Person;
import com.bensonl.samples.jsonview.exceptions.PersonServiceException;
import com.bensonl.samples.jsonview.mappers.PersonMapper;
import com.bensonl.samples.jsonview.repositories.PersonEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Created by bensonliu on 4/6/17.
 */
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonEntityRepository personEntityRepository;

	@Autowired
	private PersonMapper personMapper;

	@Override
	public Person getPerson(Long id) {
		return Optional.ofNullable(personEntityRepository.findOne(id))
		               .map(personMapper::entityToDto)
		               .orElseThrow(() -> new PersonServiceException("Failed to return person for id=" + id));
	}

}
