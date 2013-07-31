package boot.arguments;

import boot.Launcher;

public class ArgumentTextMode extends RunningArgument {
	protected String getInfo() {
		return "Using text mode...";
	}
	
	public String getName() {
		return "textmode";
	}
	
	public void run(Launcher caller) {
		caller.setGuiEnabled(false);
	}
}
