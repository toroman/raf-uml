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

import edu.raf.uml.gui.util.Focusable;
import edu.raf.uml.gui.util.GuiPoint;
import edu.raf.uml.gui.util.MathUtil;
import edu.raf.uml.gui.util.PointContainer;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class UMLRelation extends UMLObject implements PointContainer, Focusable {

    public UMLBox from,  to;
    public LinkedList<GuiPoint> points;
    public static final double CLICK_MISS_DISTANCE = 5;
    public static final int DISTANCE_FROM_UMLBOX = 20;

    public UMLRelation(UMLDiagram diagram, UMLBox from, UMLBox to) {
        super(diagram);
        this.from = from;
        this.to = to;
        from.addRelation(this);
        to.addRelation(this);
        points = new LinkedList<GuiPoint>();
        points.addFirst(new GuiPoint(diagram, this, from.x + from.width / 2, from.y - DISTANCE_FROM_UMLBOX));
        points.addLast(new GuiPoint(diagram, this, to.x + to.width / 2, to.y + +to.height + DISTANCE_FROM_UMLBOX));
        diagram.giveFocus(this);
    }

    public void calculatePointLocations() {
        int midx = from.x + from.width / 2;
        int eastx = from.x + from.width;
        int midy = from.y + from.height / 2;
        int southy = from.y + from.height;
        if ((points.getFirst().y <= midy) && (points.getFirst().x - from.x >= points.getFirst().y - from.y) && (eastx - points.getFirst().x >= points.getFirst().y - from.y)) {
            // Gornji kvadrant
            points.getFirst().x = MathUtil.getBetween(points.getFirst().x, from.x, eastx);
            points.getFirst().y = from.y - DISTANCE_FROM_UMLBOX;
        } else if ((points.getFirst().y >= midy) && (southy - points.getFirst().y <= points.getFirst().x - from.x) && (southy - points.getFirst().y <= eastx - points.getFirst().x)) {
            // Donji kvadrant
            points.getFirst().x = MathUtil.getBetween(points.getFirst().x, from.x, eastx);
            points.getFirst().y = southy + DISTANCE_FROM_UMLBOX;
        } else if ((points.getFirst().x <= midx) && (points.getFirst().x - from.x <= points.getFirst().y - from.y) && (points.getFirst().x - from.x <= southy - points.getFirst().y)) {
            // Levi kvadrant
            points.getFirst().x = from.x - DISTANCE_FROM_UMLBOX;
            points.getFirst().y = MathUtil.getBetween(points.getFirst().y, from.y, southy);
        } else {
            // Desni kvadrant
            points.getFirst().x = eastx + DISTANCE_FROM_UMLBOX;
            points.getFirst().y = MathUtil.getBetween(points.getFirst().y, from.y, southy);
        }

        midx = to.x + to.width / 2;
        eastx = to.x + to.width;
        midy = to.y + to.height / 2;
        southy = to.y + to.height;
        if ((points.getLast().y <= midy) && (points.getLast().x - to.x >= points.getLast().y - to.y) && (eastx - points.getLast().x >= points.getLast().y - to.y)) {
            // Gornji kvadrant
            points.getLast().x = MathUtil.getBetween(points.getLast().x, to.x, eastx);
            points.getLast().y = to.y - DISTANCE_FROM_UMLBOX;
        } else if ((points.getLast().y >= midy) && (southy - points.getLast().y <= points.getLast().x - to.x) && (southy - points.getLast().y <= eastx - points.getLast().x)) {
            // Donji kvadrant
            points.getLast().x = MathUtil.getBetween(points.getLast().x, to.x, eastx);
            points.getLast().y = southy + DISTANCE_FROM_UMLBOX;
        } else if ((points.getLast().x <= midx) && (points.getLast().x - to.x <= points.getLast().y - to.y) && (points.getLast().x - to.x <= southy - points.getLast().y)) {
            // Levi kvadrant
            points.getLast().x = to.x - DISTANCE_FROM_UMLBOX;
            points.getLast().y = MathUtil.getBetween(points.getLast().y, to.y, southy);
        } else {
            // Desni kvadrant
            points.getLast().x = eastx + DISTANCE_FROM_UMLBOX;
            points.getLast().y = MathUtil.getBetween(points.getLast().y, to.y, southy);
        }
    }

    /*
     * Focusable methods
     */
    public void gainFocus(UMLDiagram diagram) {
        diagram.moveForward(this);
        for (GuiPoint point : points) {
            point.setVisible(true);
            diagram.moveForward(point);
        }
    }

    public void loseFocus(UMLDiagram diagram) {
        for (GuiPoint point : points) {
            point.setVisible(false);
        }
    }

    /*
     * PointContainer methods
     */
    public void movePoint(GuiPoint guiPoint, int x, int y) {
        guiPoint.x = x;
        guiPoint.y = y;
        calculatePointLocations();
    }

    public void pointClicked(GuiPoint guiPoint, Point clickLocation) {

    }

    public void pointDoubleClicked(GuiPoint guiPoint, Point clickLocation) {
        if (guiPoint != points.getFirst() && guiPoint != points.getLast()) {
            guiPoint.delete();
        }
    }

    public void pointDragEnded(GuiPoint guiPoint) {

    }

    public void pointDragged(GuiPoint guiPoint, int x, int y) {
        this.movePoint(guiPoint, x, y);
    }

    public void pointDragStarted(GuiPoint guiPoint, int x, int y) {

    }

    public void deletePoint(GuiPoint guiPoint) {
        if (guiPoint != points.getFirst() && guiPoint != points.getLast()) {
            points.remove(guiPoint);
        } else {
            delete();
        }
    }

    /*
     * UMLObject methods
     */
    @Override
    public void DoubleclickOn(Point point) {
        GuiPoint tempPoint1, tempPoint2;
        Iterator<GuiPoint> point1 = points.iterator();
        Iterator<GuiPoint> point2 = points.iterator();
        point2.next();
        int where = 1;
        while (point2.hasNext()) {
            tempPoint1 = point1.next();
            tempPoint2 = point2.next();
            double r = MathUtil.getProjectionr(tempPoint1.x, tempPoint1.y, tempPoint2.x, tempPoint2.y, point.x, point.y);
            Point projectionPoint = MathUtil.getProjectionPoint(tempPoint1.x, tempPoint1.y, tempPoint2.x, tempPoint2.y, r);
            if (r >= 0 && r <= 1 && MathUtil.pointDistance(projectionPoint, point) <= CLICK_MISS_DISTANCE) {
                points.add(where, new GuiPoint(diagram, this, projectionPoint.x, projectionPoint.y));
                points.get(where).setVisible(true);
                return;
            }
            where++;
        }
    }

    @Override
    public void clickOn(Point point) {

    }

    @Override
    public boolean contains(Point argumentPoint) {
        GuiPoint tempPoint1, tempPoint2;
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
            Point projectionPoint = MathUtil.getProjectionPoint(tempPoint1.x, tempPoint1.y, tempPoint2.x, tempPoint2.y, r);
            if (r >= 0 && r <= 1 && MathUtil.pointDistance(projectionPoint, argumentPoint) <= CLICK_MISS_DISTANCE) {
                return true;
            }
        }
        if (MathUtil.pointDistance(points.getLast().toPoint(), argumentPoint) <= CLICK_MISS_DISTANCE) {
            return true;
        }

        if (from.y > points.getFirst().y) {
            // Pochinje na gore
            if (new Rectangle(points.getFirst().x - (int) CLICK_MISS_DISTANCE, points.getFirst().y, 2 * (int) CLICK_MISS_DISTANCE + 1, DISTANCE_FROM_UMLBOX).contains(argumentPoint)) {
                return true;
            }
        } else if (from.x > points.getFirst().x) {
            // Pochinje na levo
            if (new Rectangle(points.getFirst().x, points.getFirst().y - (int) CLICK_MISS_DISTANCE, DISTANCE_FROM_UMLBOX, 2 * (int) CLICK_MISS_DISTANCE + 1).contains(argumentPoint)) {
                return true;
            }
        } else if (from.y + from.height < points.getFirst().y) {
            // Pochinje na dole
            if (new Rectangle(points.getFirst().x - (int) CLICK_MISS_DISTANCE, from.y + from.height, 2 * (int) CLICK_MISS_DISTANCE + 1, DISTANCE_FROM_UMLBOX).contains(argumentPoint)) {
                return true;
            }
        } else if (from.x + from.width < points.getFirst().x) {
            // Pochinje na desno
            if (new Rectangle(from.x + from.width, points.getFirst().y - (int) CLICK_MISS_DISTANCE, DISTANCE_FROM_UMLBOX, 2 * (int) CLICK_MISS_DISTANCE + 1).contains(argumentPoint)) {
                return true;
            }
        }

        if (to.y > points.getLast().y) {
            // Pochinje na gore
            if (new Rectangle(points.getLast().x - (int) CLICK_MISS_DISTANCE, points.getLast().y, 2 * (int) CLICK_MISS_DISTANCE + 1, DISTANCE_FROM_UMLBOX).contains(argumentPoint)) {
                return true;
            }
        } else if (to.x > points.getLast().x) {
            // Pochinje na levo
            if (new Rectangle(points.getLast().x, points.getLast().y - (int) CLICK_MISS_DISTANCE, DISTANCE_FROM_UMLBOX, 2 * (int) CLICK_MISS_DISTANCE + 1).contains(argumentPoint)) {
                return true;
            }
        } else if (to.y + to.height < points.getLast().y) {
            // Pochinje na dole
            if (new Rectangle(points.getLast().x - (int) CLICK_MISS_DISTANCE, to.y + to.height, 2 * (int) CLICK_MISS_DISTANCE + 1, DISTANCE_FROM_UMLBOX).contains(argumentPoint)) {
                return true;
            }
        } else if (to.x + to.width < points.getLast().x) {
            // Pochinje na desno
            if (new Rectangle(to.x + to.width, points.getLast().y - (int) CLICK_MISS_DISTANCE, DISTANCE_FROM_UMLBOX, 2 * (int) CLICK_MISS_DISTANCE + 1).contains(argumentPoint)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void delete() {
    	delete (true);
    }
    
    public void delete (boolean notifyParents) {
        super.delete();
        if (notifyParents) {
        	from.relations.remove(this);
        	to.relations.remove(this);
       }
        Iterator<GuiPoint> iter = points.iterator();
        GuiPoint point;
        while (iter.hasNext()) {
            point = iter.next();
            if (diagram.objects.contains(point)) {
                point.delete(false);
            }
        }  	
    }
}
