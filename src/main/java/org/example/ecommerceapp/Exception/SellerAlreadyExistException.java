package org.example.ecommerceapp.Exception;

public class SellerAlreadyExistException extends RuntimeException{
	
	public SellerAlreadyExistException() {
	}
	
	public SellerAlreadyExistException(String message) {
		super(message);
	}
}
