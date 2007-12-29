package edu.raf.uml.gui.tool.factory;

import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLClass;
import edu.raf.uml.model.UMLDiagram;

public class ClassBoxFactory implements BoxFactory {

	@Override
	public UMLBox createUMLBox(UMLDiagram diagram, int x, int y) {
		return new UMLClass (diagram, x, y);
	}

}
