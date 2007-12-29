package edu.raf.uml.gui.tool.factory;

import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLInheritance;
import edu.raf.uml.model.UMLObject;
import edu.raf.uml.model.UMLRelation;

public class InheritanceRelationFactory implements RelationFactory {

	/**
	 * @see edu.raf.uml.gui.tool.factory.RelationFactory#canRelate(edu.raf.uml.model.UMLBox, edu.raf.uml.model.UMLObject)
	 */	
	@Override
	public String canRelate(UMLBox from, UMLObject to) {
		if (from.inherits((UMLBox) to))
			return "Circural inheritance detected."; 
	    if (((UMLBox) to).inherits(from))
	        return "Object already inherited.";
	    return null;
	 }

	/**
	 * @see edu.raf.uml.gui.tool.factory.RelationFactory#canRelateFrom(edu.raf.uml.model.UMLBox)
	 */
	@Override
	public String canRelateFrom(UMLBox from) {
		return null;
	}

	/**
	 * @see edu.raf.uml.gui.tool.factory.RelationFactory#makeRelation(edu.raf.uml.model.UMLBox, edu.raf.uml.model.UMLObject)
	 */
	@Override
	public UMLRelation makeRelation(UMLDiagram diagram, UMLBox from, UMLObject to) {
		return new UMLInheritance (diagram, from, (UMLBox)to);
	}
}
