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
package edu.raf.uml.gui;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import edu.raf.uml.gui.util.Draggable;
import edu.raf.uml.gui.util.Focusable;
import edu.raf.uml.model.UMLObject;

public class DefaultTool implements MouseInputListener {

	public DiagramPanel parentPanel;
	private Draggable draggingObject = null;
	private boolean dragging;

	public DefaultTool(DiagramPanel parentPanel) {
		this.parentPanel = parentPanel;
	}

	public void mouseClicked(MouseEvent event) {
		try {
			UMLObject object = parentPanel.diagram
					.getObjectAt(event.getPoint());
			if (object == parentPanel.diagram.onFocus) {
				if (object != null) {
					if (event.getClickCount() == 1) {
						object.clickOn(event.getPoint());
					} else {
						object.DoubleclickOn(event.getPoint());
					}
				}
				return;
			} else if (object instanceof Focusable) {
				parentPanel.diagram.giveFocus((Focusable) object);
				return;
			} else if (object != null) {
				if (event.getClickCount() == 1) {
					object.clickOn(event.getPoint());
				} else {
					object.DoubleclickOn(event.getPoint());
				}
				return;
			} else {
				parentPanel.diagram.giveFocus(null);
			}
		} finally {
			parentPanel.repaint();
		}
	}

	public void mouseDragged(MouseEvent event) {
		if (!dragging) {
			dragging = true;
			UMLObject kme = parentPanel.diagram.getObjectAt(event.getPoint());
			if (kme instanceof Focusable) {
				parentPanel.diagram.giveFocus((Focusable) kme);
			}
			if (kme instanceof Draggable) {
				draggingObject = (Draggable) kme;
				draggingObject.startDrag(event.getX(), event.getY());
			}
		} else {
			if (draggingObject != null) {
				draggingObject.drag(event.getX(), event.getY());
			}
		}
		parentPanel.repaint();
	}

	public void mouseMoved(MouseEvent arg0) {

	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent event) {
		if (dragging) {
			if (draggingObject != null) {
				draggingObject.endDrag();
				draggingObject = null;
			}
			dragging = false;
		}
	}
}