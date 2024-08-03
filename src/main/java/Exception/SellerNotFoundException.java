package Exception;

public class SellerNotFoundException extends RuntimeException{
	
	public SellerNotFoundException() {
	}
	
	public SellerNotFoundException(String message) {
		super(message);
	}
}
