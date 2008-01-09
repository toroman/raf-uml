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

package edu.raf.uml.gui.properties;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

/**
 * Prikazuje ime propertija.
 * 
 * @author Srecko Toroman
 * 
 */
class PropertyName extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3412741687219338139L;
	static final Dimension DIMENSION = new Dimension(100, 22);
	private final PropertyPair parent;

	public PropertyName(PropertyPair parent) {
		super();
		this.parent = parent;
	}

	@Override
	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		g.setColor(Color.LIGHT_GRAY);
		Rectangle c = g.getClipBounds();
		g.fill3DRect(0, 0, c.width, c.height, true);
		g.setColor(Color.BLACK);
		g.setFont(Font.decode("Monospaced"));
		g.drawString(parent.getTitle(), 2, 14);
	}

}