package com.bensonl.samples.jsonview.exceptions;

/**
 * Created by bensonliu on 4/6/17.
 */
public class PersonServiceException extends RuntimeException {

	public PersonServiceException() {
	}

	public PersonServiceException(String message) {
		super(message);
	}

	public PersonServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersonServiceException(Throwable cause) {
		super(cause);
	}

}
