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
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import edu.raf.uml.gui.util.GuiString;
import edu.raf.uml.model.property.Property;

public class UMLInterface extends UMLBox {
	public GuiString interfaceName;
	public ArrayList<UMLMethod> methods;
	public static FontMetrics fontMetrics;
	private transient int titleWidth = -1; // ;)

	public UMLInterface(UMLDiagram diagram, double x, double y) {
		super(diagram, x, y, 0, 0);
		methods = new ArrayList<UMLMethod>();
		interfaceName = new GuiString(diagram, this);
		interfaceName.setText("NewInterface");
		interfaceName.setVisible(true);
		this.movePoint(sePoint, -1, -1);
	}

	public void addMethod() {
		UMLMethod method = new UMLMethod(diagram, this);
		methods.add(method);
		method.setVisible(true);
		method.setName("method");
		calculatePointLocations();
		diagram.giveFocus(this);
	}

	@Override
	public double calculateMinHeight() {
		if (interfaceName != null)
			return (interfaceName.getBounds().height + 2)
					* (1 + Math.max(methods.size(), 1)) + 24;
		else
			return 10;
	}

	@Override
	public double calculateMinWidth() {
		double minw;
		if (interfaceName != null)
			minw = interfaceName.calculateMinWidth();
		else
			minw = 0;
		for (GuiString kme : methods)
			if (kme.getBounds().width > minw)
				minw = kme.calculateMinWidth();
		return (minw < titleWidth ? titleWidth : minw)
				+ RIGHT_BLANK_SPACE_WIDTH;
	}

	@Override
	public void paint(Graphics2D g) {
		super.paint(g);
		Color tempColor = g.getColor();
		g.setColor(Color.BLACK);
		String s = "<< interface >>";
		if (titleWidth < 0)
			titleWidth = g.getFontMetrics().stringWidth(s);
		g.drawString(s, (int) (x + width / 2) - titleWidth / 2, (int) y + 15);
		g.drawLine((int) x, (int) y + (int) interfaceName.getBounds().height
				+ 22, (int) x + (int) width, (int) y
				+ (int) interfaceName.getBounds().height + 22);
		g.setColor(tempColor);
	}

	@Override
	public void gainFocus(UMLDiagram diagram) {
		super.gainFocus(diagram);
		diagram.moveForward(interfaceName);
		for (GuiString kme : methods) {
			diagram.moveForward(kme);
		}
	}

	@Override
	public void loseFocus(UMLDiagram diagram) {
		super.loseFocus(diagram);
	}

	@Override
	public void delete() {
		super.delete();
		interfaceName.delete(false);
		for (GuiString kme : methods) {
			kme.delete(false);
		}
	}

	@Override
	public void calculatePointLocations() {
		super.calculatePointLocations();
		if (interfaceName != null) {
			interfaceName.setX(x + (width - interfaceName.getBounds().width)
					/ 2);
			interfaceName.setY(y + 18);
			for (int i = 0; i < methods.size(); i++) {
				methods.get(i).setX(x + 3);
				methods
						.get(i)
						.setY(
								y
										+ 44
										+ i
										* (interfaceName.getBounds().height + 2)
										+ 3);
				methods.get(i).setWidth(width - RIGHT_BLANK_SPACE_WIDTH);
			}
		}
	}

	@Override
	public void dblClickOn(Point2D.Double point) {
		if (point.y > y + interfaceName.getBounds().height + 22)
			addMethod();
		super.dblClickOn(point);
	}

	@Override
	public void deleteString(GuiString guiString) {
		if (methods.contains(guiString)) {
			methods.remove(guiString);
			guiString.delete(false);
			calculatePointLocations();
			return;
		} else
			delete();
	}

	@Override
	public void stringTextChanged(GuiString guiString) {
		if (guiString != interfaceName && guiString.getText().equals(""))
			guiString.delete();
		super.stringTextChanged(guiString);
	}

	@Property
	public String getName() {
		return this.interfaceName.getText();
	}

	public void setName(String name) {
		if (!name.matches("[A-z0-9_$]+"))
			throw new IllegalArgumentException(
					"Ime klase mora biti od slova, brojeva i _$");
		this.interfaceName.setText(name);
		diagram.panel.repaint();
	}

	protected static final int RIGHT_BLANK_SPACE_WIDTH = 17;

}
