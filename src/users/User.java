package users;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

public class User {
	private String name;
	private String password;
	private Group group;
	
	public User(String name, String password, Group group) {
		this.name = name;
		this.password = password;
		this.group = group;
	}
	
	public boolean saveUser(String driveLocation) {
		File userSaveLocation = new File(driveLocation + File.separatorChar + "users.json");
		boolean exists = userSaveLocation.exists();
		FileWriter writer = null;
		FileInputStream fstream = null;
		BufferedReader br = null;
		JsonArray usernames = new JsonArray();
		JsonArray passwords = new JsonArray();
		JsonArray groups = new JsonArray();
		try {
			if (!exists && !userSaveLocation.createNewFile())
				return false;
//			build and write JSON data
			fstream = new FileInputStream(driveLocation + File.separatorChar + "users.json");
			DataInputStream in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			if (exists) {
				JsonObject users = JsonObject.readFrom(br);
				usernames = users.get("Usernames").asArray();
				passwords = users.get("Passwords").asArray();
				groups = users.get("Groups").asArray();
			}
			usernames.add(name);
			passwords.add(password);
			groups.add(group instanceof GroupAdministrator ? "Administrator" : "Normal");
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return false;
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
		try {
			writer = new FileWriter(userSaveLocation, false);
			new JsonObject()
				.add("Usernames", usernames)
				.add("Passwords", passwords)
				.add("Groups", groups)
				.writeTo(writer);
			return true;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return false;
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				System.err.println("Failed to close " + writer + "!");
			} catch (NullPointerException e) {
				
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Group getGroup() {
		return group;
	}
}
