# sample-static-json-views

This project demonstrates the use of @JsonView annotations in a @RestController to statically control what attributes of an object is serialized to a client.

The sample-static-json-views project uses Spring Boot, JDK 1.8, and Spring Data JPA with an H2 in memory database.

Unit tests are written using Spock and integration tests are written using the Spring Boot Test framework.

## PersonController

A @RestController that exposes a few APIs to demonstrate how a static @JsonView annotation affects the JSON output.

* /person/{id} - is mapped without an @JsonView.
* /person/{id}/parents - is serialized with the PersonViews.WithParents view.  Person data is returned with basic data and the person's mother and father.
* /person/{id}/siblings - is left as an exercise.

## PersonControllerITTest

The PersonControllerITTest uses the Spring Boot test framework.  It starts the Spring application on a random port and uses the @MockBean annotation to inject a mock of the PersonEntityRepository.  The class also demonstrates the use of a TestRestTemplate.

## Exercises

1. implement the PersonController.getPersonWithSiblings(...) method and annotate it with the appropriate @JsonView annotation so that a Person is serialized with his basic data and his siblings (only).
2. implement a PersonController.getPersonWithFamily(...) method and annotate it with the appropriate @JsonView annotation so that a Person is serialized with his basid data, his parents, and his siblings.
3. adjust the @JsonView annotations to see the affects of the different views.