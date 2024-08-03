package Exception;

public class InvalidLoginKeyException extends RuntimeException {
	public InvalidLoginKeyException(String message) {
		super(message);
	}
} 
