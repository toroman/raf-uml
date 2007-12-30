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
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import edu.raf.uml.gui.util.Focusable;
import edu.raf.uml.gui.util.GuiPoint;
import edu.raf.uml.gui.util.MathUtil;
import edu.raf.uml.gui.util.PointContainer;

public abstract class UMLRelation extends UMLObject implements PointContainer, Focusable {

    public LinkedList<GuiPoint> points;
    public ArrayList <UMLRelationRelation> relations;
    public static final double CLICK_MISS_DISTANCE = 5;
    public static final int DISTANCE_FROM_UMLBOX = 20;
    protected boolean line_dashed;

    public UMLRelation(UMLDiagram diagram) {
        super(diagram);
        line_dashed = false;
        points = new LinkedList<GuiPoint>();
        relations = new ArrayList<UMLRelationRelation>();
        diagram.giveFocus(this);
    }

    public void calculatePointLocations() {
    	
    }

    /*
     * Focusable methods
     */
    @Override
    public void gainFocus(UMLDiagram diagram) {
        diagram.moveForward(this);
        for (GuiPoint point : points) {
            point.setVisible(true);
            diagram.moveForward(point);
        }
    }

    @Override
    public void loseFocus(UMLDiagram diagram) {
        for (GuiPoint point : points) {
            point.setVisible(false);
        }
    }

    /*
     * PointContainer methods
     */
    @Override
    public void movePoint(GuiPoint guiPoint, double x, double y) {
        guiPoint.x = x;
        guiPoint.y = y;
        calculatePointLocations();
    }

    @Override
    public void pointClicked(GuiPoint guiPoint, Point2D.Double clickLocation) {

    }

    @Override
    public void pointDoubleClicked(GuiPoint guiPoint, Point2D.Double clickLocation) {
        if (guiPoint != points.getFirst() && guiPoint != points.getLast()) {
            guiPoint.delete();
        }
    }

    @Override
    public void pointDragEnded(GuiPoint guiPoint) {

    }

    @Override
    public void pointDragged(GuiPoint guiPoint, double x, double y) {
        this.movePoint(guiPoint, x, y);
    }

    @Override
    public void pointDragStarted(GuiPoint guiPoint, double x, double y) {

    }

    @Override
    public void deletePoint(GuiPoint guiPoint) {
        if (guiPoint != points.getFirst() && guiPoint != points.getLast()) {
            points.remove(guiPoint);
        } else {
            delete();
        }
    }
    
    @Override
    public Cursor giveCursorTo(GuiPoint guiPoint) {
    	return MOVE_CURSOR;
    }

    /*
     * UMLObject methods
     */
    
    @Override
    public void paint(Graphics2D g) {
        Color tempColor = g.getColor();
        GuiPoint tempPoint1, tempPoint2;

        g.setColor(Color.BLACK);
        Iterator<GuiPoint> point1 = points.iterator();
        Iterator<GuiPoint> point2 = points.iterator();
        point2.next();
        while (point2.hasNext()) {
            tempPoint1 = point1.next();
            tempPoint2 = point2.next();
            g.drawLine((int)tempPoint1.x, (int)tempPoint1.y, (int)tempPoint2.x, (int)tempPoint2.y);
        }
 
        g.setColor(tempColor);
    }
    
    @Override
    public void DoubleclickOn(Double point) {
        GuiPoint tempPoint1, tempPoint2;
        Point2D.Double projectionPoint;
        Iterator<GuiPoint> point1 = points.iterator();
        Iterator<GuiPoint> point2 = points.iterator();
        point2.next();
        int where = 1;
        while (point2.hasNext()) {
            tempPoint1 = point1.next();
            tempPoint2 = point2.next();
            double r = MathUtil.getProjectionr(tempPoint1.x, tempPoint1.y, tempPoint2.x, tempPoint2.y, point.x, point.y);
            projectionPoint = MathUtil.getProjectionPoint(tempPoint1.x, tempPoint1.y, tempPoint2.x, tempPoint2.y, r);
            if (r >= 0 && r <= 1 && MathUtil.pointDistance(projectionPoint, point) <= CLICK_MISS_DISTANCE) {
                points.add(where, new GuiPoint(diagram, this, projectionPoint.x, projectionPoint.y));
                points.get(where).setVisible(true);
                return;
            }
            where++;
        }
    }

    @Override
    public void clickOn(Point2D.Double point) {

    }

    @Override
    public boolean contains(Point2D.Double argumentPoint) {
        GuiPoint tempPoint1, tempPoint2;
        Point2D.Double projectionPoint;
        Iterator<GuiPoint> point1 = points.iterator();
        Iterator<GuiPoint> point2 = points.iterator();
        point2.next();
        while (point2.hasNext()) {
            tempPoint1 = point1.next();
            tempPoint2 = point2.next();
            if (MathUtil.pointDistance(tempPoint1.toPoint(), argumentPoint) <= CLICK_MISS_DISTANCE) {
                return true;
            }
            double r = MathUtil.getProjectionr(tempPoint1.x, tempPoint1.y, tempPoint2.x, tempPoint2.y, argumentPoint.x, argumentPoint.y);
            projectionPoint = MathUtil.getProjectionPoint(tempPoint1.x, tempPoint1.y, tempPoint2.x, tempPoint2.y, r);
            if (r >= 0 && r <= 1 && MathUtil.pointDistance(projectionPoint, argumentPoint) <= CLICK_MISS_DISTANCE) {
                return true;
            }
        }
        if (MathUtil.pointDistance(points.getLast().toPoint(), argumentPoint) <= CLICK_MISS_DISTANCE) {
            return true;
        }
        return false;
    }

    @Override
    public void delete() {
    	delete (true);
    }
    
    public void delete (boolean notifyParents) {
        super.delete();
        Iterator<GuiPoint> iter = points.iterator();
        GuiPoint point;
        while (iter.hasNext()) {
            point = iter.next();
            if (diagram.objects.contains(point)) {
                point.delete(false);
            }
        }
        for (UMLRelationRelation relation: relations)
        	relation.delete();
    }
}
