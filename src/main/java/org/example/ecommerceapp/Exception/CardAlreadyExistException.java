package org.example.ecommerceapp.Exception;

public class CardAlreadyExistException extends RuntimeException{
	
	public CardAlreadyExistException() {
	}
	
	public CardAlreadyExistException(String message) {
		super(message);
	}
}
