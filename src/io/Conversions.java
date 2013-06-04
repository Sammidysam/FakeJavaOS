package io;

import java.io.File;

public class Conversions {
	public String fakeToReal(String fakeLocation, String realDriveLocation){
		fakeLocation = fakeLocation.substring(2);
		fakeLocation = fakeLocation.replace('/', File.separatorChar);
		realDriveLocation += fakeLocation;
		return realDriveLocation;
	}
}
