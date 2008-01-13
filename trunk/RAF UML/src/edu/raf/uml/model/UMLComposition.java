package edu.raf.uml.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class UMLComposition extends UMLBoxRelation {
	public UMLComposition (UMLDiagram diagram, UMLBox from, UMLBox to) {
		super(diagram, from, to);
		startNorthString.setVisible(true);
		startSouthString.setVisible(true);
		middleString.setVisible(true);
		endNorthString.setVisible(true);
		endSouthString.setVisible(true);
		startNorthString.setText("1");
		endNorthString.setText("1");
	}
	
	@Override
    public void paint(Graphics2D g) {
    	super.paint(g);
        Color tempColor = g.getColor();
        Polygon polygon;

        if (from.y > points.getFirst().getY()) {
            // Pochinje na gore
            polygon = new Polygon(
                    new int[]{(int)points.getFirst().getX() - 4, (int)points.getFirst().getX(), (int)points.getFirst().getX() + 4, (int)points.getFirst().getX()},
                    new int[]{(int)from.y - 10, (int)from.y - 19, (int)from.y - 10, (int)from.y - 1},
                    4);
        } else if (from.x > points.getFirst().getX()) {
            // Pochinje na levo
            polygon = new Polygon(
                    new int[]{(int)from.x - 10, (int)from.x - 1, (int)from.x - 10, (int)from.x - 19},
                    new int[]{(int)points.getFirst().getY() - 4, (int)points.getFirst().getY(), (int)points.getFirst().getY() + 4, (int)points.getFirst().getY()},
                    4);
        } else if (from.y + from.height < points.getFirst().getY()) {
            // Pochinje na dole
            polygon = new Polygon(
                    new int[]{(int)points.getFirst().getX() - 4, (int)points.getFirst().getX(), (int)points.getFirst().getX() + 4, (int)points.getFirst().getX()},
                    new int[]{(int)from.y + (int)from.height + 10, (int)from.y + (int)from.height + 1, (int)from.y + (int)from.height + 10, (int)from.y + (int)from.height + 19},
                    4);
        } else {
            // Pochinje na desno
            polygon = new Polygon(
                    new int[]{(int)from.x + (int)from.width + 10, (int)from.x + (int)from.width + 1, (int)from.x + (int)from.width + 10, (int)from.x + (int)from.width + 19},
                    new int[]{(int)points.getFirst().getY() - 5, (int)points.getFirst().getY(), (int)points.getFirst().getY() + 5, (int)points.getFirst().getY()},
                    4);
        }
        g.setColor(Color.BLACK);
        g.fillPolygon(polygon);

        g.setColor(tempColor);
    }
}
