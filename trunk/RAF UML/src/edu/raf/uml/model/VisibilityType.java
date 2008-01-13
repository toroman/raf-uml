package edu.raf.uml.model;

public enum VisibilityType {
	Public,
	Default,
	Protected,
	Private;
	/**
	 * Kako ovaj Visibility treba da se vidi u UML dijagramu
	 * @return
	 */
	public String uml() {
		if (Public.equals(this))
			return "+";
		if (Protected.equals(this))
			return "%"; // ovo mozda zanemarimo?
		if (Private.equals(this))
			return "-";
		return "";
	}
}
