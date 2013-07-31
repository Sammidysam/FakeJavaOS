package console.commands;

import console.Console;

public class CommandStop extends Command {
	public String getActivator() {
		return "stop";
	}
	
	public int run(Console caller) {
		System.out.println("Shutting down FakeJavaOS...");
		System.exit(0);
		return 0;
	}
}
