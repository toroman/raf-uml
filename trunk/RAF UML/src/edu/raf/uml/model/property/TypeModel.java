/*
RAF UML - Student project for Object oriented programming and design
Copyright (C) <2007>  Ivan Bocic, Sasa Sijak, Srecko Toroman

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.raf.uml.model.property;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Predstavlja tip (nekog field-a ili metode). U sustini potrebno je samo da se
 * razlikuje Tip od obicnog stringa, jer tip ima posebno formatiranje i tip ima
 * par predloga (string, int ...)
 * 
 * @author Srecko Toroman
 * 
 */
public class TypeModel {
	private static class PatternIH {
		private static Pattern instance = Pattern.compile("[a-zA-Z_]+\\w*");
	}

	/**
	 * Registar svih koristenih tipova.
	 */
	private static Set<String> types = null;

	private String type = "";

	public TypeModel(String type) {
		this.type = type;
	}
	
	public TypeModel() {
		this("");
	}

	/**
	 * Ovde mozemo da dodamo da ubaci i sve UMLClass/ Interface iz dijagrama
	 * 
	 * @return
	 */
	public String[] getTypes() {
		if (types == null) {
			types = new HashSet<String>(Arrays.asList(new String[] { "string",
					"int", "double", "void" }));
		}
		types.add(type);
		String[] all = types.toArray(new String[0]);
		Arrays.sort(all);
		return all;
	}

	public void setType(String type) {
		if (type.length() > 0 && !PatternIH.instance.matcher(type).matches())
			throw new IllegalArgumentException("Invalid Type name!");
		types.add(type);
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}

}
