package org.example.ecommerceapp.Exception;

public class LoginFailedException extends RuntimeException {
	public LoginFailedException (String message) {
		super(message);
	}
}
