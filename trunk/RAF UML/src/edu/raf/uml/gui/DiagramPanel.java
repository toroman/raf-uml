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
import java.awt.Dimension;
import java.awt.Font;
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

import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLObject;

@SuppressWarnings("serial")
public class DiagramPanel extends JPanel implements MouseListener,
		MouseMotionListener {
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

	private MouseMotionListener motionListener;
	public UMLDiagram diagram;
	public ApplicationGui gui;
	public AbstractDrawableTool currentTool;
	private double gridDensity;
	private Color gridColor;
	private Color dotsColor;

	private double zoomLevel = 1.0;
	public Font font = null;
	/**
	 * Da li je ovaj dijagram prikazan u GUI-u
	 */
	private boolean active = false;
	public JScrollPane scrollPane; // ovo izbaciti

	public DiagramPanel(ApplicationGui gui) {
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		motionListener = new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				repaint();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				repaint();
			}
		};
		this.gui = gui;
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

		gridColor = new Color(175, 175, 175);
		dotsColor = Color.DARK_GRAY;
		gridDensity = 16;
		diagram = new UMLDiagram(this);
	}

	public void setTool(int toolName) {
		switch (toolName) {
		case DEFAULT_TOOL:
			currentTool = new DefaultTool(this);
			gui.toolButtons.setSelected(gui.toolDefault.getModel(), true);
			this.setRefreshOnMove(false);
			setCursor(Cursor.getDefaultCursor());
			break;
		case ADD_CLASS_TOOL:
			currentTool = new AddBoxTool(this, new ClassBoxFactory());
			this.setRefreshOnMove(false);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case ADD_INHERITANCE_TOOL:
			currentTool = new AddRelationTool(this,
					new InheritanceRelationFactory());
			this.setRefreshOnMove(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case DELETE_TOOL:
			currentTool = new DeleteTool(this);
			this.setRefreshOnMove(false);
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			break;
		case ADD_COMMENT_BOX_TOOL:
			currentTool = new AddBoxTool(this, new CommentBoxFactory());
			this.setRefreshOnMove(false);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case ADD_COMMENT_RELATION_TOOL:
			currentTool = new AddRelationTool(this,
					new CommentRelationFactory());
			this.setRefreshOnMove(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case ADD_ASSOCIATION_TOOL:
			currentTool = new AddRelationTool(this,
					new AssociationRelationFactory());
			this.setRefreshOnMove(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case ADD_AGGREGATION_TOOL:
			currentTool = new AddRelationTool(this,
					new AggregationRelationFactory());
			this.setRefreshOnMove(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case ADD_COMPOSITION_TOOL:
			currentTool = new AddRelationTool(this,
					new CompositionRelationFactory());
			this.setRefreshOnMove(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case ADD_INTERFACE_TOOL:
			currentTool = new AddBoxTool(this, new InterfaceBoxFactory());
			this.setRefreshOnMove(false);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case ADD_REALISATION_TOOL:
			currentTool = new AddRelationTool(this,
					new RealisationRelationFactory());
			this.setRefreshOnMove(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case ADD_ASSOCIATION_CLASS_TOOL:
			currentTool = new AddRelationTool(this,
					new AssociationClassFactory());
			this.setRefreshOnMove(true);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			break;
		}
	}

	public void drawGrid(Graphics g) {
		Color tempColor = g.getColor();
		g.setColor(gridColor);
		double minx = scrollPane.getViewport().getViewRect().getMinX();
		minx = minx - (minx % (gridDensity * zoomLevel));
		double miny = scrollPane.getViewport().getViewRect().getMinY();
		miny = miny - (miny % (gridDensity * zoomLevel));
		double maxx = scrollPane.getViewport().getViewRect().getMaxX();
		double maxy = scrollPane.getViewport().getViewRect().getMaxY();
		for (double x = minx; x <= maxx; x += (gridDensity * zoomLevel))
			g.drawLine((int) x, (int) miny, (int) x, (int) maxy);
		for (double y = miny; y <= maxy; y += (gridDensity * zoomLevel))
			g.drawLine((int) minx, (int) y, (int) maxx, (int) y);
		g.setColor(tempColor);
	}

	public void drawDots(Graphics g) {
		Color tempColor = g.getColor();
		g.setColor(dotsColor);
		double minx = scrollPane.getViewport().getViewRect().getMinX();
		minx = minx - (minx % (gridDensity * zoomLevel));
		double miny = scrollPane.getViewport().getViewRect().getMinY();
		miny = miny - (miny % (gridDensity * zoomLevel));
		double maxx = scrollPane.getViewport().getViewRect().getMaxX();
		double maxy = scrollPane.getViewport().getViewRect().getMaxY();
		for (double x = minx; x <= maxx; x += (gridDensity * zoomLevel))
			for (double y = miny; y <= maxy; y += (gridDensity * zoomLevel))
				g.drawLine((int) x, (int) y, (int) x, (int) y);
		g.setColor(tempColor);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (font == null) {
			font = Font.decode("Monospaced");
		}
		g.setFont(font);
		Color tempColor = g.getColor();
		g.setColor(Color.LIGHT_GRAY);
		Rectangle r = g.getClipBounds();
		g.fillRect(r.x, r.y, r.width, r.height);
		drawGrid(g2);
		AffineTransform tf = g2.getTransform();
		tf.scale(zoomLevel, zoomLevel);
		g2.setTransform(tf);
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

	public void zoomIn() {
		zoomLevel *= 1.4;
		scrollPane.setViewportView(this); // cisto da preracuna
		repaint();
	}

	public void zoomOut() {
		zoomLevel /= 1.4;
		scrollPane.setViewportView(this); // cisto da preracuna
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension((int) (diagram.getMaxWidth() * zoomLevel),
				(int) (diagram.getMaxHeight() * zoomLevel));
	}

	/**
	 * Ovi listeneri transformisu kordinate iz View-a u "UML" koordinate (time
	 * omogucavaju zoom) a zatim ih proslede listeneru (currentTool)
	 */
	private MouseEvent transformCoordinates(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		x /= zoomLevel;
		y /= zoomLevel;
		return new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), e
				.getModifiersEx(), x, y, e.getClickCount(), e.isPopupTrigger());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (currentTool == null)
			return;
		((MouseListener) currentTool).mouseClicked(transformCoordinates(e));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (currentTool == null)
			return;
		((MouseListener) currentTool).mouseEntered(transformCoordinates(e));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (currentTool == null)
			return;
		((MouseListener) currentTool).mouseExited(transformCoordinates(e));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (currentTool == null)
			return;
		((MouseListener) currentTool).mousePressed(transformCoordinates(e));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (currentTool == null)
			return;
		((MouseListener) currentTool).mouseReleased(transformCoordinates(e));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (currentTool == null)
			return;
		((MouseMotionListener) currentTool)
				.mouseDragged(transformCoordinates(e));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (currentTool == null)
			return;
		((MouseMotionListener) currentTool).mouseMoved(transformCoordinates(e));
	}

	public double getGridDensity() {
		return gridDensity;
	}

	public void setGridDensity(double gridDensity) {
		this.gridDensity = gridDensity;
	}

	public Color getGridColor() {
		return gridColor;
	}

	public void setGridColor(Color gridColor) {
		this.gridColor = gridColor;
	}

	public void setWindow(DiagramWindow wnd) {
		this.scrollPane = wnd.getScrollPane();
	}

	public void setActive(boolean b) {
		this.active = b;
	}

	public boolean isActive() {
		return active;
	}
}
