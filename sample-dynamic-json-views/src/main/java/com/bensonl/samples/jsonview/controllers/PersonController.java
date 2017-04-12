package com.bensonl.samples.jsonview.controllers;

import com.bensonl.samples.jsonview.Person;
import com.bensonl.samples.jsonview.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bensonliu on 4/12/17.
 */
@RestController
@RequestMapping(path = "/person")
public class PersonController {

	@Autowired
	private PersonService personService;

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public Person getPerson(@PathVariable("id") Long id) {
		return personService.getPerson(id);
	}

}
