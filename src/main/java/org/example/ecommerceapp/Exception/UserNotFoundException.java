package org.example.ecommerceapp.Exception;

public class UserNotFoundException extends RuntimeException {
	
	public UserNotFoundException() {
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}
}
