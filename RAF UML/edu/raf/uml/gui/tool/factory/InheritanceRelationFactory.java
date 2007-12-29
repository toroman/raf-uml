package edu.raf.uml.gui.tool.factory;

import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLClass;
import edu.raf.uml.model.UMLCommentBox;
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
		if (to instanceof UMLCommentBox)
			return "Cannot inherit a comment";
		if (to instanceof UMLBox) {
			if (from.inherits((UMLBox)to))
				return "Circural inheritance detected."; 
			if (((UMLBox)to).inherits(from))
				return "Object already inherited.";
			return null;
		}
		return "Cannot inherit to other than UMLBox";
	 }

	/**
	 * @see edu.raf.uml.gui.tool.factory.RelationFactory#canRelateFrom(edu.raf.uml.model.UMLBox)
	 */
	@Override
	public String canRelateFrom(UMLBox from) {
		if (from instanceof UMLClass)
			return null;
		if (from instanceof UMLCommentBox)
			return "A comment cannot inherit anything";	
		return "Unknown type of UMLBox";
	}

	/**
	 * @see edu.raf.uml.gui.tool.factory.RelationFactory#makeRelation(edu.raf.uml.model.UMLBox, edu.raf.uml.model.UMLObject)
	 */
	@Override
	public UMLRelation makeRelation(UMLDiagram diagram, UMLBox from, UMLObject to) {
		return new UMLInheritance (diagram, from, (UMLBox)to);
	}
}
