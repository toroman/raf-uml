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
package edu.raf.uml.gui.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;

import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLObject;
import edu.raf.uml.model.property.Property;

public class GuiString extends UMLObject implements Draggable {
	protected Rectangle2D.Double bounds;
	private boolean isVisible = false;
	public boolean isBackgroundRectVisible = false;
	private StringContainer parent;
	private double xoffset, yoffset;
	private Font defaultFont = Font.decode("Monospaced");
	private String text;

	public GuiString(UMLDiagram diagram, StringContainer parent, String text,
			double x, double y) {
		super(diagram);
		this.setParent(parent);
		bounds = new Rectangle2D.Double(x, y, 0, 0);
		this.text = text;
		this.recalculateBounds();
	}

	/**
	 * Konstruktor potreban za UMLField
	 */
	protected GuiString(UMLDiagram diagram) {
		super(diagram);
	}

	public Rectangle2D.Double getBounds() {
		return bounds;
	}

	public void setX(double x) {
		bounds.x = x;
	}

	public void setY(double y) {
		bounds.y = y;
	}

	public void setWidth(double width) {
		bounds.width = Math.max(calculateMinWidth(), width);
	}

	public void setHeight(double width) {
		bounds.width = width;
	}

	public double calculateMinWidth() {
		return calculateStringSize(getText()).getWidth() + 8;
	}

	public GuiString(UMLDiagram diagram, StringContainer parent) {
		this(diagram, parent, "", 0, 0);
	}

	protected void recalculateBounds() {
		Rectangle2D kme = calculateStringSize(getText());
		bounds.height = kme.getHeight() + 7;
		bounds.width = kme.getWidth() + 8;
		parent.stringSizeChanged(this);
	}

	public Rectangle2D calculateStringSize(String string) {
		Graphics2D g = (Graphics2D) diagram.panel.getGraphics();
		return g.getFontMetrics(defaultFont).getStringBounds(string, g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see guiUtil.Draggable#startDrag(int, int)
	 */
	public void startDrag(double x, double y) {
		xoffset = x - this.bounds.x;
		yoffset = y - this.bounds.y;
		parent.stringDragStarted(this, bounds.x, bounds.y + bounds.height);
	}

	@Property(editable = false)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		recalculateBounds();
		parent.stringTextChanged(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see guiUtil.Draggable#drag(int, int)
	 */
	public void drag(double x, double y) {
		this.bounds.x = x - xoffset;
		this.bounds.y = y - yoffset;
		getParent().stringDragged(this, bounds.x, bounds.y + bounds.height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see guiUtil.Draggable#endDrag()
	 */
	public void endDrag() {
		getParent().stringDragEnded(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uml.UMLObject#clickOn(java.awt.Point)
	 */
	@Override
	public void clickOn(Point2D.Double point) {
		getParent().stringClicked(this, point);
		if (parent instanceof Focusable)
			diagram.giveFocus((Focusable) parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uml.UMLObject#DoubleclickOn(java.awt.Point)
	 */
	@Override
	public void dblClickOn(Double point) {
		getParent().stringDoubleClicked(this, point);
		if (parent instanceof Focusable)
			diagram.giveFocus((Focusable) parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uml.UMLObject#contains(java.awt.Point)
	 */
	@Override
	public boolean contains(Point2D.Double point) {
		if (isVisible() && bounds.contains(point)) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uml.UMLObject#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics2D g) {
		if (isVisible) {
			Color tempColor = g.getColor();
			if (isBackgroundRectVisible) {
				g.setColor(Color.BLUE);
				g.drawRect((int) bounds.x, (int) bounds.y, (int) bounds.width,
						(int) bounds.height);
			}
			g.setColor(Color.BLACK);
			g.setFont(defaultFont);
			g.drawString(getText(), (int) bounds.x + 3, (int) bounds.y
					+ (int) bounds.height - 6);
			g.setColor(tempColor);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uml.UMLObject#delete()
	 */
	@Override
	public void delete() {
		delete(true);
	}

	public void delete(boolean notifyParent) {
		super.delete();
		if (notifyParent)
			getParent().deleteString(this);
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setParent(StringContainer parent) {
		this.parent = parent;
	}

	public StringContainer getParent() {
		return parent;
	}

	@Override
	public Cursor getCursor() {
		return parent.giveCursorTo(this);
	}

	// @Property
	public Font getFont() {
		return defaultFont;
	}

	public void setFont(Font f) {

	}
}
