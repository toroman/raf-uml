package edu.raf.uml.gui.tool.factory;

import edu.raf.uml.model.UMLAssociation;
import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLClass;
import edu.raf.uml.model.UMLClassAssociationLink;
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLObject;
import edu.raf.uml.model.UMLRelation;

public class AssociationClassFactory implements RelationFactory {

	@Override
	public String canRelate(UMLBox from, UMLObject to) {
		return new AssociationRelationFactory().canRelate(from, to);
	}

	@Override
	public String canRelateFrom(UMLBox from) {
		return new AssociationRelationFactory().canRelateFrom(from);
	}

	@Override
	public UMLRelation makeRelation(UMLDiagram diagram, UMLBox from,
			UMLObject to) {
		UMLAssociation newAssoc = new UMLAssociation (diagram, from, (UMLBox)to);
		UMLClass newClass = new UMLClass (diagram,
				(newAssoc.points.getFirst().x + newAssoc.points.getLast().x)/2 - 40,
				(newAssoc.points.getLast().y + newAssoc.points.getFirst().y)/2 + 120);
		new UMLClassAssociationLink (diagram, newClass, newAssoc);
		return newAssoc;
	}

}
