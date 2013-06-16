package console.commands;

public class UnknownArgumentException extends Exception {
	public UnknownArgumentException(String message){
		super(message);
	}
	public UnknownArgumentException(String message, Throwable throwable){
		super(message, throwable);
	}
}
