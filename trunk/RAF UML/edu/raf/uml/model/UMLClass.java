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
import java.awt.Graphics;
import java.util.ArrayList;

public class UMLClass extends UMLBox {

    public String className;
    public ArrayList<String> methods,  fields;
    public static FontMetrics fontMetrics;

    public UMLClass(UMLDiagram diagram, int x, int y) {
        super(diagram, x, y, 0, 0);
        className = "";
        methods = new ArrayList<String>();
        fields = new ArrayList<String>();

        this.movePoint(sePoint, -1, -1);
    }

    @Override
    public int calculateMinHeight() {
        return 100;
    }

    @Override
    public int calculateMinWidth() {
//		System.out.println(diagram.graphics.getFontMetrics());
//		int minStrWidth = diagram.graphics.getFontMetrics().stringWidth(className);
//		for (String method: methods) {
//			int temp = diagram.graphics.getFontMetrics().stringWidth(method);
//			if (temp > minStrWidth)
//				minStrWidth = temp;
//		}
//		for (String field: fields) {
//			int temp = diagram.graphics.getFontMetrics().stringWidth(field);
//			if (temp > minStrWidth)
//				minStrWidth = temp;
//		}			
//		return minStrWidth + 4;
        return 100;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Color tempColor = g.getColor();
        g.setColor(Color.BLACK);
        g.drawString(className, x + 3, y + 15);
        g.setColor(tempColor);
    }
}
