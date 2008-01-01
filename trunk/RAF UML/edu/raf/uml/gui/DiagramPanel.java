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
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.raf.uml.gui.tool.AbstractDrawableTool;
import edu.raf.uml.gui.tool.AddBoxTool;
import edu.raf.uml.gui.tool.AddRelationTool;
import edu.raf.uml.gui.tool.DefaultTool;
import edu.raf.uml.gui.tool.DeleteTool;
import edu.raf.uml.gui.tool.factory.ClassBoxFactory;
import edu.raf.uml.gui.tool.factory.CommentBoxFactory;
import edu.raf.uml.gui.tool.factory.CommentRelationFactory;
import edu.raf.uml.gui.tool.factory.InheritanceRelationFactory;
import edu.raf.uml.gui.util.GuiString;
import edu.raf.uml.model.UMLDiagram;

@SuppressWarnings("serial")
public class DiagramPanel extends JPanel {

	private MouseMotionListener motionListener;
	
    public UMLDiagram diagram;
    public ApplicationGui gui;
    public AbstractDrawableTool currentTool;
    public JTextField guiStringEditField;
    public GuiString editingGuiString;
    
    public void setTool(int toolName) {
        this.removeMouseListener(currentTool);
        this.removeMouseMotionListener(currentTool);
		if (editingGuiString != null) {
			removeGuiStringTextField();
		}
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
                setCursor(Cursor.getDefaultCursor());
                break;
            case ADD_CLASS_TOOL:
                currentTool = new AddBoxTool(this, new ClassBoxFactory());
                this.addMouseListener(currentTool);
                this.addMouseMotionListener(currentTool);
                if (!gui.toolAddClass.isSelected()) {
                    gui.toolAddClass.setSelected(true);
                }
                this.setRefreshOnMove(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                break;
            case ADD_INHERITANCE_TOOL:
                currentTool = new AddRelationTool(this, new InheritanceRelationFactory ());
                this.addMouseListener(currentTool);
                this.addMouseMotionListener(currentTool);
                if (!gui.toolAddInheritance.isSelected()) {
                    gui.toolAddInheritance.setSelected(true);
                }
                this.setRefreshOnMove(true);
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                break;
            case DELETE_TOOL:
                currentTool = new DeleteTool(this);
                this.addMouseListener(currentTool);
                this.addMouseMotionListener(currentTool);
                if (!gui.toolDelete.isSelected()) {
                    gui.toolDelete.setSelected(true);
                }
                this.setRefreshOnMove(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
               break;
            case ADD_COMMENT_BOX_TOOL:
                currentTool = new AddBoxTool(this, new CommentBoxFactory());
                this.addMouseListener(currentTool);
                this.addMouseMotionListener(currentTool);
                if (!gui.toolAddCommentBox.isSelected()) {
                    gui.toolAddCommentBox.setSelected(true);
                }
                this.setRefreshOnMove(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                break;                
            case ADD_COMMENT_RELATION_TOOL:
                currentTool = new AddRelationTool(this, new CommentRelationFactory());
                this.addMouseListener(currentTool);
                this.addMouseMotionListener(currentTool);
                if (!gui.toolAddCommentRelation.isSelected()) {
                    gui.toolAddCommentRelation.setSelected(true);
                }
                this.setRefreshOnMove(true);
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                break;                
       }
    }
    
    public void removeGuiStringTextField () {
    	this.remove(guiStringEditField);
    	if (editingGuiString != null) {
    		editingGuiString.setText(guiStringEditField.getText());
    		editingGuiString = null;
    	}
    	repaint();
    }
    
    public void showGuiStringTextField (GuiString guiString) {
    	this.add(guiStringEditField);
    	guiStringEditField.setText(guiString.getText());
    	guiStringEditField.setBounds ((int)guiString.getBounds().x, (int)guiString.getBounds().y, (int)guiString.getBounds().width+2, (int)guiString.getBounds().height+1);
    	editingGuiString = guiString;
    	guiStringEditField.requestFocus();
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
        this.setPreferredSize(UMLDiagram.MAX_DIMENSION);
        this.setLayout(null);
        
        guiStringEditField = new JTextField ();
        guiStringEditField.addKeyListener(new KeyAdapter () {
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER)
        			removeGuiStringTextField();
        	}
        });
        
        diagram = new UMLDiagram(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        Color tempColor = g.getColor();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, UMLDiagram.MAX_DIMENSION.width, UMLDiagram.MAX_DIMENSION.height);
        diagram.paint((Graphics2D)g);
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
    public static final int ADD_INHERITANCE_TOOL = 3;
    public static final int DELETE_TOOL = 4;
    public static final int ADD_COMMENT_BOX_TOOL = 5;
    public static final int ADD_COMMENT_RELATION_TOOL = 6;
}
