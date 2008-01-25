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
package edu.raf.uml.gui.tool;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import edu.raf.uml.gui.DiagramPanel;

/**
 * Ovo bash i nije tool ali ne znam gde da ga stavim, yet.
 * 
 * @author Srecko Toroman
 * 
 */
public class ZoomTool {
    DiagramPanel diagram;

    public ZoomTool(DiagramPanel diagramPanel) {
        this.diagram = diagramPanel;
    }

    public static void zoomIn(Graphics2D g) {
        AffineTransform tf = g.getTransform();
        tf.setToScale(1.2, 1.2);
        g.setTransform(tf);
    }

    public static void zoomOut(Graphics2D g) {
        AffineTransform tf = g.getTransform();
        tf.setToScale(5.0 / 6.0, 5.0 / 6.0);
        g.setTransform(tf);
    }
}
