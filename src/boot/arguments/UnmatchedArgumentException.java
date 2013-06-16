package boot.arguments;

public class UnmatchedArgumentException extends Exception {
	public UnmatchedArgumentException(String message){
		super(message);
	}
	public UnmatchedArgumentException(String message, Throwable throwable){
		super(message, throwable);
	}
}
