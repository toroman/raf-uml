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
package edu.raf.uml.gui.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLObject;

/**
 * Ovo je pomotjna klasa, jako bitna da bi sela stvar kako-tako radila. To su one crne
 * kockice koje se pojave kada kliknesh na neki objekat.
 * 
 * GuiPoint se razlikuje od ostalih UMLObjekata po tome shto ne vodi rachuna o sebi; Naime,
 * ove tachke se razlichito ponashaju u zavisnosti od toga kome pripadaju i u kojoj ulozi.
 * Svaki GuiPoint ima parenta, koji je PointContainer, i kad god radim neshto sa tachkom
 * direktno iz Gui-a parent je obaveshtoen i odradi sav posao umesto tachke.
 */
public class GuiPoint extends UMLObject implements Draggable {
	
    private double x;
    private double y;
    private boolean isVisible = false;
    private PointContainer parent;
    private transient double xoffset,  yoffset;
    
    public GuiPoint(UMLDiagram diagram, PointContainer parent, double x, double y) {
        super(diagram);
        this.setParent(parent);
        this.setX(x);
        this.setY(y);
    }

    public Point2D.Double toPoint() {
        return new Point2D.Double(getX(), getY());
    }

    @Override
    public Rectangle2D getBounds() {
    	return new Rectangle2D.Double(x,y,0,0);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see guiUtil.Draggable#startDrag(int, int)
     */
    public void startDrag(double x, double y) {
        xoffset = x - this.getX();
        yoffset = y - this.getY();
    }

    /*
     * (non-Javadoc)
     * 
     * @see guiUtil.Draggable#drag(int, int)
     */
    public void drag(double x, double y) {
        this.setX(x - xoffset);
        this.setY(y - yoffset);
        getParent().pointDragged(this, x, y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see guiUtil.Draggable#endDrag()
     */
    public void endDrag() {
        getParent().pointDragEnded(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uml.UMLObject#clickOn(java.awt.Point)
     */
    @Override
    public void clickOn(Point2D.Double point) {
        getParent().pointClicked(this, point);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uml.UMLObject#DoubleclickOn(java.awt.Point)
     */
    @Override
    public void dblClickOn(Point2D.Double point) {
        getParent().pointDoubleClicked(this, point);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uml.UMLObject#contains(java.awt.Point)
     */
    @Override
    public boolean contains(Point2D.Double point) {
        if (isVisible() && Math.abs(point.x - getX()) <= 2 && Math.abs(point.y - getY()) <= 2)
            return true;
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uml.UMLObject#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics2D g) {
        if (isVisible()) {
            Color tempColor = g.getColor();
            g.setColor(Color.BLACK);
            g.fillRect((int)getX() - 2, (int)getY() - 2, 5, 5);
            g.setColor(tempColor);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see uml.UMLObject#delete()
     */
    @Override
    public void delete() {
        delete (true);
    }
    
    public void delete(boolean notifyParent) {
    	super.delete();
    	if (notifyParent)
    		getParent().deletePoint(this);
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setParent(PointContainer parent) {
        this.parent = parent;
    }

    public PointContainer getParent() {
        return parent;
    }
    
    @Override
    public Cursor getCursor() {
       	return parent.giveCursorTo(this);
    }

	public void setX(double x) {
		this.x = x;
	}

	public double getX() {
		return x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public double getY() {
		return y;
	}
}
