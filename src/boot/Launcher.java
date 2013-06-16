package boot;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import users.GroupAdministrator;
import users.GroupNormal;
import users.User;

import boot.arguments.Argument;
import boot.arguments.Matcher;
import boot.arguments.ClosingArgument;
import boot.arguments.UnmatchedArgumentException;

public class Launcher {
	private boolean guiEnabled = true;
	private String[] args;
	public Launcher(String[] args){
		this.args = args;
	}
	void processArguments(){
		boolean hasClosing = false;
		for(int i = 0; i < args.length; i++){
			try {
				Argument arg = Matcher.matchArgument(args[i]);
				if(args[i].contains("="))
					arg.setEquals(args[i].substring(args[i].indexOf('=') + 1, args[i].length()));
//				check if there is a closing argument
//				if so, we will need to close after processing all args
				if(arg instanceof ClosingArgument)
					hasClosing = true;
//				display information about the arg
				arg.displayInfo();
//				run necessary code for the argument
				arg.run(this);
			} catch (UnmatchedArgumentException e) {
				System.err.println(e.getMessage());
			}
		}
		if(hasClosing){
			System.out.println("Closing argument detected; shutting down...");
			System.exit(0);
		}
	}
	void launch(){
		FileInputStream fstream = null;
		BufferedReader br = null;
		String driveLocation = null;
		try {
			if(new File(System.getProperty("jarDir") + File.separatorChar + "init.txt").exists()){
				fstream = new FileInputStream(System.getProperty("jarDir") + File.separatorChar + "init.txt");
				DataInputStream in = new DataInputStream(fstream);
				br = new BufferedReader(new InputStreamReader(in));
				driveLocation = br.readLine();
			} else if(!new File(System.getProperty("JDrive")).exists()){
				System.out.println("No FakeJavaOS directory detected!");
				System.out.println("You need to run FakeJavaOS with the argument \"--init\" to initialize FakeJavaOS!");
				System.exit(0);
			}
			else
				driveLocation = System.getProperty("JDrive");
		} catch (FileNotFoundException e) {
			System.err.println("What just happened?");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Failed to read from file!");
			System.exit(1);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				System.err.println("Failed to close " + br + "!");
			} catch (NullPointerException e) {
				
			}
			try {
				fstream.close();
			} catch (IOException e) {
				System.err.println("Failed to close " + fstream + "!");
			} catch (NullPointerException e) {
				
			}
		}
		if(driveLocation == null)
			System.exit(1);
		System.out.println("Please log in.");
		System.out.println("Username?");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		User user = null;
		try {
			String username = reader.readLine();
			System.out.println("Password?");
			String password = reader.readLine();
			File usersFile = new File(driveLocation + File.separatorChar + "users.json");
			if(usersFile.exists()){
				fstream = new FileInputStream(driveLocation + File.separatorChar + "users.json");
				DataInputStream in = new DataInputStream(fstream);
				br = new BufferedReader(new InputStreamReader(in));
				JsonObject users = JsonObject.readFrom(br);
				JsonArray usernames = users.get("Usernames").asArray();
				JsonArray passwords = users.get("Passwords").asArray();
				JsonArray groups = users.get("Groups").asArray();
				for(int i = 0; i < usernames.size(); i++)
					if(usernames.get(i).asString().equals(username) && passwords.get(i).asString().equals(password))
						user = new User(username, password, groups.get(i).asString().equals("Administrator") ? new GroupAdministrator() : new GroupNormal());
			} else {
				System.err.println("Users file does not exist!");
				System.err.println("Run with argument \"--init\" to create a user!");
				System.exit(1);
			}
		} catch (IOException e) {
			System.err.println("Failed to read user input!");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				System.err.println("Failed to close " + br + "!");
			} catch (NullPointerException e) {
				
			}
			try {
				fstream.close();
			} catch (IOException e) {
				System.err.println("Failed to close " + fstream + "!");
			} catch (NullPointerException e) {
				
			}
		}
		if(user == null){
			System.err.println("Failed to log in!");
			System.exit(1);
		}
		OperatingSystem os = new OperatingSystem(guiEnabled, driveLocation, user);
		os.start();
	}
	public void setGuiEnabled(boolean guiEnabled){
		this.guiEnabled = guiEnabled;
	}
}
