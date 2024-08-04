package org.example.ecommerceapp.Exception;

public class NoProductFoundInCart extends RuntimeException {

	public NoProductFoundInCart() {
		super();

	}

	public NoProductFoundInCart(String message) {
		super(message);

	}

}
