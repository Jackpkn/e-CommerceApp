package org.example.ecommerceapp.Exception;

public class ProductAlreadyFoundException extends RuntimeException{

	public ProductAlreadyFoundException() {
		
	}
	
	public ProductAlreadyFoundException(String message) {
		super(message);
	}
}
