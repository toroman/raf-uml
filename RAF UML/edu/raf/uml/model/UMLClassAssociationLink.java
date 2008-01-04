package edu.raf.uml.model;

public class UMLClassAssociationLink extends UMLRelationRelation {
    public UMLClassAssociationLink (UMLDiagram diagram, UMLBox from, UMLRelation to) {
        super(diagram, from, to);   
		super.line_dashed = true;
    }
}
