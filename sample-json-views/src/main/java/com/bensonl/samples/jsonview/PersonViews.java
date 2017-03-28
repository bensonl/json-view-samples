package com.bensonl.samples.jsonview;

/**
 * Created by bensonliu on 4/5/17.
 */
public class PersonViews {

    static class Default {}

    interface Parents {}

    interface Siblings {}

    static class WithParents extends Default implements Parents {}

    static class WithSiblings extends Default implements Siblings {}

    // TODO: Exercise - implement a view for WithChildren
    // TODO: Exercise - implement a WithFamily view that returns the person's information, his parents, siblings, and children

}
