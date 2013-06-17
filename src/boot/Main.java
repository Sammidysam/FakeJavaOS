package boot;

import java.io.File;
import java.net.URISyntaxException;


public class Main {
	public static void main(String[] args){
		// set some properties used by the program
		// JDrive is the default value at this time
		try {
			System.setProperty("jarDir", new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent());
			System.setProperty("JDrive", System.getProperty("jarDir") + File.separatorChar + "JDrive");
		} catch (URISyntaxException e) {
			System.err.println(e.getMessage());
		}

		// create launcher which will launch program
		Launcher launcher = new Launcher(args);
		launcher.processArguments();
		launcher.launch();
	}
}
