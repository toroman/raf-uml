package edu.raf.uml.gui.tool.factory;

import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLInterface;

public class InterfaceBoxFactory implements BoxFactory {

	@Override
	public UMLBox createUMLBox(UMLDiagram diagram, int x, int y) {
		return new UMLInterface (diagram, x, y);
	}

}
