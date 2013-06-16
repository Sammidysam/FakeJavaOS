package console.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import users.GroupAdministrator;
import users.GroupNormal;
import users.User;
import console.Console;

public class CommandUser extends Command {
	public boolean hasArgs(){
		return true;
	}
	public String getActivator(){
		return "user";
	}
	public int run(Console caller){
		String[] args = splitArgs();
		if(args.length > 0){
			if(args[0].equals("add")){
				if(args.length > 1){
					for(int i = 1; i < args.length; i++){
						System.out.print(args[i] + "'s password is ");
						BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
						try {
							String password = reader.readLine();
							System.out.println("Will " + args[1] + " be a member of the administrator group [a] or normal users group [n]?");
							char answer = reader.readLine().charAt(0);
							User user = null;
							if(answer == 'a')
								user = new User(args[1], password, new GroupAdministrator());
							else
								user = new User(args[1], password, new GroupNormal());
							user.saveUser(caller.getDriveLocation());
						} catch (IOException e) {
							System.err.println("Failed to get user input!");
						}
					}
					return 0;
				} else {
					try {
						throw new TooFewArgumentsException("You must specify a username after \"user add\"!");
					} catch (TooFewArgumentsException e) {
						System.err.println(e.getMessage());
					}
					return 1;
				}
			} else if(args[0].equals("remove")){
				return 1;
			} else {
				try {
					throw new UnknownArgumentException("Argument " + args[0] + " unknown!");
				} catch (UnknownArgumentException e) {
					System.err.println(e.getMessage());
				}
				return 1;
			}
		} else {
			System.out.println("Username is " + caller.getUser().getName());
			System.out.println("Password is " + caller.getUser().getPassword());
			System.out.println(caller.getUser().getGroup() instanceof GroupAdministrator ? caller.getUser().getName() + " is an administrator." : caller.getUser().getName() + " is a normal user.");
			return 0;
		}
	}
}
