package edu.raf.uml.gui.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;

import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLObject;

public class GuiString extends UMLObject implements Draggable {

    private Rectangle2D.Double bounds;
    private String text;
    private boolean isVisible = false;
    public boolean isBackgroundRectVisible = false;
    private StringContainer parent;
    private double xoffset,  yoffset;

    public GuiString(UMLDiagram diagram, StringContainer parent, String text, double x, double y) {
        super(diagram);
        this.setParent(parent);
        bounds = new Rectangle2D.Double (x, y, 0, 0);
        this.text = text;
        recalculateBounds();
    }
    
    public Rectangle2D.Double getBounds () {
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

    public double calculateMinWidth () {
    	return calculateStringSize(text).width + 8;
    }
    
    public GuiString (UMLDiagram diagram, StringContainer parent) {
    	this (diagram, parent, "", 0, 0);
    }

    public void recalculateBounds () {
    	Dimension kme = calculateStringSize(text);
    	bounds.height = kme.height + 7;
    	bounds.width = Math.max(kme.width + 8, bounds.width);
    	parent.stringSizeChanged(this);
    }
    
    private static int getCharWidth (char c) {
    	switch (c) {
    	case '!': return 3;
    	case '"': return 3;
    	case '#': return 7;
    	case '$': return 5;
    	case '%': return 10;
    	case '&': return 6;
    	case '\'': return 1;
    	case '(': return 3;
    	case ')': return 3;
    	case '*': return 5;
    	case '+': return 7;
       	case ',': return 3;
       	case '-': return 3;
       	case '.': return 3;
       	case '/': return 3;
       	case '0': return 5;
       	case '1': return 5;
       	case '2': return 5;
       	case '3': return 5;
       	case '4': return 5;
       	case '5': return 5;
       	case '6': return 5;
       	case '7': return 5;
       	case '8': return 5;
       	case '9': return 5;
       	case ':': return 3;
       	case ';': return 3;
       	case '<': return 7;
       	case '=': return 7;
       	case '>': return 7;
       	case '?': return 4;
       	case '@': return 9;
       	case 'A': return 6;
       	case 'B': return 5;
       	case 'C': return 6;
       	case 'D': return 6;
       	case 'E': return 5;
       	case 'F': return 5;
       	case 'G': return 6;
       	case 'H': return 6;
       	case 'I': return 3;
       	case 'J': return 4;
       	case 'K': return 5;
       	case 'L': return 4;
       	case 'M': return 7;
       	case 'N': return 6;
       	case 'O': return 7;
       	case 'P': return 5;
       	case 'Q': return 7;
       	case 'R': return 6;
       	case 'S': return 5;
       	case 'T': return 5;
       	case 'U': return 6;
       	case 'V': return 5;
       	case 'W': return 9;
       	case 'X': return 5;
       	case 'Y': return 5;
       	case 'Z': return 5;
       	case '[': return 3;
       	case '\\': return 3;
       	case ']': return 3;
       	case '^': return 7;
       	case '_': return 6;
       	case '`': return 4;
       	case 'a': return 5;
       	case 'b': return 5;
       	case 'c': return 4;
       	case 'd': return 5;
       	case 'e': return 5;
       	case 'f': return 3;
       	case 'g': return 5;
       	case 'h': return 5;
       	case 'i': return 1;
       	case 'j': return 5;
       	case 'k': return 4;
       	case 'l': return 1;
       	case 'm': return 7;
       	case 'n': return 5;
       	case 'o': return 5;
       	case 'p': return 5;
       	case 'q': return 5;
       	case 'r': return 3;
       	case 's': return 4;
       	case 't': return 3;
       	case 'u': return 5;
       	case 'v': return 5;
       	case 'w': return 7;
       	case 'x': return 5;
       	case 'y': return 5;
       	case 'z': return 4;
       	case '{': return 4;
       	case '|': return 3;
       	case '}': return 4;
       	case '~': return 7;
       	case ' ': return 1;
    	default: return 0;
    	}
    }
    
    public static Dimension calculateStringSize (String string) {
    	int width = 0;
    	for (char c: string.toCharArray())
    		width += 1 + getCharWidth(c);
    	return new Dimension (width, 11);
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
        	diagram.giveFocus((Focusable)parent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uml.UMLObject#DoubleclickOn(java.awt.Point)
     */
    @Override
    public void DoubleclickOn(Double point) {
        getParent().stringDoubleClicked(this, point);
        diagram.panel.showGuiStringTextField(this);
        if (parent instanceof Focusable)
        	diagram.giveFocus((Focusable)parent);
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
    			g.drawRect((int)bounds.x, (int)bounds.y, (int)bounds.width, (int)bounds.height);
    		}
    		g.setColor(Color.BLACK);
    		g.drawString(text, (int)bounds.x + 3, (int)bounds.y + (int)bounds.height - 4); 
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
        delete (true);
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
}
