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
import edu.raf.uml.model.UMLClass;
import edu.raf.uml.model.UMLObject;

public class AddClassTool extends AbstractDrawableTool {

	public DiagramPanel parentPanel;

	public AddClassTool(DiagramPanel parentPanel) {
		this.parentPanel = parentPanel;
	}

	public void mouseClicked(MouseEvent event) {
		int count = 0;
		for (UMLObject object : parentPanel.diagram.objects) {
			if (object instanceof UMLClass
					&& ((UMLClass) (object)).className.equals("NewClass"
							+ count)) {
				count++;
			}
		}
		UMLClass newClass;
		parentPanel.diagram.giveFocus(newClass = new UMLClass(
				parentPanel.diagram, event.getX(), event.getY()));
		newClass.className = "NewClass" + count;
		parentPanel.setTool(DiagramPanel.DEFAULT_TOOL);
		parentPanel.repaint();
	}
}
