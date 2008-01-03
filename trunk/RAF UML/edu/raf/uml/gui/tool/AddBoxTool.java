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

import java.awt.event.MouseEvent;

import edu.raf.uml.gui.DiagramPanel;
import edu.raf.uml.gui.tool.factory.BoxFactory;
import edu.raf.uml.model.UMLBox;

public class AddBoxTool extends AbstractDrawableTool {

	public DiagramPanel parentPanel;
	private BoxFactory factory;

	public AddBoxTool(DiagramPanel parentPanel, BoxFactory factory) {
		this.parentPanel = parentPanel;
		this.factory = factory;
	}

	public void mouseReleased(MouseEvent event) {
		UMLBox newBox = factory.createUMLBox(parentPanel.diagram, event.getX(), event.getY());
		parentPanel.diagram.giveFocus(newBox);
		parentPanel.setTool(DiagramPanel.DEFAULT_TOOL);
		parentPanel.repaint();
	}
}
