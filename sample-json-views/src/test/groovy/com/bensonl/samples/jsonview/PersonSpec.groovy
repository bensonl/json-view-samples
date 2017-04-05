package com.bensonl.samples.jsonview

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import spock.lang.Shared
import spock.lang.Specification
import sun.security.provider.SHA

/**
 * Created by bensonliu on 4/5/17.
 */
@groovy.util.logging.Slf4j
class PersonSpec extends Specification {

    def objectMapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)

    @Shared
    def father = new Person(first: "Dad", last: "Doe", gender: Gender.MALE)
    @Shared
    def mother = new Person(first: "Mom", last: "Doe", gender: Gender.FEMALE)

    @Shared
    def brother = new Person(first: "Brother", last: "Doe", gender: Gender.MALE, father: father, mother: mother)
    @Shared
    def sister = new Person(first: "Sister", last: "Doe", gender: Gender.FEMALE, father: father, mother: mother)

    @Shared
    def p = new Person(first: "John", last: "Doe", gender: Gender.MALE, father: father, mother: mother, siblings: [brother, sister ])

    def "test how people are deserialized without using @JsonView" () {
        setup:
            def result

        when: "me is deserialized without a @JsonView"
            result = objectMapper.writeValueAsString(p)

        then: "Person p is deserialized with all fields"
            result
            log.debug "p=[${result}]"

    }


}
