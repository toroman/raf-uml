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

public class ArgumentModel {
	private String name;
	private TypeModel type;
	
	public ArgumentModel() {
		// prazan konstruktor mora postojati zbog ListField-a
		name = "x";
		type = new TypeModel("int");
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Property
	public String getName() {
		return name;
	}
	
	public void setType(TypeModel type) {
		this.type = type;
	}
	
	@Property
	public TypeModel getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return type + " " + name;
	}
}
