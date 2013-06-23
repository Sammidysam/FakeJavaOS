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

/**
 * A class for launching FakeJavaOS.
 *
 * @author Sam Craig (Sammidysam)
 * @since indev
 */
public class Launcher {
	/**
	 * A boolean determining whether GUI is enabled or not.
	 */
	private boolean guiEnabled = true;

	/**
	 * The arguments inputted to the program.
	 */
	private String[] args;

	/**
	 * Sets field args to argument args.
	 *
	 * @param args The arguments inputted to the program.
	 */
	public Launcher(String[] args){
		this.args = args;
	}

	/**
	 * Processes the arguments in variable args.
	 */
	void processArguments(){
		boolean hasClosing = false;
		for(int i = 0; i < args.length; i++){
			try {
				// match argument
				Argument arg = Matcher.matchArgument(args[i]);

				// check if argument has arguments
				if(args[i].contains("="))
					arg.setEquals(args[i].substring(args[i].indexOf('=') + 1, args[i].length()));

				// check if there is a closing argument
				// if so, the program will need to close after processing all args
				if(arg instanceof ClosingArgument)
					hasClosing = true;

				// display information about the arg
				arg.displayInfo();

				// run necessary code for the argument
				arg.run(this);

			// catch an exception thrown if the argument could not be matched
			} catch (UnmatchedArgumentException e) {
				System.err.println(e.getMessage());
			}
		}

		// close if a closing argument was present in arguments
		if(hasClosing){
			System.out.println("Closing argument detected; shutting down...");
			System.exit(0);
		}
	}

	/**
	 * Launches FakeJavaOS.
	 */
	void launch(){
		// setup variables for reading of file to figure out JDrive location
		FileInputStream fstream = null;
		BufferedReader br = null;
		String driveLocation = null;
		try {
			// determine location of JDrive
			if(new File(System.getProperty("jarDir") + File.separatorChar + "init.txt").exists()){
				// read location from file
				fstream = new FileInputStream(System.getProperty("jarDir") + File.separatorChar + "init.txt");
				DataInputStream in = new DataInputStream(fstream);
				br = new BufferedReader(new InputStreamReader(in));
				driveLocation = br.readLine();
			} else if(!new File(System.getProperty("JDrive")).exists()){
				// display error message that no JDrive exists
				System.out.println("No FakeJavaOS directory detected!");
				System.out.println("You need to run FakeJavaOS with the argument \"--init\" to initialize FakeJavaOS!");
				System.exit(0);
			}
			else
				// set JDrive location to default location
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

		// log in a user
		// print prompt
		System.out.println("Please log in.");
		System.out.println("Username?");

		// setup input reader
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		User user = null;
		try {
			// get username and password
			String username = reader.readLine();
			System.out.println("Password?");
			String password = reader.readLine();

			// read users file
			File usersFile = new File(driveLocation + File.separatorChar + "users.json");
			if(usersFile.exists()){
				fstream = new FileInputStream(driveLocation + File.separatorChar + "users.json");
				DataInputStream in = new DataInputStream(fstream);
				br = new BufferedReader(new InputStreamReader(in));

				// read json from users file
				JsonObject users = JsonObject.readFrom(br);
				JsonArray usernames = users.get("Usernames").asArray();
				JsonArray passwords = users.get("Passwords").asArray();
				JsonArray groups = users.get("Groups").asArray();

				// loop through usernames checking if username matches any of them
				// if username matches and password matches then user will be logged in
				// if username matches and password does not match user will not be logged in
				for(int i = 0; i < usernames.size(); i++)
					if(usernames.get(i).asString().equals(username) && passwords.get(i).asString().equals(password))
						user = new User(username, password, groups.get(i).asString().equals("Administrator") ? new GroupAdministrator() : new GroupNormal());
					else if(usernames.get(i).asString().equals(username) && !passwords.get(i).asString().equals(password)){
						System.out.println("Incorrect password!");
						System.exit(1);
					}
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

		// create new operating system instance
		// start operating system
		OperatingSystem os = new OperatingSystem(guiEnabled, driveLocation, user);
		os.start();
	}

	/**
	 * Sets the value of gui being enabled or not.  Used by {@link boot.arguments.ArgumentTextMode}.
	 */
	public void setGuiEnabled(boolean guiEnabled){
		this.guiEnabled = guiEnabled;
	}
}
