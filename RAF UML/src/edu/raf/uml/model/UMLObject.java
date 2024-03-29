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

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import edu.raf.uml.gui.util.Clickable;

/**
 * Sve shto mozhe da postoji u dijagramu je UML object.
 */
public abstract class UMLObject implements Clickable {

    protected UMLDiagram diagram;
    protected static final Cursor DEFAULT_CURSOR = Cursor.getDefaultCursor();
    protected static final Cursor MOVE_CURSOR = new Cursor (Cursor.MOVE_CURSOR);
    protected static final Cursor W_RESIZE_CURSOR = new Cursor (Cursor.W_RESIZE_CURSOR);
    protected static final Cursor NW_RESIZE_CURSOR = new Cursor (Cursor.NW_RESIZE_CURSOR);
    protected static final Cursor N_RESIZE_CURSOR = new Cursor (Cursor.N_RESIZE_CURSOR);
    protected static final Cursor NE_RESIZE_CURSOR = new Cursor (Cursor.NE_RESIZE_CURSOR);
    protected static final Cursor E_RESIZE_CURSOR = new Cursor (Cursor.E_RESIZE_CURSOR);
    protected static final Cursor SE_RESIZE_CURSOR = new Cursor (Cursor.SE_RESIZE_CURSOR);
    protected static final Cursor S_RESIZE_CURSOR = new Cursor (Cursor.S_RESIZE_CURSOR);
    protected static final Cursor SW_RESIZE_CURSOR = new Cursor (Cursor.SW_RESIZE_CURSOR);


    /**
     * Konstruktor koji automatski taj objekat ubaci u diagram.
     */
    public UMLObject(UMLDiagram diagram) {
        this.diagram = diagram;
        diagram.objects.add(this);
    }

    /**
     * Destruktor?
     */
    public void delete() {
        diagram.removeObject(this);
    }

    /**
     * Poziva se kada god gui pita da li se objekat nalazi na tim koordinatama.
     */
    public abstract boolean contains(Point2D.Double point);

    /**
     * Iscrtavanje na panelu. GuiPointovi koji pripadaju tom objektu se sami iscrtavaju,
     * ako treba.
     */
    public abstract void paint(Graphics2D g);
    
    public Cursor getCursor () {
    	return DEFAULT_CURSOR;
    }
    
    public abstract Rectangle2D getBounds();
}
