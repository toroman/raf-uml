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
import java.awt.Polygon;

public class UMLInheritance extends UMLBoxRelation {

    public UMLInheritance(UMLDiagram diagram, UMLBox from, UMLBox to) {
        super(diagram, from, to);        
    }

    @Override
    public void paint(Graphics2D g) {
    	super.paint(g);
        Color tempColor = g.getColor();
        Polygon polygon;

        if (to.y > points.getLast().getY()) {
            // Zavrshava se gore
            polygon = new Polygon(
                    new int[]{(int)points.getLast().getX() - 5, (int)points.getLast().getX() + 5, (int)points.getLast().getX()},
                    new int[]{(int)to.y - 10, (int)to.y - 10, (int)to.y - 1},
                    3);
        } else if (to.x > points.getLast().getX()) {
            // Zavrshava se levo
            polygon = new Polygon(
                    new int[]{(int)to.x - 10, (int)to.x - 1, (int)to.x - 10},
                    new int[]{(int)points.getLast().getY() - 5, (int)points.getLast().getY(), (int)points.getLast().getY() + 5},
                    3);
        } else if (to.y + to.height < points.getLast().getY()) {
            // Zavrshava se na dole
            polygon = new Polygon(
                    new int[]{(int)points.getLast().getX() - 5, (int)points.getLast().getX(), (int)points.getLast().getX() + 5},
                    new int[]{(int)to.y + (int)to.height + 10, (int)to.y + (int)to.height + 1, (int)to.y + (int)to.height + 10},
                    3);
        } else {
            // Zavrshava se desno
            polygon = new Polygon(
                    new int[]{(int)to.x + (int)to.width + 10, (int)to.x + (int)to.width + 1, (int)to.x + (int)to.width + 10},
                    new int[]{(int)points.getLast().getY() - 5, (int)points.getLast().getY(), (int)points.getLast().getY() + 5},
                    3);
        }
        g.setColor(Color.WHITE);
        g.fillPolygon(polygon);
        g.setColor(Color.BLACK);
        g.drawPolygon(polygon);

        g.setColor(tempColor);
    }
}
