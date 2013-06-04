package boot.arguments;

import boot.Launcher;
import boot.OperatingSystem;

public class ArgumentHelp extends ClosingArgument {
	protected String getInfo(){
		return "Displaying FakeJavaOS info...";
	}
	protected String getName(){
		return "help";
	}
	public void run(Launcher caller){
		System.out.println("---FakeJavaOS version " + OperatingSystem.getVersion() + " help---");
		System.out.println("Arguments with only one dash will affect how the program runs.");
		System.out.println("Arguments starting with two dashes will prevent the program from starting.");
	}
}
