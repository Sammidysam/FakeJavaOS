package boot;

public class Main {
	public static void main(String[] args){
		Launcher launcher = new Launcher(args);
		launcher.processArguments();
		launcher.launch();
	}
}
