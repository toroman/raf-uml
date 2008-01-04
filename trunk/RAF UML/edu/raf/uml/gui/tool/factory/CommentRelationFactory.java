package edu.raf.uml.gui.tool.factory;

import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLCommentBox;
import edu.raf.uml.model.UMLCommentBoxRelation;
import edu.raf.uml.model.UMLCommentRelationRelation;
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLObject;
import edu.raf.uml.model.UMLRelation;

public class CommentRelationFactory implements RelationFactory {

	/**
	 * CommentRelation mozhe da se zavrshi na bilo chemu, bilo to relacija bilo box.
	 */
	@Override
	public String canRelate(UMLBox from, UMLObject to) {
		if (to instanceof UMLCommentBox || to instanceof UMLCommentBoxRelation || to instanceof UMLCommentRelationRelation)
			return "Commenting a comment is dumb.";
		return null;
	}

	/**
	 * CommentRelation mozhe da pochne samo sa UMLCommentBoxa.
	 */
	@Override
	public String canRelateFrom(UMLBox from) {
		if (from instanceof UMLCommentBox)
			return null;
		return "Comment relation may only start from a comment box";
	}
	
	/**
	 * Ova metoda proveri da li se relacija pravi sa kutijom ili sa relacijom, i shodno
	 * tome pravi jedan ili drugi objekat. Ako je prvi sluchaj, onda se pravi novi
	 * UMLCommentRelationRelation. U suprotnom se pravi UMLCommentBoxRelation.
	 * 
	 * @see RelationFactory.makeRelation(UMLDiagram diagram, UMLBox from, UMLObject to)
	 */
	@Override
	public UMLRelation makeRelation(UMLDiagram diagram, UMLBox from,
			UMLObject to) {
		if (to instanceof UMLRelation)
			return new UMLCommentRelationRelation (diagram, from, (UMLRelation)to);
		else
			return new UMLCommentBoxRelation (diagram, from, (UMLBox)to);
	}

}
