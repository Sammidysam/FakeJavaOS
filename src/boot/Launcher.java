package boot;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import users.GroupAdministrator;
import users.GroupNormal;
import users.User;

import boot.arguments.Argument;
import boot.arguments.ArgumentMatcher;
import boot.arguments.ClosingArgument;
import boot.arguments.UnmatchedArgumentException;

import io.Directories;

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
				Argument arg = ArgumentMatcher.matchArgument(args[i]);
//				check if there is a closing argument
//				if so, we will need to close after processing all args
				if(arg instanceof ClosingArgument)
					hasClosing = true;
//				display information about the arg
				arg.displayInfo();
//				run necessary code for the argument
				arg.run(this);
			} catch (UnmatchedArgumentException e) {
				System.out.println(e.getMessage());
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
			if(new File(Directories.getJarDirectory() + File.separatorChar + "init.txt").exists()){
				fstream = new FileInputStream(Directories.getJarDirectory() + File.separatorChar + "init.txt");
				DataInputStream in = new DataInputStream(fstream);
				br = new BufferedReader(new InputStreamReader(in));
				driveLocation = br.readLine();
			} else if(!new File(Directories.getDriveDirectory()).exists()){
				System.out.println("No FakeJavaOS directory detected!");
				System.out.println("You need to run FakeJavaOS with the argument \"--init\" to initialize FakeJavaOS!");
				System.exit(0);
			}
			else
				driveLocation = Directories.getDriveDirectory();
		} catch (URISyntaxException e) {
			System.exit(1);
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
			File userFile = new File(Directories.getUsersDirectory(driveLocation) + File.separatorChar + username + ".user");
			if(userFile.exists()){
				fstream = new FileInputStream(Directories.getUsersDirectory(driveLocation) + File.separatorChar + username + ".user");
				DataInputStream in = new DataInputStream(fstream);
				br = new BufferedReader(new InputStreamReader(in));
				String group = br.readLine();
				if(group.equals("GroupAdministrator"))
					user = new User(username, new GroupAdministrator());
				else if(group.equals("GroupNormal"))
					user = new User(username, new GroupNormal());
			}
			else
				System.err.println("User " + username + " does not exist!");
		} catch (IOException e) {
			System.err.println("Failed to read user input!");
		} catch (URISyntaxException e) {
			
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
