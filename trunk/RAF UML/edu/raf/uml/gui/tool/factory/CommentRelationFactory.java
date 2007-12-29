package edu.raf.uml.gui.tool.factory;

import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLCommentBox;
import edu.raf.uml.model.UMLCommentRelation;
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLObject;
import edu.raf.uml.model.UMLRelation;

public class CommentRelationFactory implements RelationFactory {

	@Override
	public String canRelate(UMLBox from, UMLObject to) {
		return null;
	}

	@Override
	public String canRelateFrom(UMLBox from) {
		if (from instanceof UMLCommentBox)
			return null;
		return "Comment relation may only start from a comment box";
	}

	@Override
	public UMLRelation makeRelation(UMLDiagram diagram, UMLBox from,
			UMLObject to) {
		return new UMLCommentRelation (diagram, from, (UMLRelation)to);
	}

}
