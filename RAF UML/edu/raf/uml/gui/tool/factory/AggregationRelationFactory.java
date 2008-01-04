package edu.raf.uml.gui.tool.factory;

import edu.raf.uml.model.UMLAggregation;
import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLClass;
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLObject;
import edu.raf.uml.model.UMLRelation;

public class AggregationRelationFactory implements RelationFactory {

	@Override
	public String canRelate(UMLBox from, UMLObject to) {
		if (!(to instanceof UMLBox))
			return "Can only aggregate a UMLBox object";
		if (!(to instanceof UMLClass))
			return "Can only aggregate with a UMLClass";
		if (from == to)
			return "Cannot aggregate itself";
		return null;
	}

	@Override
	public String canRelateFrom(UMLBox from) {
		if (from instanceof UMLClass)
			return null;
		return "Can only aggregate from a UMLClass";
	}

	@Override
	public UMLRelation makeRelation(UMLDiagram diagram, UMLBox from,
			UMLObject to) {
		return new UMLAggregation (diagram, from, (UMLBox)to);
	}

}
