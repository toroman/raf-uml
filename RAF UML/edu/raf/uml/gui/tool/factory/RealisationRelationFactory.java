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
		return new UMLRealization (diagram, from, (UMLBox)to);
	}

}
