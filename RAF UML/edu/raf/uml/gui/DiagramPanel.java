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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.raf.uml.gui.tool.AbstractDrawableTool;
import edu.raf.uml.gui.tool.AddClassTool;
import edu.raf.uml.gui.tool.AddRelationTool;
import edu.raf.uml.gui.tool.DefaultTool;
import edu.raf.uml.gui.tool.DeleteTool;
import edu.raf.uml.gui.tool.factory.InheritanceRelationFactory;
import edu.raf.uml.model.UMLDiagram;

@SuppressWarnings("serial")
public class DiagramPanel extends JPanel {

	private MouseMotionListener motionListener;
	
    public UMLDiagram diagram;
    public ApplicationGui gui;
    public AbstractDrawableTool currentTool;
    
    public void setTool(int toolName) {
        this.removeMouseListener(currentTool);
        this.removeMouseMotionListener(currentTool);
        for (JButton toolButton : gui.toolButtons) {
            toolButton.setSelected(false);
        }
        switch (toolName) {
            case DEFAULT_TOOL:
                currentTool = new DefaultTool(this);
                this.addMouseListener(currentTool);
                this.addMouseMotionListener(currentTool);
                if (!gui.toolDefault.isSelected()) {
                    gui.toolDefault.setSelected(true);
                }
                this.setRefreshOnMove(false);
                break;
            case ADD_CLASS_TOOL:
                currentTool = new AddClassTool(this);
                this.addMouseListener(currentTool);
                this.addMouseMotionListener(currentTool);
                if (!gui.toolAddClass.isSelected()) {
                    gui.toolAddClass.setSelected(true);
                }
                this.setRefreshOnMove(false);
                break;
            case ADD_RELATION_TOOL:
                currentTool = new AddRelationTool(this, new InheritanceRelationFactory ());
                this.addMouseListener(currentTool);
                this.addMouseMotionListener(currentTool);
                if (!gui.toolAddInheritance.isSelected()) {
                    gui.toolAddInheritance.setSelected(true);
                }
                this.setRefreshOnMove(true);
                break;
            case DELETE_TOOL:
                currentTool = new DeleteTool(this);
                this.addMouseListener(currentTool);
                this.addMouseMotionListener(currentTool);
                if (!gui.toolDelete.isSelected()) {
                    gui.toolDelete.setSelected(true);
                }
                this.setRefreshOnMove(false);
                break;
       }
    }

    public DiagramPanel(ApplicationGui gui) {
        super();
        motionListener = new MouseMotionAdapter () {
        	@Override
        	public void mouseMoved(MouseEvent e) {
        		diagram.panel.repaint();
        	}
        	@Override
        	public void mouseDragged(MouseEvent e) {
        		diagram.panel.repaint();
        	}
        };
        this.gui = gui;
        diagram = new UMLDiagram(this);
    }

    @Override
    public void paint(Graphics g) {
        Color tempColor = g.getColor();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 2000, 2000);
        diagram.paint(g);
        currentTool.paint(g);
        g.setColor(tempColor);
    }
    
    
    /**
     * Da li da se panel refreshuje kada se kursor kretje (drag ili move).
     * Ochekivan performance hit, meni se ni ne ostetja ali tje verovatno Sretjku
     * dodatno usporiti. Staviti na true samo kada je nepohodno!
     * 
     * @param shouldRefreshOnMove - da li da refreshuje
     */
    public void setRefreshOnMove (boolean shouldRefreshOnMove) {
    	if (shouldRefreshOnMove) {
    		for (MouseListener listener: this.getMouseListeners())
    			if (listener == motionListener)
    				return;
    		this.addMouseMotionListener(motionListener);
    		return;
    	} else {
    		for (MouseListener listener: this.getMouseListeners())
    			if (listener == motionListener)
    				return;
    		this.removeMouseMotionListener(motionListener);
    	}
    }
    
    public static final int DEFAULT_TOOL = 1;
    public static final int ADD_CLASS_TOOL = 2;
    public static final int ADD_RELATION_TOOL = 3;
    public static final int DELETE_TOOL = 4;
}
