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

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import edu.raf.uml.gui.DiagramPanel;
import edu.raf.uml.gui.util.Draggable;
import edu.raf.uml.gui.util.Focusable;
import edu.raf.uml.gui.util.MathUtil;
import edu.raf.uml.model.UMLObject;

public class DefaultTool extends AbstractDrawableTool {

	public DiagramPanel parentPanel;
	private Point2D.Double mouseDownPoint = null;
	private Draggable draggingObject = null;

	public DefaultTool(DiagramPanel parentPanel) {
		this.parentPanel = parentPanel;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		try {
			UMLObject object = parentPanel.diagram
					.getObjectAt(MathUtil.toPoint2D((event.getPoint())));
			if (object != null)
				parentPanel.gui.propertiesPanel.setObject(object);
			
			if (object == parentPanel.diagram.onFocus) {
				if (object != null) {
					if (event.getClickCount() == 1) {
						object.clickOn(MathUtil.toPoint2D(event.getPoint()));
					} else {
						object.dblClickOn(MathUtil.toPoint2D(event.getPoint()));
					}
				}
				return;
			} else if (object instanceof Focusable) {
				parentPanel.diagram.giveFocus((Focusable) object);
				return;
			} else if (object != null) {
				if (event.getClickCount() == 1) {
					object.clickOn(MathUtil.toPoint2D(event.getPoint()));
				} else {
					object.dblClickOn(MathUtil.toPoint2D(event.getPoint()));
				}
				return;
			} else {
				parentPanel.diagram.giveFocus(null);
			}
		} finally {
			parentPanel.repaint();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		mouseDownPoint = new Point2D.Double (e.getX(), e.getY());
		if (parentPanel.editingGuiString != null && !parentPanel.guiStringEditField.getBounds().contains(e.getPoint())) {
			parentPanel.removeGuiStringTextField();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent event) {
		if (mouseDownPoint != null) {
			UMLObject kme = parentPanel.diagram.getObjectAt(mouseDownPoint);
			if (kme instanceof Draggable) {
				draggingObject = (Draggable) kme;
				draggingObject.startDrag(mouseDownPoint.getX(), mouseDownPoint.getY());
			}
			mouseDownPoint = null;
		} else {
			if (draggingObject != null) {
				draggingObject.drag(event.getX(), event.getY());
			}
		}
		parentPanel.repaint();
	}
	
	@Override
	public void mouseReleased(MouseEvent event) {
		if (draggingObject != null) {
			draggingObject.endDrag();
			draggingObject = null;
		}
		mouseDownPoint = null;
	}
	
	@Override
	public void mouseMoved(MouseEvent event) {
		UMLObject kme = parentPanel.diagram.getObjectAt(MathUtil.toPoint2D(event.getPoint()));
		if (kme != null)
			parentPanel.setCursor(kme.getCursor());
		else
			parentPanel.setCursor(Cursor.getDefaultCursor());
	}
}
