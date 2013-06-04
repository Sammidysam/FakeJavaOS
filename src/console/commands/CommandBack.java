package console.commands;

import console.Console;

public class CommandBack extends Command {
	public static String getActivator(){
		return "back";
	}
	public int run(Console caller){
		String copy = caller.getLastDirectory();
		caller.setLastDirectory(caller.getCurrentDirectory());
		caller.setCurrentDirectory(copy);
		return 0;
	}
}
