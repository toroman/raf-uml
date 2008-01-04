package edu.raf.uml.gui.tool.factory;

import edu.raf.uml.model.UMLAssociation;
import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLClass;
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLObject;
import edu.raf.uml.model.UMLRelation;

public class AssociationRelationFactory implements RelationFactory {

	@Override
	public String canRelate(UMLBox from, UMLObject to) {
		if (!(to instanceof UMLBox))
			return "Can only associate with a UMLBox object";
		if (!(to instanceof UMLClass))
			return "Can only associate with a UMLClass";
		return null;
	}

	@Override
	public String canRelateFrom(UMLBox from) {
		if (from instanceof UMLClass)
			return null;
		return "Can only associate from a UMLClass";
	}

	@Override
	public UMLRelation makeRelation(UMLDiagram diagram, UMLBox from,
			UMLObject to) {
		return new UMLAssociation (diagram, from, (UMLBox)to);
	}

}
