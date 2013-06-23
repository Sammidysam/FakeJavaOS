package boot;

import java.io.File;
import java.net.URISyntaxException;

/**
 * The class containing the main method.
 *
 * @author Sam Craig (Sammidysam)
 * @since indev
 */
public class Main {

	/**
	 * The program's main method.
	 */
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

		// process arguments provided as argument args in launcher constructor
		launcher.processArguments();

		// launch OS
		launcher.launch();
	}
}
