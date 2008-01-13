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

import java.util.ArrayList;

import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLBoxRelation;
import edu.raf.uml.model.UMLClass;
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLInheritance;
import edu.raf.uml.model.UMLInterface;
import edu.raf.uml.model.UMLObject;
import edu.raf.uml.model.UMLRelation;

public class InheritanceRelationFactory implements RelationFactory {

	/**
	 * @see edu.raf.uml.gui.tool.factory.RelationFactory#canRelate(edu.raf.uml.model.UMLBox,
	 *      edu.raf.uml.model.UMLObject)
	 */
	@Override
	public String canRelate(UMLBox from, UMLObject to) {
		if (!(to instanceof UMLBox))
			return "Can only inherit a "
					+ (from instanceof UMLInterface ? "interface." : "class");
		if (from instanceof UMLInterface && !(to instanceof UMLInterface))
			return "Incompatibile types. Whatever.";
		if (from instanceof UMLClass && !(to instanceof UMLClass))
			return "Incompatibile types. Whatever.";
		
		ArrayList <UMLBox> boxesRelatedToFrom = new ArrayList<UMLBox> ();
		boxesRelatedToFrom.add(from);
		int i = 0;
		while (i < boxesRelatedToFrom.size()) {
			int searchTo = boxesRelatedToFrom.size();
			for (; i < searchTo; i++) {
				if (boxesRelatedToFrom.get(i) == to)
					return "Those boxes are already related";
				for (UMLRelation relation: boxesRelatedToFrom.get(i).relations) {
					if (relation instanceof UMLBoxRelation) {
						if (boxesRelatedToFrom.get(i) == ((UMLBoxRelation)relation).from)
							if (!boxesRelatedToFrom.contains(((UMLBoxRelation)relation).to))
								boxesRelatedToFrom.add(((UMLBoxRelation)relation).to);
						if (boxesRelatedToFrom.get(i) == ((UMLBoxRelation)relation).to)
							if (!boxesRelatedToFrom.contains(((UMLBoxRelation)relation).from))
								boxesRelatedToFrom.add(((UMLBoxRelation)relation).from);
					}
				}
			}
			
		}		
		return null;
	}

	/**
	 * @see edu.raf.uml.gui.tool.factory.RelationFactory#canRelateFrom(edu.raf.uml.model.UMLBox)
	 */
	@Override
	public String canRelateFrom(UMLBox from) {
		if (from instanceof UMLClass || from instanceof UMLInterface)
			return null;
		return "Only classes and interfaces can inherit other objects";
	}

	/**
	 * @see edu.raf.uml.gui.tool.factory.RelationFactory#makeRelation(edu.raf.uml.model.UMLBox,
	 *      edu.raf.uml.model.UMLObject)
	 */
	@Override
	public UMLRelation makeRelation(UMLDiagram diagram, UMLBox from,
			UMLObject to) {
		return new UMLInheritance(diagram, from, (UMLBox) to);
	}
}
