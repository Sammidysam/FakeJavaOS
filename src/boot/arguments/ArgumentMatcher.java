package boot.arguments;

public class ArgumentMatcher {
	public static Argument matchArgument(String arg) throws UnmatchedArgumentException {
		if(arg.indexOf('=') != -1)
			arg = arg.substring(0, arg.indexOf('='));
		if(arg.equals(new ArgumentTextMode().getActivator()))
			return new ArgumentTextMode();
		else if(arg.equals(new ArgumentHelp().getActivator()))
			return new ArgumentHelp();
		else if(arg.equals(new ArgumentInit().getActivator()))
			return new ArgumentInit();
		else
			throw new UnmatchedArgumentException("Unable to match argument " + arg);
	}
}
