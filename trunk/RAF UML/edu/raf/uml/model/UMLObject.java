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

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/*
 * Sve shto mozhe da postoji u dijagramu je UML object.
 */
public abstract class UMLObject {

    UMLDiagram diagram;

    /*
     * Konstruktor koji automatski taj objekat ubaci u diagram.
     */
    public UMLObject(UMLDiagram diagram) {
        this.diagram = diagram;
        diagram.objects.add(this);
    }

    /*
     * Destruktor?
     */
    public void delete() {
        diagram.removeObject(this);
    }

    /*
     * Poziva se kada god gui pita da li se objekat nalazi na tim koordinatama.
     */
    public abstract boolean contains(Point2D.Double point);

    /*
     * Iscrtavanje na panelu. GuiPointovi koji pripadaju tom objektu se sami iscrtavaju,
     * ako treba.
     */
    public abstract void paint(Graphics2D g);

    /*
     * Sve shto treba uraditi kada se klikne na taj objekat.
     */
    public abstract void clickOn(Point2D.Double point);

    /*
     * Sve shto treba uraditi kada se dvaput klikne na taj objekat.
     */
    public abstract void DoubleclickOn(Point2D.Double point);
}
