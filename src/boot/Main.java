package boot;

import java.io.File;
import java.net.URISyntaxException;

public class Main {
	public static void main(String[] args){
		try {
			System.setProperty("jarDir", new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent());
			System.setProperty("JDrive", System.getProperty("jarDir") + File.separatorChar + "JDrive");
		} catch (URISyntaxException e) {
			System.err.println(e.getMessage());
		}
		Launcher launcher = new Launcher(args);
		launcher.processArguments();
		launcher.launch();
	}
}
