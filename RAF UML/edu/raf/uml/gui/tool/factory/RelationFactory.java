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
package edu.raf.uml.gui.tool.factory;

import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLObject;
import edu.raf.uml.model.UMLRelation;

public interface RelationFactory {

	/**
	 * Ovu metodu tje pozivati tool za pravljenje relacije kada se odabere
	 * objekat sa koga ta relacija pochinje, da bi se proverilo da li ta
	 * relacija mozhe da se napravi sa tim objektom kao pochetkom. Btw, zashto
	 * mi ne prikazuje return?
	 * 
	 * @param from -
	 *            UMLBox sa kog ta relacija treba da krene
	 * @return String - ukoliko se prihvata, u suprotnom tekst koji opisuje
	 *         zashto nije prihvatjen.
	 */
	public abstract String canRelateFrom(UMLBox from);

	/**
	 * Ovu metodu tje pozivati tool za pravljenje relacije kada se odaberu
	 * objekti za koju se ta relacija pravi. Opet javadoc zajebava..
	 * 
	 * @param from -
	 *            UMLBox sa koga bi relacija trebalo da pochne
	 * @param to -
	 *            UMLObject do koga bi relacija trebalo da ide
	 * @return String - null ako je ok, u suprotnom objashnjenje zashto nije
	 *         prihvatjeno
	 */
	public abstract String canRelate(UMLBox from, UMLObject to);

	/**
	 * Samo pravljenje relacije.
	 * 
	 * @param diagram -
	 *            u kom dijagramu da se napravi
	 * @param from -
	 *            od chega
	 * @param to -
	 *            do chega
	 * @return UMLRelation - napravljena relacija
	 */
	public UMLRelation makeRelation(UMLDiagram diagram, UMLBox from,
			UMLObject to);
}
