package users;

public class Rights {
	private boolean canDeleteAnything = false, canModifyAnything = false;
	
	public boolean getCanDeleteAnything() {
		return canDeleteAnything;
	}
	
	public boolean getCanModifyAnything() {
		return canModifyAnything;
	}
	
	public void setCanDeleteAnything(boolean canDeleteAnything) {
		this.canDeleteAnything = canDeleteAnything;
	}
	
	public void setCanModifyAnything(boolean canModifyAnything) {
		this.canModifyAnything = canModifyAnything;
	}
}
