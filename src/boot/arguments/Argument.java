package boot.arguments;

import boot.Launcher;

/**
 * An argument for FakeJavaOS.
 *
 * @author Sam Craig (Sammidysam)
 * @since indev
 */
public class Argument {
	/**
	 * The value of the arguments inputted to the argument.
	 */
	protected String equals = null;

	/**
	 * Gets whether the argument has arguments or not.
	 *
	 * @return Whether the argument has arguments or not.
	 */
	public boolean hasEquals() {
		return false;
	}

	/**
	 * Sets the value of the arguments inputted to the argument.
	 *
	 * @param equals The new value of the arguments inputted to the argument.
	 */
	public void setEquals(String equals) {
		this.equals = equals;
	}

	/**
	 * Gets the info string associated with the argument.
	 * The info string holds information about what the argument is doing.
	 *
	 * @return The information associated with the argument.
	 */
	protected String getInfo() {
		return null;
	}

	/**
	 * Prints information about the argument, if the argument has information.
	 */
	public void displayInfo() {
		if (getInfo() != null)
			System.out.println(getInfo());
	}

	/**
	 * Gets the string that starts the argument.  For a {@link boot.arguments.RunningArgument} it is "-", and for a {@link boot.arguments.ClosingArgument} it is "--".
	 *
	 * @return The string that starts the argument.
	 */
	protected String getStarter() {
		return null;
	}

	/**
	 * Gets the name of the argument, e.g. for {@link boot.arguments.ArgumentTextMode} the value returned is "textmode".
	 * This is the activator without the starter.
	 *
	 * @return The activator-like name of the argument.
	 */
	protected String getName() {
		return null;
	}

	/**
	 * Combines {@link #getStarter} and {@link #getName} to create the activator for the argument.
	 *
	 * @return The activator for the argument.
	 */
	public String getActivator() {
		return getStarter() + getName();
	}

	/**
	 * Runs code associated with the argument.
	 *
	 * @param caller The launcher calling the argument.
	 */
	public void run(Launcher caller) {
		
	}
}
