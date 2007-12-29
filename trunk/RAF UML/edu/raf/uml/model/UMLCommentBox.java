package edu.raf.uml.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class UMLCommentBox extends UMLBox {

    public String text;
    private Polygon shapePolygon, edgeTriangle;

    /*
     * 0--1
     * |   \
     * |    2
     * 4----3
     */
    
    public UMLCommentBox (UMLDiagram diagram, int x, int y) {
        super(diagram, x, y, 0, 0);
        shapePolygon = new Polygon (
        		new int [] {x, x+width-EDGE_TRIM_SIZE, x+width, x + width, x}, 
        		new int [] {y, y, y+EDGE_TRIM_SIZE, y+width, y+width},
        		5);
        edgeTriangle = new Polygon (
        		new int [] {x+width-EDGE_TRIM_SIZE, x, x+width}, 
        		new int [] {y, y+EDGE_TRIM_SIZE, y+EDGE_TRIM_SIZE}, 
        		3);
        text = "";
        this.movePoint(sePoint, -1, -1);
    }

    @Override
    public int calculateMinHeight() {
        return 100;
    }

    @Override
    public int calculateMinWidth() {
        return 100;
    }

    @Override
    public void paint(Graphics g) {
        Color tempColor = g.getColor();
        g.setColor(Color.WHITE);
        g.fillPolygon(shapePolygon);
        g.setColor(Color.DARK_GRAY);
        g.drawPolygon(shapePolygon);
        g.setColor(Color.GRAY);
        g.fillPolygon(edgeTriangle);
        g.setColor(Color.DARK_GRAY);
        g.drawPolygon(edgeTriangle);
         
        g.setColor(Color.BLACK);
        g.drawString(text, x + 3, y + 15);
        g.setColor(tempColor);
    }
    
    @Override
    public void calculatePointLocations() {
    	super.calculatePointLocations();
    	shapePolygon.xpoints[0] = x;
    	shapePolygon.xpoints[1] = x+width-EDGE_TRIM_SIZE;
    	shapePolygon.xpoints[2] = x+width;
    	shapePolygon.xpoints[3] = x+width;
    	shapePolygon.xpoints[4] = x;
 
    	shapePolygon.ypoints[0] = y;
    	shapePolygon.ypoints[1] = y;
    	shapePolygon.ypoints[2] = y+EDGE_TRIM_SIZE;
    	shapePolygon.ypoints[3] = y+height;
    	shapePolygon.ypoints[4] = y+height;

		edgeTriangle.xpoints[0] = x+width-EDGE_TRIM_SIZE;
		edgeTriangle.xpoints[1] = x+width-EDGE_TRIM_SIZE;
		edgeTriangle.xpoints[2] = x+width;

		edgeTriangle.ypoints[0] = y;
		edgeTriangle.ypoints[1] = y+EDGE_TRIM_SIZE;
		edgeTriangle.ypoints[2] = y+EDGE_TRIM_SIZE;    
    }
    
    /**
     * Velichina trougle u tjoshku, merena u pixelima.
     */
    private static final int EDGE_TRIM_SIZE = 14;
}
