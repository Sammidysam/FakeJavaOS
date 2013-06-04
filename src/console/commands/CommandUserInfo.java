package console.commands;

import users.GroupAdministrator;
import console.Console;

public class CommandUserInfo extends Command {
	public static String getActivator(){
		return "userinfo";
	}
	public int run(Console caller){
		System.out.println(caller.getUser().getGroup() instanceof GroupAdministrator ? caller.getUser().getName() + " is an administrator." : caller.getUser().getName() + " is a normal user.");
		return 0;
	}
}
