package edu.raf.uml.model;

import java.awt.geom.Point2D.Double;
import java.util.List;

import edu.raf.uml.gui.util.GuiString;
import edu.raf.uml.gui.util.StringContainer;
import edu.raf.uml.model.property.ArgumentModel;
import edu.raf.uml.model.property.Property;
import edu.raf.uml.model.property.TypeModel;

public class UMLMethod extends GuiString {
	private TypeModel type;
	private String name = "";
	private VisibilityType visibility = VisibilityType.Default;
	private String modifiers = "";
	private List<ArgumentModel> arguments;
	
	public UMLMethod(UMLDiagram diagram, StringContainer parent) {
		super(diagram, parent);
	}

	@Override
	public void DoubleclickOn(Double point) {
		// nista ne radi
	}

	@Override
	public String getText() {
		/*
		 * vis moze biti null jer prilikom konstrukcije objekta poziva se
		 * getText dok visibility jos nije ni inicijalizovan
		 */
		String vis = visibility == null ? VisibilityType.Default.uml()
				: visibility.uml();
		String t = type == null ? "" : type.toString();
		return vis + " " + t + " " + name + " " + modifiers;
	}

	@Override
	public void setText(String text) {
		throw new RuntimeException("UML Field ne treba koristiti sa setText");
	}

	@Property
	public TypeModel getType() {
		return type;
	}

	public void setType(TypeModel type) {
		this.type = type;
		super.recalculateBounds();
		super.diagram.panel.repaint();
	}

	@Property
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		super.recalculateBounds();
		super.diagram.panel.repaint();
	}

	@Property
	public VisibilityType getVisibility() {
		return visibility;
	}

	public void setVisibility(VisibilityType visibilityType) {
		this.visibility = visibilityType;
		super.recalculateBounds();
		super.diagram.panel.repaint();
	}

	@Property
	public String getModifiers() {
		return modifiers;
	}

	public void setModifiers(String modifiers) {
		this.modifiers = modifiers;
		super.recalculateBounds();
		super.diagram.panel.repaint();
	}
}
