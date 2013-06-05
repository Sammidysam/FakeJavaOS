package console.commands;

@SuppressWarnings("serial")
public class TooFewArgumentsException extends Exception {
	public TooFewArgumentsException(String message){
		super(message);
	}
	public TooFewArgumentsException(String message, Throwable throwable){
		super(message, throwable);
	}
}
