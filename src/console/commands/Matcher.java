package console.commands;

import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import pro.ddopson.ClassEnumerator;

public class Matcher {
	private static String errorMessage = "Error dynamically loading commands:  ";
	
	public static boolean isCommand(String simpleName) {
		if (simpleName.startsWith("Command") && !simpleName.equals("Command"))
			return true;
		else
			return false;
	}
	
	public static Command matchCommand(String command) throws UnmatchedCommandException {
		ArrayList<Class<?>> discoveredClasses = ClassEnumerator.getClassesForPackage(Matcher.class.getPackage());
		ArrayList<Class<?>> goodClasses = new ArrayList<Class<?>>(0);
		for (int i = 0; i < discoveredClasses.size(); i++)
			if (isCommand(discoveredClasses.get(i).getSimpleName()))
				goodClasses.add(discoveredClasses.get(i));
		for (int i = 0; i < goodClasses.size(); i++) {
			try {
				Class<Command> commandClass = (Class<Command>) Class.forName(goodClasses.get(i).getName());
				Method getActivator = commandClass.getMethod("getActivator", (Class[]) null);
				Object instance = commandClass.newInstance();
				String activator = (String) getActivator.invoke(instance, (Object[]) null);
				if (command.equals(activator))
					return (Command) instance;
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
		throw new UnmatchedCommandException("Unable to match command " + command);
	}
}
