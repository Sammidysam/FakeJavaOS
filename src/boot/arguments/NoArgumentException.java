package boot.arguments;

@SuppressWarnings("serial")
public class NoArgumentException extends Exception {
	public NoArgumentException(String message){
		super(message);
	}
	public NoArgumentException(String message, Throwable throwable){
		super(message, throwable);
	}
}
