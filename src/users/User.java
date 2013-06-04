package users;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import io.Directories;

public class User {
	private String name;
	private Group group;
	public User(String name, Group group){
		this.name = name;
		this.group = group;
	}
	public boolean saveUser(String driveLocation){
		try {
			File userSaveLocation = new File(Directories.getUsersDirectory(driveLocation));
			if(!userSaveLocation.exists() && !userSaveLocation.mkdirs())
				return false;
			userSaveLocation = new File(Directories.getUsersDirectory(driveLocation) + File.separatorChar + name + ".user");
			PrintWriter writer = null;
			if(userSaveLocation.exists())
				return false;
			else {
				try {
					if(userSaveLocation.createNewFile()){
						writer = new PrintWriter(userSaveLocation);
						if(group instanceof GroupAdministrator)
							writer.println("GroupAdministrator");
						else
							writer.println("GroupNormal");
						return true;
					}
					else
						return false;
				} catch (IOException e) {
					return false;
				} finally {
					try {
						writer.close();
					} catch (NullPointerException e) {
						
					}
				}
			}
		} catch (URISyntaxException e) {
			return false;
		}
	}
	public String getName(){
		return name;
	}
	public Group getGroup(){
		return group;
	}
}
