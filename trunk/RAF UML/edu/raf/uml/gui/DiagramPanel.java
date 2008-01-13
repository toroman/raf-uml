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
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.raf.uml.gui.tool.AbstractDrawableTool;
import edu.raf.uml.gui.tool.AddBoxTool;
import edu.raf.uml.gui.tool.AddRelationTool;
import edu.raf.uml.gui.tool.DefaultTool;
import edu.raf.uml.gui.tool.DeleteTool;
import edu.raf.uml.gui.tool.factory.AggregationRelationFactory;
import edu.raf.uml.gui.tool.factory.AssociationClassFactory;
import edu.raf.uml.gui.tool.factory.AssociationRelationFactory;
import edu.raf.uml.gui.tool.factory.ClassBoxFactory;
import edu.raf.uml.gui.tool.factory.CommentBoxFactory;
import edu.raf.uml.gui.tool.factory.CommentRelationFactory;
import edu.raf.uml.gui.tool.factory.CompositionRelationFactory;
import edu.raf.uml.gui.tool.factory.InheritanceRelationFactory;
import edu.raf.uml.gui.tool.factory.InterfaceBoxFactory;
import edu.raf.uml.gui.tool.factory.RealisationRelationFactory;
import edu.raf.uml.gui.util.GuiString;
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLObject;

@SuppressWarnings("serial")
public class DiagramPanel extends JPanel {

	private MouseMotionListener motionListener;

	public UMLDiagram diagram;
	public ApplicationGui gui;
	public AbstractDrawableTool currentTool;
	public JTextField guiStringEditField;
	public GuiString editingGuiString;

	private double zoomLevel = 1.0;

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
			currentTool = new AddRelationTool(this,
					new InheritanceRelationFactory());
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
			currentTool = new AddRelationTool(this,
					new CommentRelationFactory());
			this.addMouseListener(currentTool);
			this.addMouseMotionListener(currentTool);
			if (!gui.toolAddCommentRelation.isSelected()) {
				gui.toolAddCommentRelation.setSelected(true);
			}
			this.setRefreshOnMove(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case ADD_ASSOCIATION_TOOL:
			currentTool = new AddRelationTool(this,
					new AssociationRelationFactory());
			this.addMouseListener(currentTool);
			this.addMouseMotionListener(currentTool);
			if (!gui.toolAddAssociationRelation.isSelected()) {
				gui.toolAddAssociationRelation.setSelected(true);
			}
			this.setRefreshOnMove(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case ADD_AGGREGATION_TOOL:
			currentTool = new AddRelationTool(this,
					new AggregationRelationFactory());
			this.addMouseListener(currentTool);
			this.addMouseMotionListener(currentTool);
			if (!gui.toolAddAggregationRelation.isSelected()) {
				gui.toolAddAggregationRelation.setSelected(true);
			}
			this.setRefreshOnMove(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case ADD_COMPOSITION_TOOL:
			currentTool = new AddRelationTool(this,
					new CompositionRelationFactory());
			this.addMouseListener(currentTool);
			this.addMouseMotionListener(currentTool);
			if (!gui.toolAddCompositionRelation.isSelected()) {
				gui.toolAddCompositionRelation.setSelected(true);
			}
			this.setRefreshOnMove(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case ADD_INTERFACE_TOOL:
			currentTool = new AddBoxTool(this, new InterfaceBoxFactory());
			this.addMouseListener(currentTool);
			this.addMouseMotionListener(currentTool);
			if (!gui.toolAddInterface.isSelected()) {
				gui.toolAddInterface.setSelected(true);
			}
			this.setRefreshOnMove(false);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case ADD_REALISATION_TOOL:
			currentTool = new AddRelationTool(this,
					new RealisationRelationFactory());
			this.addMouseListener(currentTool);
			this.addMouseMotionListener(currentTool);
			if (!gui.toolAddRealisationRelation.isSelected()) {
				gui.toolAddRealisationRelation.setSelected(true);
			}
			this.setRefreshOnMove(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case ADD_ASSOCIATION_CLASS_TOOL:
			currentTool = new AddRelationTool(this,
					new AssociationClassFactory());
			this.addMouseListener(currentTool);
			this.addMouseMotionListener(currentTool);
			if (!gui.toolAddAssociationClass.isSelected()) {
				gui.toolAddAssociationClass.setSelected(true);
			}
			this.setRefreshOnMove(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		}
	}

	public void removeGuiStringTextField() {
		this.remove(guiStringEditField);
		if (editingGuiString != null) {
			editingGuiString.setText(guiStringEditField.getText());
			editingGuiString = null;
		}
		repaint();
	}

	public void showGuiStringTextField(GuiString guiString) {
		this.add(guiStringEditField);
		guiStringEditField.setText(guiString.getText());
		guiStringEditField.setBounds((int) guiString.getBounds().x,
				(int) guiString.getBounds().y,
				(int) guiString.getBounds().width + 2, (int) guiString
						.getBounds().height + 1);
		guiStringEditField.setFont(guiString.getFont());
		editingGuiString = guiString;
		guiStringEditField.requestFocus();
	}

	public DiagramPanel(ApplicationGui gui) {
		super();
		this.setFocusable(true);
		motionListener = new MouseMotionAdapter() {
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

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					if (diagram.onFocus != null) {
						((UMLObject) diagram.onFocus).delete();
						repaint();
					}
					return;
				}
			}
		});

		guiStringEditField = new JTextField();
		guiStringEditField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					removeGuiStringTextField();
					return;
				}
			}
		});

		diagram = new UMLDiagram(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform tf = g2.getTransform();
		tf.scale(zoomLevel, zoomLevel);
		g2.setTransform(tf);
		Color tempColor = g.getColor();
		g.setColor(Color.LIGHT_GRAY);
		Rectangle r = g.getClipBounds();
		g.fillRect(r.x, r.y, r.width, r.height);
		diagram.paint((Graphics2D) g);
		currentTool.paint(g);
		g.setColor(tempColor);
	}

	/**
	 * Da li da se panel refreshuje kada se kursor kretje (drag ili move).
	 * Ochekivan performance hit, meni se ni ne ostetja ali tje verovatno
	 * Sretjku dodatno usporiti. Staviti na true samo kada je nepohodno!
	 * 
	 * @param shouldRefreshOnMove -
	 *            da li da refreshuje
	 */
	public void setRefreshOnMove(boolean shouldRefreshOnMove) {
		if (shouldRefreshOnMove) {
			for (MouseListener listener : this.getMouseListeners())
				if (listener == motionListener)
					return;
			this.addMouseMotionListener(motionListener);
			return;
		} else {
			for (MouseListener listener : this.getMouseListeners())
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
	public static final int ADD_ASSOCIATION_TOOL = 7;
	public static final int ADD_AGGREGATION_TOOL = 8;
	public static final int ADD_COMPOSITION_TOOL = 9;
	public static final int ADD_INTERFACE_TOOL = 10;
	public static final int ADD_REALISATION_TOOL = 11;
	public static final int ADD_ASSOCIATION_CLASS_TOOL = 12;

	public void zoomIn() {
		zoomLevel  *= 1.2;
		repaint();
	}

	public void zoomOut() {
		zoomLevel *= 5.0/6.0;
		repaint();
	}
}
