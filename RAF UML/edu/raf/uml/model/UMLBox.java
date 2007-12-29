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

import edu.raf.uml.gui.util.Draggable;
import edu.raf.uml.gui.util.Focusable;
import edu.raf.uml.gui.util.GuiPoint;
import edu.raf.uml.gui.util.PointContainer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public abstract class UMLBox extends UMLObject implements Focusable, Draggable, PointContainer {

    public int x,  y,  width,  height;
    public GuiPoint nwPoint,  nePoint,  sePoint,  swPoint;
    public ArrayList<UMLRelation> relations;
    private int xoffset,  yoffset;

    public UMLBox(UMLDiagram diagram, int x, int y, int width, int height) {
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

    public boolean inherits(UMLBox other) {
        if (this == other) {
            return true;
        }
        for (UMLRelation relation : relations) {
            if (relation instanceof UMLInheritance && relation.to == this && (relation.from == other || relation.from.inherits(other))) {
                return true;
            }
        }
        return false;
    }

    public abstract int calculateMinHeight();

    public abstract int calculateMinWidth();

    /*
     * PointContainer methods
     */
    public void movePoint(GuiPoint guiPoint, int x, int y) {
        int oldx = this.x;
        int oldy = this.y;
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

    public void pointClicked(GuiPoint guiPoint, Point clickLocation) {

    }

    public void pointDoubleClicked(GuiPoint guiPoint, Point clickLocation) {
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

    public void pointDragStarted(GuiPoint guiPoint, int x, int y) {

    }

    public void pointDragged(GuiPoint guiPoint, int x, int y) {
        this.movePoint(guiPoint, x, y);
    }

    public void pointDragEnded(GuiPoint guiPoint) {

    }

    public void deletePoint(GuiPoint guiPoint) {
        delete();
    }

    /*
     * Focusable methods
     */
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

    public void loseFocus(UMLDiagram diagram) {
        nePoint.setVisible(false);
        sePoint.setVisible(false);
        swPoint.setVisible(false);
        nwPoint.setVisible(false);
    }

    /*
     * Draggable methods
     */
    public void startDrag(int x, int y) {
        xoffset = x - this.x;
        yoffset = y - this.y;
    }

    public void drag(int x, int y) {
        this.x = x - xoffset;
        this.y = y - yoffset;
        calculatePointLocations();
    }

    public void endDrag() {

    }

    /*
     * UMLObject methods
     */
    @Override
    public boolean contains(Point point) {
        if (point.x >= x && point.x <= x + width && point.y >= y && point.y <= y + height) {
            return true;
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        Color tempColor = g.getColor();
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(x, y, width, height);
        g.setColor(tempColor);
    }

    @Override
    public void clickOn(Point point) {

    }

    @Override
    public void DoubleclickOn(Point point) {

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
