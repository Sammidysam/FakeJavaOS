package boot.arguments;

import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import pro.ddopson.ClassEnumerator;

public class Matcher {
	private static String errorMessage = "Error dynamically loading arguments:  ";
	
	private static boolean isArgument(String simpleName) {
		if (simpleName.startsWith("Argument") && !simpleName.equals("Argument"))
			return true;
		else
			return false;
	}
	
	public static Argument matchArgument(String arg) throws UnmatchedArgumentException {
		ArrayList<Class<?>> discoveredClasses = ClassEnumerator.getClassesForPackage(Matcher.class.getPackage());
		ArrayList<Class<?>> goodClasses = new ArrayList<Class<?>>(0);
		for (int i = 0; i < discoveredClasses.size(); i++)
			if (isArgument(discoveredClasses.get(i).getSimpleName()))
				goodClasses.add(discoveredClasses.get(i));
		for (int i = 0; i < goodClasses.size(); i++) {
			try {
				Class<Argument> argumentClass = (Class<Argument>) Class.forName(goodClasses.get(i).getName());
				Method getActivator = argumentClass.getMethod("getActivator", (Class[]) null);
				Object instance = argumentClass.newInstance();
				String activator = (String)getActivator.invoke(instance, (Object[]) null);
				if (arg.equals(activator))
					return (Argument) instance;
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
		throw new UnmatchedArgumentException("Unable to match argument " + arg);
	}
}
