package boot;

import users.User;
import console.Console;

public class OperatingSystem {
	private boolean guiEnabled;
	private String driveLocation;
	private User user;
	public OperatingSystem(boolean guiEnabled, String driveLocation, User user){
		this.guiEnabled = guiEnabled;
		this.driveLocation = driveLocation;
		this.user = user;
	}
	void start(){
		System.out.println("Starting FakeJavaOS version " + getVersion() + "...");
		if(guiEnabled){
			System.out.println("Setting up FakeJavaOS GUI...");
		} else {
			System.out.println("Setting up FakeJavaOS TextMode...");
			Console console = new Console(false, driveLocation, user);
			console.loop();
		}
	}
	public static String getVersion(){
//		add reading from file
		return "in-dev";
	}
}
