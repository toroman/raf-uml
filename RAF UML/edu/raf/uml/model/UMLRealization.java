package edu.raf.uml.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class UMLRealization extends UMLBoxRelation{

    public UMLRealization(UMLDiagram diagram, UMLBox from, UMLBox to) {
        super(diagram, from, to);    
        line_dashed = true;
        middleString.setVisible(true);
        middleString.setText("<< realize >>");
    }
   
    @Override
    public void paint(Graphics2D g) {
    	super.paint(g);
        Color tempColor = g.getColor();
        Polygon polygon;

        if (to.y > points.getLast().getY()) {
            // Zavrshava se gore
            polygon = new Polygon(
                    new int[]{(int)points.getLast().getX() - 5, (int)points.getLast().getX() + 5, (int)points.getLast().getX()},
                    new int[]{(int)to.y - 10, (int)to.y - 10, (int)to.y - 1},
                    3);
        } else if (to.x > points.getLast().getX()) {
            // Zavrshava se levo
            polygon = new Polygon(
                    new int[]{(int)to.x - 10, (int)to.x - 1, (int)to.x - 10},
                    new int[]{(int)points.getLast().getY() - 5, (int)points.getLast().getY(), (int)points.getLast().getY() + 5},
                    3);
        } else if (to.y + to.height < points.getLast().getY()) {
            // Zavrshava se na dole
            polygon = new Polygon(
                    new int[]{(int)points.getLast().getX() - 5, (int)points.getLast().getX(), (int)points.getLast().getX() + 5},
                    new int[]{(int)to.y + (int)to.height + 10, (int)to.y + (int)to.height + 1, (int)to.y + (int)to.height + 10},
                    3);
        } else {
            // Zavrshava se desno
            polygon = new Polygon(
                    new int[]{(int)to.x + (int)to.width + 10, (int)to.x + (int)to.width + 1, (int)to.x + (int)to.width + 10},
                    new int[]{(int)points.getLast().getY() - 5, (int)points.getLast().getY(), (int)points.getLast().getY() + 5},
                    3);
        }
        g.setColor(Color.WHITE);
        g.fillPolygon(polygon);
        g.setColor(Color.BLACK);
        g.drawPolygon(polygon);

        g.setColor(tempColor);
    }
}
