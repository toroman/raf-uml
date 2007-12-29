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
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import edu.raf.uml.gui.util.Draggable;
import edu.raf.uml.gui.util.Focusable;
import edu.raf.uml.gui.util.GuiPoint;
import edu.raf.uml.gui.util.PointContainer;

public abstract class UMLBox extends UMLObject implements Focusable, Draggable, PointContainer {

    public double x,  y,  width,  height;
    public GuiPoint nwPoint,  nePoint,  sePoint,  swPoint;
    public ArrayList<UMLRelation> relations;
    private double xoffset,  yoffset;

    public UMLBox(UMLDiagram diagram, double x, double y, double width, double height) {
        super(diagram);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        relations = new ArrayList<UMLRelation>();
        nwPoint = new GuiPoint(diagram, this, x, y);
        nePoint = new GuiPoint(diagram, this, x + width, y);
        swPoint = new GuiPoint(diagram, this, x, y + height);
        sePoint = new GuiPoint(diagram, this, x + width, y + height);
    }

    public void addRelation(UMLRelation relation) {
        relations.add(relation);
    }

    public void calculatePointLocations() {
        nwPoint.x = x;
        nwPoint.y = y;
        nePoint.x = x + width;
        nePoint.y = y;
        swPoint.x = x;
        swPoint.y = y + height;
        sePoint.x = x + width;
        sePoint.y = y + height;
        for (UMLRelation relation : relations) {
            relation.calculatePointLocations();
        }
    }

    public abstract int calculateMinHeight();

    public abstract int calculateMinWidth();

    /*
     * PointContainer methods
     */
    @Override
    public void movePoint(GuiPoint guiPoint, double x, double y) {
        double oldx = this.x;
        double oldy = this.y;
        if (guiPoint == nwPoint) {
            this.x = Math.min(x, this.x + this.width - this.calculateMinWidth());
            this.width = oldx + width - this.x;
            this.y = Math.min(y, this.y + this.height - this.calculateMinHeight());
            this.height = oldy + height - this.y;
        } else if (guiPoint == nePoint) {
            this.width = Math.max(this.calculateMinWidth(), x - this.x);
            this.y = Math.min(y, this.y + this.height - this.calculateMinHeight());
            this.height = oldy + height - this.y;
        } else if (guiPoint == swPoint) {
            this.x = Math.min(x, this.x + this.width - this.calculateMinWidth());
            this.width = oldx + width - this.x;
            this.height = Math.max(this.calculateMinHeight(), y - this.y);
        } else if (guiPoint == sePoint) {
            this.width = Math.max(this.calculateMinWidth(), x - this.x);
            this.height = Math.max(this.calculateMinHeight(), y - this.y);
        }
        calculatePointLocations();
        return;
    }

    @Override
    public void pointClicked(GuiPoint guiPoint, Point2D.Double clickLocation) {

    }

    @Override
    public void pointDoubleClicked(GuiPoint guiPoint, Point2D.Double clickLocation) {
        if (guiPoint == nwPoint) {
            movePoint(guiPoint, Integer.MAX_VALUE, Integer.MAX_VALUE);
        } else if (guiPoint == nePoint) {
            movePoint(guiPoint, -1, Integer.MAX_VALUE);
        } else if (guiPoint == swPoint) {
            movePoint(guiPoint, Integer.MAX_VALUE, -1);
        } else if (guiPoint == sePoint) {
            movePoint(guiPoint, -1, -1);
        }
    }

    @Override
    public void pointDragStarted(GuiPoint guiPoint, double x, double y) {

    }

    @Override
    public void pointDragged(GuiPoint guiPoint, double x, double y) {
        this.movePoint(guiPoint, x, y);
    }

    @Override
    public void pointDragEnded(GuiPoint guiPoint) {

    }

    @Override
    public void deletePoint(GuiPoint guiPoint) {
        delete();
    }

    /*
     * Focusable methods
     */
    @Override
    public void gainFocus(UMLDiagram diagram) {
        nePoint.setVisible(true);
        sePoint.setVisible(true);
        swPoint.setVisible(true);
        nwPoint.setVisible(true);
        diagram.moveForward(this);
        diagram.moveForward(nePoint);
        diagram.moveForward(sePoint);
        diagram.moveForward(swPoint);
        diagram.moveForward(nwPoint);
    }

    @Override
    public void loseFocus(UMLDiagram diagram) {
        nePoint.setVisible(false);
        sePoint.setVisible(false);
        swPoint.setVisible(false);
        nwPoint.setVisible(false);
    }

    /*
     * Draggable methods
     */
    @Override
    public void startDrag(double x, double y) {
        xoffset = x - this.x;
        yoffset = y - this.y;
    }

    @Override
    public void drag(double x, double y) {
        this.x = x - xoffset;
        this.y = y - yoffset;
        calculatePointLocations();
    }

    @Override
    public void endDrag() {

    }

    /*
     * UMLObject methods
     */
    @Override
    public boolean contains(Point2D.Double point) {
        if (point.x >= x && point.x <= x + width && point.y >= y && point.y <= y + height) {
            return true;
        }
        return false;
    }

    @Override
    public void paint(Graphics2D g) {
        Color tempColor = g.getColor();
        g.setColor(Color.WHITE);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
        g.setColor(Color.DARK_GRAY);
        g.drawRect((int)x, (int)y, (int)width, (int)height);
        g.setColor(tempColor);
    }

    @Override
    public void clickOn(Point2D.Double point) {

    }

    @Override
    public void DoubleclickOn(Double point) {

    }

    @Override
    public void delete() {
        super.delete();
        for (UMLRelation relation: relations)
        	relation.delete(false);
        if (diagram.objects.contains(nwPoint)) {
            nwPoint.delete(false);
        }
        if (diagram.objects.contains(swPoint)) {
            swPoint.delete(false);
        }
        if (diagram.objects.contains(sePoint)) {
            sePoint.delete(false);
        }
        if (diagram.objects.contains(nePoint)) {
            nePoint.delete(false);
        }
    }
}