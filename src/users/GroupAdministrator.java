package users;

public class GroupAdministrator extends Group {
	public GroupAdministrator() {
		rights.setCanDeleteAnything(true);
		rights.setCanModifyAnything(true);
	}
}
