package org.example.ecommerceapp.Exception;

public class ProductQuantityNotEnoughException extends RuntimeException{

	public ProductQuantityNotEnoughException() {
		
	}
	
	public ProductQuantityNotEnoughException(String message) {
		super(message);
	}
}
