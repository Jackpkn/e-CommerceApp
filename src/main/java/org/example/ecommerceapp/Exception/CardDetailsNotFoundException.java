package org.example.ecommerceapp.Exception;

public class CardDetailsNotFoundException extends RuntimeException {
	
	public CardDetailsNotFoundException() {
	}
	
	public CardDetailsNotFoundException(String message) {
		super(message);
	}
}
