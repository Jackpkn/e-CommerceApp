package org.example.ecommerceapp.Exception;

public class CustomerAlreadyExistsException extends RuntimeException{
	
	public CustomerAlreadyExistsException() {
	}
	
	public CustomerAlreadyExistsException(String message) {
		super(message);
	}
}
