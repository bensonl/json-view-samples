package com.bensonl.samples.jsonview;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

/**
 * Created by bensonliu on 4/5/17.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {

   private Long id;

   @JsonView(PersonViews.Default.class)
   private String first;
   @JsonView(PersonViews.Default.class)
   private String last;

   @JsonView(PersonViews.Default.class)
   private Gender gender;

   @JsonView(PersonViews.Parents.class)
   private Person father;
   @JsonView(PersonViews.Parents.class)
   private Person mother;

   @JsonView(PersonViews.Siblings.class)
   private List<Person> siblings;

   // TODO: Exercise - add children
}
