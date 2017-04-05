package com.bensonl.samples.jsonview;

/**
 * Created by bensonliu on 4/5/17.
 */
public class PersonViews {

    static class Default {}

    interface Parents {}

    interface Siblings {}

    interface Children {}

    static class Family extends Default implements Parents, Siblings, Children {}
}
