package edu.raf.uml.gui.tool.factory;

import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLCommentBox;
import edu.raf.uml.model.UMLDiagram;

public class CommentBoxFactory implements BoxFactory {

	@Override
	public UMLBox createUMLBox(UMLDiagram diagram, int x, int y) {
		return new UMLCommentBox (diagram, x, y);
	}

}
