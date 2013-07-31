package console.commands;

import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import console.Console;

import pro.ddopson.ClassEnumerator;

public class CommandHelp extends Command {
	private static String errorMessage = "Error dynamically loading commands:  ";
	
	public boolean hasArgs() {
		return true;
	}
	
	public String getActivator() {
		return "help";
	}
	
	public int run(Console caller) {
		String[] args = splitArgs();
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				try {
					Command helpWith = Matcher.matchCommand(args[i]);
					System.out.println(helpWith.getHelp());
				} catch (UnmatchedCommandException e) {
					System.err.println(e.getMessage());
				}
			}
			return 0;
		} else {
			ArrayList<Class<?>> discoveredClasses = ClassEnumerator.getClassesForPackage(Matcher.class.getPackage());
			ArrayList<Class<?>> goodClasses = new ArrayList<Class<?>>(0);
			for (int i = 0; i < discoveredClasses.size(); i++)
				if (Matcher.isCommand(discoveredClasses.get(i).getSimpleName()))
					goodClasses.add(discoveredClasses.get(i));
			System.out.println("Available commands:");
			for (int i = 0; i < goodClasses.size(); i++) {
				try {
					Class<Command> commandClass = (Class<Command>) Class.forName(goodClasses.get(i).getName());
					Method getActivator = commandClass.getMethod("getActivator", (Class[]) null);
					Object instance = commandClass.newInstance();
					System.out.println((String) getActivator.invoke(instance, (Object[]) null));
				} catch (ClassNotFoundException e) {
					System.err.println(errorMessage + e.getMessage());
				} catch (NoSuchMethodException e) {
					System.err.println(errorMessage + e.getMessage());
				} catch (SecurityException e) {
					System.err.println(errorMessage + e.getMessage());
				} catch (InstantiationException e) {
					System.err.println(errorMessage + e.getMessage());
				} catch (IllegalAccessException e) {
					System.err.println(errorMessage + e.getMessage());
				} catch (IllegalArgumentException e) {
					System.err.println(errorMessage + e.getMessage());
				} catch (InvocationTargetException e) {
					System.err.println(errorMessage + e.getMessage());
				}
			}
			return 0;
		}
	}
}
