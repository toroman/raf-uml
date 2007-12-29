package edu.raf.uml.gui.tool.factory;

import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLDiagram;

public interface BoxFactory {
	public UMLBox createUMLBox (UMLDiagram diagram, int x, int y);
}
