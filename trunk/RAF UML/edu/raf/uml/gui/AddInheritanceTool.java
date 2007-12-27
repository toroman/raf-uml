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

import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;

import edu.raf.uml.model.UMLBox;
import edu.raf.uml.model.UMLInheritance;
import edu.raf.uml.model.UMLObject;

public class AddInheritanceTool implements MouseInputListener {

    private UMLBox from;
    public DiagramPanel parentPanel;

    public AddInheritanceTool(DiagramPanel parentPanel) {
        this.parentPanel = parentPanel;
        from = null;
    }

    public void mouseClicked(MouseEvent event) {
        UMLObject object = parentPanel.diagram.getObjectAt(event.getPoint());
        if (object instanceof UMLBox) {
            if (from == null) {
                from = (UMLBox) object;
                return;
            } else {
                if (from.inherits((UMLBox) object)) {
                    // Error: kruzhno nasledjivanje
                    JOptionPane.showConfirmDialog(parentPanel,
                            "Circural inheritance detected.", "Error",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.ERROR_MESSAGE);
                    parentPanel.setTool(DiagramPanel.DEFAULT_TOOL);
                    return;
                }
                if (((UMLBox) object).inherits(from)) {
                    // Error: Vetj je nasledjen nasledjivanje
                    JOptionPane.showConfirmDialog(parentPanel,
                            "Object already inherited.", "Error",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.ERROR_MESSAGE);
                    parentPanel.setTool(DiagramPanel.DEFAULT_TOOL);
                    return;
                }
                UMLInheritance inheritance = new UMLInheritance(
                        parentPanel.diagram, from, (UMLBox) object);
                parentPanel.diagram.giveFocus(inheritance);
                parentPanel.setTool(DiagramPanel.DEFAULT_TOOL);
                parentPanel.repaint();
            }
        }
    }

    public void mouseEntered(MouseEvent arg0) {

    }

    public void mouseExited(MouseEvent arg0) {

    }

    public void mousePressed(MouseEvent arg0) {

    }

    public void mouseReleased(MouseEvent arg0) {

    }

    public void mouseDragged(MouseEvent arg0) {

    }

    public void mouseMoved(MouseEvent arg0) {

    }
}
