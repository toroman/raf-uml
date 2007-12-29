package edu.raf.uml.model;

public class UMLCommentRelation extends UMLRelationRelation {
	public UMLCommentRelation(UMLDiagram diagram, UMLBox from, UMLRelation to) {
		super (diagram, from, to);
		super.line_dashed = true;
	}
}
