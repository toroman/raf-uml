/*
RAF UML - Student project for Object oriented programming and design
Copyright (C) <2007>  Ivan Bocic, Sasa Sijak, Srecko Toroman

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.raf.uml.model;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
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
	private String textCache = null;

	public UMLMethod(UMLDiagram diagram, StringContainer parent) {
		super(diagram);
		this.setParent(parent);
		bounds = new Rectangle2D.Double(0, 0, 0, 0);
		arguments = new ArrayList<ArgumentModel>();
		type = new TypeModel("");
		super.recalculateBounds();
	}

	@Override
	public String getText() {
		if (textCache != null)
			return textCache;

		StringBuilder sb = new StringBuilder();
		sb.append(visibility.uml());
		sb.append(' ');
		sb.append(type.toString());
		sb.append(' ');
		sb.append(name);
		sb.append(' ');
		sb.append('(');
		for (ArgumentModel arg : arguments) {
			sb.append(arg.toString());
			sb.append(',');
		}
		if (arguments.size() > 0)
			sb.setCharAt(sb.length() - 1, ')');
		else
			sb.append(')');
		sb.append(modifiers);
		return textCache = sb.toString();
	}

	@Override
	public void setText(String text) {
		throw new RuntimeException("UML Method ne treba koristiti sa setText");
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

	@Property(type = ArgumentModel.class)
	public List<ArgumentModel> getArguments() {
		return arguments;
	}

	public void setArguments(List<ArgumentModel> arguments) {
		textCache = null;
		this.arguments = arguments;
		super.recalculateBounds();
		super.diagram.panel.repaint();
	}
}
