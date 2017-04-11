package com.bensonl.samples.jsonview.controllers;

import com.bensonl.samples.jsonview.Person;
import com.bensonl.samples.jsonview.PersonViews;
import com.bensonl.samples.jsonview.services.PersonService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bensonliu on 4/5/17.
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

	@JsonView(PersonViews.WithParents.class)
	@RequestMapping(method = RequestMethod.GET, path = "/{id}/parents")
	public Person getPersonWithParents(@PathVariable("id") Long id) {
		return personService.getPerson(id);
	}

	// TODO: exercise - annotate this method so that the Person is returned with Siblings
	@RequestMapping(method = RequestMethod.GET, path = "/{id}/siblings")
	public Person getPersonWithSiblings(@PathVariable("id") Long id) {
		throw new UnsupportedOperationException("This method is not implemented yet.");
	}

	// TODO: exercise - create a method so that the Person is returned with his children


	// TODO: exercise - create a method so that the Person is returned with all of his family data
	// including his parents, his siblings, and any children
}
