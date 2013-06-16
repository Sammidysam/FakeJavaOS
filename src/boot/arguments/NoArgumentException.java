package boot.arguments;

public class NoArgumentException extends Exception {
	public NoArgumentException(String message){
		super(message);
	}
	public NoArgumentException(String message, Throwable throwable){
		super(message, throwable);
	}
}
