package boot.arguments;

import io.Directories;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import users.Group;
import users.GroupAdministrator;
import users.GroupNormal;
import users.User;

import boot.Launcher;

public class ArgumentInit extends ClosingArgument {
	protected String getInfo(){
		return "Initializing FakeJavaOS...";
	}
	protected String getName(){
		return "init";
	}
	public void run(Launcher caller){
		System.out.println("FakeJavaOS needs a fake drive to store data in.  This will just be a folder on your computer.");
		System.out.println("Would you like to specify a custom path [y] or use the default path [n]?");
		try {
			System.out.println("The default path is " + Directories.getDriveDirectory());
		} catch (URISyntaxException e) {
			
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = null;
		File driveLocation = null;
		try {
			String input = reader.readLine();
			if(input.charAt(0) == 'y'){
				System.out.println("Please enter the custom path for the fake drive.");
				input = reader.readLine();
				File init = new File(Directories.getJarDirectory() + File.separatorChar + "init.txt");
				writer = new PrintWriter(init);
				writer.println(input);
			} else {
				System.out.println(Directories.getDriveDirectory() + " will be used.");
				input = Directories.getDriveDirectory();
			}
			driveLocation = new File(input);
			if(!driveLocation.mkdirs())
				System.err.println("Failed to make necessary directories!");
		} catch (IOException e) {
			System.err.println("Failed to read user input!");
		} catch (URISyntaxException e) {
			
		} finally {
			try {
				writer.close();
			} catch (NullPointerException e) {
				
			}
		}
		System.out.println("Now FakeJavaOS needs at least one user for you to sign in as.  We suggest you use an administrator.");
		System.out.println("What would you like to name your user?");
		try {
			String name = reader.readLine();
			System.out.println("What would you like to make your user's password?");
			String password = reader.readLine();
			System.out.println("Would you like to make your user an administrator [a] or normal user [n]?");
			char answer = reader.readLine().charAt(0);
			Group group;
			if(answer == 'a')
				group = new GroupAdministrator();
			else
				group = new GroupNormal();
			System.out.println("Saving user...");
			User user = new User(name, password, group);
			if(!user.saveUser(driveLocation.getAbsolutePath()))
				System.err.println("Failed to save user!");
		} catch (IOException e) {
			System.err.println("Failed to read user input!");
		}
	}
}
