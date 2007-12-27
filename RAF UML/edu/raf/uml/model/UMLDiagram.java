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

import edu.raf.uml.gui.DiagramPanel;
import edu.raf.uml.gui.util.Focusable;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class UMLDiagram {

    public ArrayList<UMLObject> objects;
    public Focusable onFocus;
    public Graphics graphics;
    public DiagramPanel panel;

    public UMLDiagram(DiagramPanel panel) {
        objects = new ArrayList<UMLObject>();
        onFocus = null;
        this.panel = panel;
    }

    public void addObject(UMLObject object) {
        objects.add(object);
    }

    public void removeObject(UMLObject object) {
        objects.remove(object);
    }

    public void giveFocus(Focusable fobject) {
        if (onFocus != null) {
            onFocus.loseFocus(this);
        }
        if (fobject != null) {
            fobject.gainFocus(this);
        }
        onFocus = fobject;
    }

    public void paint(Graphics g) {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).paint(g);
        }
    }

    public UMLObject getObjectAt(Point point) {
        for (int i = objects.size() - 1; i >= 0; i--) {
            if (objects.get(i).contains(point)) {
                return objects.get(i);
            }
        }
        return null;
    }

    public void moveForward(UMLObject object) {
        objects.add(object);
        objects.remove(object);
    }
}
