package edu.raf.uml.gui.tool.factory;

import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLClass;
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLInheritance;
import edu.raf.uml.model.UMLInterface;
import edu.raf.uml.model.UMLObject;
import edu.raf.uml.model.UMLRelation;

public class InheritanceRelationFactory implements RelationFactory {

	/**
	 * @see edu.raf.uml.gui.tool.factory.RelationFactory#canRelate(edu.raf.uml.model.UMLBox, edu.raf.uml.model.UMLObject)
	 */	
	@Override
	public String canRelate(UMLBox from, UMLObject to) {
		if (!(to instanceof UMLBox))
			return "Can only inherit a " + (from instanceof UMLInterface ? "interface." : "class");
		if (from instanceof UMLInterface && to instanceof UMLInterface)
			return null;
		if (from instanceof UMLClass && to instanceof UMLClass)
			return null;
		return "Incompatibile types. Whatever.";
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
	 * @see edu.raf.uml.gui.tool.factory.RelationFactory#makeRelation(edu.raf.uml.model.UMLBox, edu.raf.uml.model.UMLObject)
	 */
	@Override
	public UMLRelation makeRelation(UMLDiagram diagram, UMLBox from, UMLObject to) {
		return new UMLInheritance (diagram, from, (UMLBox)to);
	}
}
