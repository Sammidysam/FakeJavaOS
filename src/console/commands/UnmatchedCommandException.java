package console.commands;

@SuppressWarnings("serial")
public class UnmatchedCommandException extends Exception {
	public UnmatchedCommandException(String message){
		super(message);
	}
	public UnmatchedCommandException(String message, Throwable throwable){
		super(message, throwable);
	}
}
