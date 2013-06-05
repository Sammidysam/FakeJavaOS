package boot.arguments;

import boot.Launcher;

public class Argument {
	protected String equals = null;
	public boolean hasEquals(){
		return false;
	}
	public void setEquals(String equals){
		this.equals = equals;
	}
	protected String getInfo(){
		return null;
	}
	public void displayInfo(){
		if(getInfo() != null)
			System.out.println(getInfo());
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
