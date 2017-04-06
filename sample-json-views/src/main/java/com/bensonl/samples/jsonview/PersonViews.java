package com.bensonl.samples.jsonview;

/**
 * Created by bensonliu on 4/5/17.
 */
public class PersonViews {

    public static class Default {}

    public interface Parents {}

    public interface Siblings {}

    public static class WithParents extends Default implements Parents {}

    public static class WithSiblings extends Default implements Siblings {}

    // TODO: Exercise - implement a view for WithChildren
    // TODO: Exercise - implement a WithFamily view that returns the person's information, his parents, siblings, and children

}
