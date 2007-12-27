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
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Iterator;

import edu.raf.uml.gui.util.GuiPoint;

public class UMLInheritance extends UMLRelation {

    public UMLInheritance(UMLDiagram diagram, UMLBox from, UMLBox to) {
        super(diagram, from, to);
    }

    @Override
    public void paint(Graphics g) {
        Color tempColor = g.getColor();
        GuiPoint tempPoint1, tempPoint2;
        Polygon polygon;

        g.setColor(Color.BLACK);
        if (from.y > points.getFirst().y) {
            // Pochinje na gore
            g.drawLine(points.getFirst().x, points.getFirst().y, points.getFirst().x, from.y);
        } else if (from.x > points.getFirst().x) {
            // Pochinje na levo
            g.drawLine(points.getFirst().x, points.getFirst().y, from.x, points.getFirst().y);
        } else if (from.y + from.height < points.getFirst().y) {
            // Pochinje na dole
            g.drawLine(points.getFirst().x, points.getFirst().y, points.getFirst().x, from.y + from.height);
        } else if (from.x + from.width < points.getFirst().x) {
            // Pochinje na desno
            g.drawLine(points.getFirst().x, points.getFirst().y, from.x + from.width, points.getFirst().y);
        }
        Iterator<GuiPoint> point1 = points.iterator();
        Iterator<GuiPoint> point2 = points.iterator();
        point2.next();
        while (point2.hasNext()) {
            tempPoint1 = point1.next();
            tempPoint2 = point2.next();
            g.drawLine(tempPoint1.x, tempPoint1.y, tempPoint2.x, tempPoint2.y);
        }
        if (to.y > points.getLast().y) {
            // Zavrshava se gore
            g.drawLine(points.getLast().x, points.getLast().y, points.getLast().x, to.y);
            polygon = new Polygon(
                    new int[]{points.getLast().x - 5, points.getLast().x + 5, points.getLast().x},
                    new int[]{to.y - 10, to.y - 10, to.y - 1},
                    3);
        } else if (to.x > points.getLast().x) {
            // Zavrshava se levo
            polygon = new Polygon(
                    new int[]{to.x - 10, to.x - 1, to.x - 10},
                    new int[]{points.getLast().y - 5, points.getLast().y, points.getLast().y + 5},
                    3);
            g.drawLine(points.getLast().x, points.getLast().y, to.x, points.getLast().y);
        } else if (to.y + to.height < points.getLast().y) {
            // Zavrshava se na dole
            g.drawLine(points.getLast().x, points.getLast().y, points.getLast().x, to.y + to.height);
            polygon = new Polygon(
                    new int[]{points.getLast().x - 5, points.getLast().x, points.getLast().x + 5},
                    new int[]{to.y + to.height + 10, to.y + to.height + 1, to.y + to.height + 10},
                    3);
        } else {
            // Zavrshava se desno
            g.drawLine(points.getLast().x, points.getLast().y, to.x + to.width, points.getLast().y);
            polygon = new Polygon(
                    new int[]{to.x + to.width + 10, to.x + to.width + 1, to.x + to.width + 10},
                    new int[]{points.getLast().y - 5, points.getLast().y, points.getLast().y + 5},
                    3);
        }
        g.setColor(Color.WHITE);
        g.fillPolygon(polygon);
        g.setColor(Color.BLACK);
        g.drawPolygon(polygon);

        g.setColor(tempColor);
    }
}
