package boot.arguments;

import boot.Launcher;

public class Argument {
	protected String getInfo(){
		return null;
	}
	public void displayInfo(){
		try {
			System.out.println(getInfo());
		} catch (NullPointerException e) {
			
		}
	}
	protected String getStarter(){
		return null;
	}
	protected String getName(){
		return null;
	}
	public String getActivator(){
		return getStarter() + getName();
	}
	public void run(Launcher caller){
		
	}
}
