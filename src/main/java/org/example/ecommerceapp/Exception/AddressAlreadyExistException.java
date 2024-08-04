package org.example.ecommerceapp.Exception;

public class AddressAlreadyExistException extends RuntimeException{
	
	public AddressAlreadyExistException() {
	}
	
	public AddressAlreadyExistException(String message) {
		super(message);
	}
}
