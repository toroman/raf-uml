package edu.raf.uml.model;

public class UMLCommentRelationRelation extends UMLRelationRelation {
	public UMLCommentRelationRelation(UMLDiagram diagram, UMLBox from, UMLRelation to) {
		super (diagram, from, to);
		super.lineDashed = true;
	}
}
