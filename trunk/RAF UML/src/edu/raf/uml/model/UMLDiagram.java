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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import edu.raf.uml.gui.DiagramPanel;
import edu.raf.uml.gui.util.Focusable;
import edu.raf.uml.model.property.Property;

public class UMLDiagram {
	private String title = "Untitled";
	public ArrayList<UMLObject> objects;
	public Focusable onFocus;
	public DiagramPanel panel;
	private int maxWidth = 3000;
	private int maxHeight = 3000;
	
	public UMLDiagram(DiagramPanel panel) {
		objects = new ArrayList<UMLObject>();
		onFocus = null;
		this.panel = panel;
	}

	public void addObject(UMLObject object) {
		objects.add(object);
	}

	public void removeObject(UMLObject object) {
		objects.remove(object);
	}

	public void giveFocus(Focusable fobject) {
		if (fobject == null) {
			panel.gui.propertiesPanel.setObject(this);
			if (onFocus != null) {
				onFocus.loseFocus(this);
			}
			onFocus = null;
		} else {
			panel.gui.propertiesPanel.setObject(fobject);
			if (onFocus != null) {
				onFocus.loseFocus(this);
			}
			if (fobject != null) {
				fobject.gainFocus(this);
			}
			onFocus = fobject;
		}
	}

	@Property
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		panel.repaint();
	}

	public void paint(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		g.drawString("RAFUML v0.1", 10, 20);
		g.drawString("Authors: Ivan Bocic, Srecko Toroman, Sasa Sijak", 10, 40);
		g.drawString("Title: " + getTitle(), 10, 60);
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).paint(g);
		}
	}

	public UMLObject getObjectAt(Point2D.Double point) {
		for (int i = objects.size() - 1; i >= 0; i--) {
			if (objects.get(i).contains(point)) {
				return objects.get(i);
			}
		}
		return null;
	}

	public UMLObject getContainerObjectAt(Point2D.Double point) {
		for (int i = objects.size() - 1; i >= 0; i--) {
			if (objects.get(i).contains(point)
					&& (objects.get(i) instanceof UMLBox || objects.get(i) instanceof UMLRelation)) {
				return objects.get(i);
			}
		}
		return null;
	}

	public void moveForward(UMLObject object) {
		objects.add(object);
		objects.remove(object);
	}

	public int getMaxHeight() {
		return maxHeight;
	}
	
	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}
	
	public int getMaxWidth() {
		return maxWidth;
	}
	
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}
	
}
