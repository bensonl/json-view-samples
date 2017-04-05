package com.bensonl.samples.jsonview

import com.fasterxml.jackson.databind.MapperFeature
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
                                         .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)

    @Shared
    def father = new Person(id: 1, first: "Dad", last: "Doe", gender: Gender.MALE)
    @Shared
    def mother = new Person(id: 2, first: "Mom", last: "Doe", gender: Gender.FEMALE)

    @Shared
    def brother = new Person(id: 3, first: "Brother", last: "Doe", gender: Gender.MALE, father: father, mother: mother)
    @Shared
    def sister = new Person(id: 4, first: "Sister", last: "Doe", gender: Gender.FEMALE, father: father, mother: mother)

    @Shared
    def p = new Person(id: 5, first: "John", last: "Doe", gender: Gender.MALE, father: father, mother: mother, siblings: [brother, sister ])

    def "how a Person is deserialized without an @JsonView"() {
        setup:
            def result

        when: "me is deserialized without a @JsonView"
            result = objectMapper.writeValueAsString(p)

        then: "Person p is deserialized with all fields"
            result
            log.debug "p=[${result}]"

    }

    def "how a Person is deserialized using the PersonViews.Default @JsonView"() {
        setup:
            def result

        when: "a person is deserialized using the default object mapper settings"
            result = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)
                                       .writerWithView(PersonViews.Default.class)
                                       .writeValueAsString(p)

        then: "deserialized result includes fields that are not annotated with an @JsonView"
            result
            log.debug "p=[${result}]"

        when: "the objectMapper has DEFAULT_VIEW_INCLUSION is disabled"
            result = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT,true)
                                       .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
                                       .writerWithView(PersonViews.Default.class)
                                       .writeValueAsString(p)

        then: "deserialied result includes only the PersonViews.Default fields"
            result
            log.debug "p=[${result}]"
    }


    def "example of how other @JsonViews look" () {
        setup:
            def result

        when: "Person is deserialized wtih Parents"
            result = objectMapper.writerWithView(PersonViews.WithParents.class)
                                 .writeValueAsString(p)

        then: "result looks as follows"
            log.debug "person with parents=[${result}]"

    }
}
