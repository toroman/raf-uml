package edu.raf.uml.model;

import java.awt.geom.Rectangle2D;

import edu.raf.uml.gui.util.GuiString;
import edu.raf.uml.gui.util.StringContainer;
import edu.raf.uml.model.property.Property;
import edu.raf.uml.model.property.TypeModel;

public class UMLField extends GuiString {
	private TypeModel type;
	private String name = "";
	private VisibilityType visibility = VisibilityType.Default;
	private String modifiers = "";
	transient private String textCache = null;

	public UMLField(UMLDiagram diagram, StringContainer parent) {
		super(diagram);
		this.setParent(parent);
		bounds = new Rectangle2D.Double(0, 0, 0, 0);
		type = new TypeModel("");
		super.recalculateBounds();
	}

	@Override
	public String getText() {
		if (textCache == null)
			textCache = visibility.uml() + " " + type + " " + name + " " + modifiers;
		return textCache ;
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
		textCache = null;
		this.type = type;
		super.recalculateBounds();
		super.diagram.panel.repaint();
	}

	@Property
	public String getName() {
		return name;
	}

	public void setName(String name) {
		textCache = null;
		this.name = name;
		super.recalculateBounds();
		super.diagram.panel.repaint();
	}

	@Property
	public VisibilityType getVisibility() {
		return visibility;
	}

	public void setVisibility(VisibilityType visibilityType) {
		textCache = null;
		this.visibility = visibilityType;
		super.recalculateBounds();
		super.diagram.panel.repaint();
	}

	@Property
	public String getModifiers() {
		return modifiers;
	}

	public void setModifiers(String modifiers) {
		textCache = null;
		this.modifiers = modifiers;
		super.recalculateBounds();
		super.diagram.panel.repaint();
	}
}
