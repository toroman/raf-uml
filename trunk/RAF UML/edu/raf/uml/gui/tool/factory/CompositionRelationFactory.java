package edu.raf.uml.gui.tool.factory;

import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLClass;
import edu.raf.uml.model.UMLComposition;
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLObject;
import edu.raf.uml.model.UMLRelation;

public class CompositionRelationFactory implements RelationFactory {

	@Override
	public String canRelate(UMLBox from, UMLObject to) {
		if (!(to instanceof UMLBox))
			return "Can only composite a UMLBox object";
		if (!(to instanceof UMLClass))
			return "Can only composite with a UMLClass";
		if (from == to)
			return "Cannot composite itself";
		return null;
	}

	@Override
	public String canRelateFrom(UMLBox from) {
		if (from instanceof UMLClass)
			return null;
		return "Can only composite from a UMLClass";
	}

	@Override
	public UMLRelation makeRelation(UMLDiagram diagram, UMLBox from,
			UMLObject to) {
		return new UMLComposition (diagram, from, (UMLBox)to);
	}

}
