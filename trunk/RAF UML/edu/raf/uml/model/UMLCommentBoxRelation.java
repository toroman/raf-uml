package edu.raf.uml.model;

public class UMLCommentBoxRelation extends UMLBoxRelation {
    public UMLCommentBoxRelation (UMLDiagram diagram, UMLBox from, UMLBox to) {
        super(diagram, from, to);   
		super.line_dashed = true;
    }
}

