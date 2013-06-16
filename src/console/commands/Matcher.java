package console.commands;

import java.util.ArrayList;
import java.lang.Class;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.Object;

import pro.ddopson.ClassEnumerator;

public class Matcher {
	public static Command matchCommand(String command) throws UnmatchedCommandException {
		ArrayList<Class<?>> discoveredClasses = ClassEnumerator.getClassesForPackage(Matcher.class.getPackage());
		ArrayList<Class<?>> goodClasses = new ArrayList<Class<?>>(0);
		for(int i = 0; i < discoveredClasses.size(); i++){
			if(discoveredClasses.get(i).getSimpleName().startsWith("Command") && !discoveredClasses.get(i).getSimpleName().equals("Command"))
				goodClasses.add(discoveredClasses.get(i));
		}
		for(int i = 0; i < goodClasses.size(); i++){
			try {
				Class<Command> commandClass = (Class<Command>)Class.forName(goodClasses.get(i).getName());
				Method getActivator = commandClass.getMethod("getActivator", (Class[])null);
				Object instance = commandClass.newInstance();
				String activator = (String)getActivator.invoke(instance, (Object[])null);
				if(command.equals(activator))
					return (Command)instance;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		throw new UnmatchedCommandException("Unable to match command " + command);
	}
}
