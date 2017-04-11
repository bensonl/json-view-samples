#sample-json-views
This project is both a demonstration of the new *@JsonView* feature of the **FasterXML/Jackson** project and a tool to use for learning how to use and apply @JsonViews to your projects.

## Person

The **Person** class has some basic attributes such as an *id*, a *first* and *last* name, and a gender.  

A Person also has relationships to other Persons such as a *father*, a *mother*, and *siblings*.

Annotating the Person class with  @JsonView annotations allows developers to use the same Java class while having better control over what data is returned to the client as JSON.

## PersonViews

The **PersonViews** class defines a the views used to annotate the Person class with @JsonView annotations.

The PersonViews class defines the following views:

* The **Default** view - returns the Person with his basic attributes.
* The **WithParents** view - builds on the Default view and adds the Person's father and mother.
* The **WithSiblings** view - includes the Person's brothers and sisters.

## PersonViewsSpec

The **PersonViewsSpec** is a **Spock** specification which demonstrates the affects of deserializing a Person with one of the defined PersonViews.

## Exercises

The following are exercises that you can complete to explore and learn more about using @JsonView annotations in your next project:

1. Try deserializing a Person using the Parents view.
	* Compare this to when a Person is deserialized using the Withparents view.
2. Add children relationship to the Person class and define a WithChildren that includes a person's basic attributes and his children.
3. Define a WithFamily view that includes a person's basic attributes, his parents, his siblings, and his children.
4. Add grandparents to a Person
	* what kind of views would you add now?