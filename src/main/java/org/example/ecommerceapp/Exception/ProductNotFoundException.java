package org.example.ecommerceapp.Exception;

public class ProductNotFoundException extends RuntimeException{

	public ProductNotFoundException() {
		
	}
	
	public ProductNotFoundException(String message) {
		super(message);
	}
}
