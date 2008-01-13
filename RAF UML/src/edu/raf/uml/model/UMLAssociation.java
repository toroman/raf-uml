package edu.raf.uml.model;

public class UMLAssociation extends UMLBoxRelation {
	public UMLAssociation (UMLDiagram diagram, UMLBox from, UMLBox to) {
		super(diagram, from, to);
		startNorthString.setVisible(true);
		startSouthString.setVisible(true);
		middleString.setVisible(true);
		endNorthString.setVisible(true);
		endSouthString.setVisible(true);
		startNorthString.setText("1");
		endNorthString.setText("1");
	}	
}
