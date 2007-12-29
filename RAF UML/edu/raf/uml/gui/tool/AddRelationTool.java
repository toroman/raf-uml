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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import edu.raf.uml.gui.DiagramPanel;
import edu.raf.uml.gui.tool.factory.RelationFactory;
import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLObject;
import edu.raf.uml.model.UMLRelation;

public class AddRelationTool extends AbstractDrawableTool {

	private RelationFactory factory; 
	
    private UMLBox from;
    private Point lineStart, lineEnd; 
    public DiagramPanel parentPanel;

    public AddRelationTool(DiagramPanel parentPanel, RelationFactory factory) {
        this.parentPanel = parentPanel;
        this.factory = factory;
        from = null;
        lineStart = null;
        lineEnd = new Point (0, 0);
    }

    public void mouseClicked(MouseEvent event) {
        UMLObject object = parentPanel.diagram.getObjectAt(event.getPoint());
        if (object == null) return;
        if (object instanceof UMLBox) {
            if (from == null) {
            	if (factory.canRelateFrom((UMLBox)object) == null) {
            		from = (UMLBox) object;
            		lineStart = new Point (event.getPoint());
            		lineEnd = new Point (event.getPoint());
                	return;
            	} else {
            		System.out.println(factory.canRelateFrom((UMLBox)object));
            	}
            }
        }
        if (factory.canRelate(from, object) == null) {           	
            UMLRelation relation = factory.makeRelation(
            	parentPanel.diagram, from, object);
            parentPanel.diagram.giveFocus(relation);
            parentPanel.setTool(DiagramPanel.DEFAULT_TOOL);
            parentPanel.repaint();
        } else {
            System.out.println(factory.canRelate(from, object));
        }
    }
    
    @Override
    public void paint(Graphics g) {
    	if (lineStart != null) {
    		Color tempColor = g.getColor();
    		g.setColor(Color.BLACK);
    		g.drawLine(lineStart.x, lineStart.y, lineEnd.x, lineEnd.y);
    		g.setColor (tempColor);
    	}
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
    	lineEnd.x = e.getX();
    	lineEnd.y = e.getY();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
       	lineEnd.x = e.getX();
    	lineEnd.y = e.getY();
    }
}
