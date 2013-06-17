package console.commands;

import console.Console;

public class Command {
	protected String args = null;
	public boolean hasArgs(){
		return false;
	}
	public void setArgs(String args){
		this.args = args;
	}
	public String getActivator(){
		return null;
	}
	protected String[] splitArgs(){
		try {
			if(args.contains(" ")){
				return args.split(" ");
			} else {
				String[] argsList = new String[1];
				argsList[0] = args;
				return argsList;
			}
		} catch (NullPointerException e) {
//			System.err.println("Args were not set when trying to split args!");
//			commented out due to being annoying
			return new String[0];
		}
	}
	public String getHelp(){
		return "Not yet implemented!";
	}
	public int run(Console caller){
		return 0;
	}
}
