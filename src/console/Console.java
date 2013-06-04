package console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import users.User;

import console.commands.Command;
import console.commands.CommandMatcher;
import console.commands.UnmatchedCommandException;

public class Console {
	private String driveLocation;
	private User user;
	private String lastDirectory = "J:/home";
	private String currentDirectory = "J:/home/sam";
	public Console(boolean guiEnabled, String driveLocation, User user){
		if(guiEnabled)
			setupWindow();
		this.driveLocation = driveLocation;
		this.user = user;
	}
	public void processCommand(String input){
		try {
			Command command = input.contains(" ") ? CommandMatcher.matchCommand(input.substring(0, input.indexOf(' '))) : CommandMatcher.matchCommand(input);
			if(command.hasArgs()){
				String args = input.substring(input.indexOf(' ') + 1, input.length());
				command.setArgs(args);
			}
			command.run(this);
		} catch (UnmatchedCommandException e) {
			System.out.println(e.getMessage());
		}
	}
	public void loop(){
		while (true){
			System.out.print(user.getName() + " @ " + currentDirectory + "]$ ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String input;
			try {
				input = reader.readLine();
				processCommand(input);
			} catch (IOException e) {
				System.err.println("Failed to read user input!");
			}
		}
	}
	private void setupWindow(){
		
	}
	public String getLastDirectory(){
		return lastDirectory;
	}
	public String getCurrentDirectory(){
		return currentDirectory;
	}
	public String getDriveLocation(){
		return driveLocation;
	}
	public User getUser(){
		return user;
	}
	public void setLastDirectory(String lastDirectory){
		this.lastDirectory = lastDirectory;
	}
	public void setCurrentDirectory(String currentDirectory){
		this.currentDirectory = currentDirectory;
	}
}
