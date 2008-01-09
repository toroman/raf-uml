package edu.raf.uml.model;

import java.awt.geom.Point2D.Double;

import edu.raf.uml.gui.util.GuiString;
import edu.raf.uml.gui.util.StringContainer;
import edu.raf.uml.model.property.Property;

public class UMLField extends GuiString {
	private String type = "";
	private String name = "";
	private VisibilityType visibility = VisibilityType.Default;
	private String modifiers = "";

	public UMLField(UMLDiagram diagram, StringContainer parent) {
		super(diagram, parent);
	}

	@Override
	public void DoubleclickOn(Double point) {
		// nista ne radi
	}

	@Override
	public String getText() {
		/* 
		 * vis moze biti null jer prilikom konstrukcije objekta poziva se getText
		 * dok visibility jos nije ni inicijalizovan
		 */
		VisibilityType vis = visibility == null ? VisibilityType.Default
				: visibility;
		return vis.uml() + " " + type + " " + name + " " + modifiers;
	}

	@Override
	public void setText(String text) {
		throw new RuntimeException("UML Field ne treba koristiti sa setText");
	}

	@Property
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		diagram.panel.repaint();
	}

	@Property
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		diagram.panel.repaint();
	}

	@Property
	public VisibilityType getVisibility() {
		return visibility;
	}

	public void setVisibility(VisibilityType visibilityType) {
		this.visibility = visibilityType;
		diagram.panel.repaint();
	}

	@Property
	public String getModifiers() {
		return modifiers;
	}

	public void setModifiers(String modifiers) {
		this.modifiers = modifiers;
		diagram.panel.repaint();
	}
}
