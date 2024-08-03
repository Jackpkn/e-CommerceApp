package Exception;

public class ProductNotAvailableException extends RuntimeException{

	public ProductNotAvailableException() {
		
	}
	
	public ProductNotAvailableException(String message) {
		super(message);
	}
}
