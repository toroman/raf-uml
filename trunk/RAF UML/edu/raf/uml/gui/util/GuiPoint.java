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

import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

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

    public int x,  y;
    private boolean isVisible = false;
    private PointContainer parent;
    private int xoffset,  yoffset;

    public GuiPoint(UMLDiagram diagram, PointContainer parent, int x, int y) {
        super(diagram);
        this.setParent(parent);
        this.x = x;
        this.y = y;
    }

    public Point toPoint() {
        return new Point(x, y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see guiUtil.Draggable#startDrag(int, int)
     */
    public void startDrag(int x, int y) {
        xoffset = x - this.x;
        yoffset = y - this.y;
    }

    /*
     * (non-Javadoc)
     * 
     * @see guiUtil.Draggable#drag(int, int)
     */
    public void drag(int x, int y) {
        this.x = x - xoffset;
        this.y = y - yoffset;
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
    public void clickOn(Point point) {
        getParent().pointClicked(this, point);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uml.UMLObject#DoubleclickOn(java.awt.Point)
     */
    @Override
    public void DoubleclickOn(Point point) {
        getParent().pointDoubleClicked(this, point);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uml.UMLObject#contains(java.awt.Point)
     */
    @Override
    public boolean contains(Point point) {
        if (isVisible() && Math.abs(point.x - x) <= 2 && Math.abs(point.y - y) <= 2) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uml.UMLObject#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        if (isVisible()) {
            Color tempColor = g.getColor();
            g.setColor(Color.BLACK);
            g.fillRect(x - 2, y - 2, 5, 5);
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
        super.delete();
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
}
