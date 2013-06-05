package io;

import java.io.File;
import java.net.URISyntaxException;

public class Directories {
	public static String getJarDirectory() throws URISyntaxException {
		return new File(Directories.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
	}
	public static String getDriveDirectory() throws URISyntaxException {
		return getJarDirectory() + File.separatorChar + "JDrive";
	}
}
