package boot;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import console.Console;

import users.User;

/**
 * The class that manages the version of operating system, including the creation of the window if gui is enabled or the creation of a console if gui is not enabled.
 *
 * @author Sam Craig (Sammidysam)
 * @since indev
 */
public class OperatingSystem {
	/**
	 * A boolean determining whether gui is enabled or not.
	 */
	private boolean guiEnabled;

	/**
	 * The location of the JDrive.
	 */
	private String driveLocation;

	/**
	 * The currently logged in user.
	 */
	private User user;

	/**
	 * Sets fields to arguments.
	 *
	 * @param guiEnabled Boolean determining whether gui is enabled or not.
	 * @param driveLocation The location of the JDrive.
	 * @param user The currently logged in user.
	 */
	public OperatingSystem(boolean guiEnabled, String driveLocation, User user) {
		this.guiEnabled = guiEnabled;
		this.driveLocation = driveLocation;
		this.user = user;
	}

	/**
	 * A method that creates a gui if gui is enabled or creates a {@link console.Console} if gui is not enabled.
	 */
	void start() {
		System.out.println("Starting FakeJavaOS version " + getVersion() + "...");
		if (guiEnabled) {
			System.out.println("Setting up FakeJavaOS GUI...");
		} else {
			System.out.println("Setting up FakeJavaOS TextMode...");
			Console console = new Console(false, driveLocation, user);
			console.loop();
		}
	}

	/**
	 * Retrieves the version of the operating system.
	 *
	 * @return The version, in a string.
	 */
	public static String getVersion() {
		// creates a bufferedreader to read a file inside the jar
		BufferedReader version = new BufferedReader(new InputStreamReader(OperatingSystem.class.getClassLoader().getResourceAsStream("version")));
		
		// creates a variable to hold the version
		String value = null;

		// retrieves version string
		try {
			value = version.readLine();
		} catch (IOException e) {
			System.err.println("Could not read file version.txt!");
		} finally {
			try {
				version.close();
			} catch (IOException e) {
				System.err.println("Failed to close " + version + "!");
			}
		}

		// returns version string
		return value;
	}
}
