package console.commands;

public class CommandMatcher {
	public static Command matchCommand(String command) throws UnmatchedCommandException {
		if(command.equals(CommandBack.getActivator()))
			return new CommandBack();
		else if(command.equals(CommandStop.getActivator()))
			return new CommandStop();
		else if(command.equals(CommandUser.getActivator()))
			return new CommandUser();
		else
			throw new UnmatchedCommandException("Unable to match command " + command);
	}
}
