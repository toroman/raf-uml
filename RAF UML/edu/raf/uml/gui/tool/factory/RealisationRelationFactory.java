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
import edu.raf.uml.model.UMLClass;
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLInterface;
import edu.raf.uml.model.UMLObject;
import edu.raf.uml.model.UMLRealization;
import edu.raf.uml.model.UMLRelation;

public class RealisationRelationFactory implements RelationFactory {

	@Override
	public String canRelate(UMLBox from, UMLObject to) {
		if (to instanceof UMLInterface)
			return null;
		return "A class can only realize an interface";
	}

	@Override
	public String canRelateFrom(UMLBox from) {
		if (from instanceof UMLClass)
			return null;
		return "Realisation can only start from a class";
	}

	@Override
	public UMLRelation makeRelation(UMLDiagram diagram, UMLBox from,
			UMLObject to) {
		return new UMLRealization(diagram, from, (UMLBox) to);
	}

}
