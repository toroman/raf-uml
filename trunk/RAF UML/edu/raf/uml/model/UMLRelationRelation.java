package edu.raf.uml.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import edu.raf.uml.gui.util.GuiPoint;
import edu.raf.uml.gui.util.MathUtil;

public class UMLRelationRelation extends UMLRelation {
	
	public UMLBox from;
	public UMLRelation to;
	
	public UMLRelationRelation(UMLDiagram diagram, UMLBox from, UMLRelation to) {
		super(diagram);
		this.from = from;
		this.to = to;
		to.relations.add(this);
		from.relations.add(this);
        points.addFirst(new GuiPoint(diagram, this, from.x + from.width / 2, from.y - DISTANCE_FROM_UMLBOX));
        
        double newx = (to.points.getFirst().getX() + to.points.getLast().getX()) / 2;
        double newy = (to.points.getFirst().getY() + to.points.getLast().getY()) / 2;
		points.addLast(new GuiPoint (diagram, this, newx, newy));
		startNorthString.setVisible(false);
		startSouthString.setVisible(false);
		middleString.setVisible(false);
		endNorthString.setVisible(false);
		endSouthString.setVisible(false);
		calculatePointLocations();
	}
	
	@Override
	public void calculatePointLocations() {
        double midx = from.x + from.width / 2;
        double eastx = from.x + from.width;
        double midy = from.y + from.height / 2;
        double southy = from.y + from.height;
        if ((points.getFirst().getY() <= midy) && (points.getFirst().getX() - from.x >= points.getFirst().getY() - from.y) && (eastx - points.getFirst().getX() >= points.getFirst().getY() - from.y)) {
            // Gornji kvadrant
            points.getFirst().setX((int)MathUtil.getBetween(points.getFirst().getX(), from.x, eastx));
            points.getFirst().setY(from.y - DISTANCE_FROM_UMLBOX);
        } else if ((points.getFirst().getY() >= midy) && (southy - points.getFirst().getY() <= points.getFirst().getX() - from.x) && (southy - points.getFirst().getY() <= eastx - points.getFirst().getX())) {
            // Donji kvadrant
            points.getFirst().setX((int)MathUtil.getBetween(points.getFirst().getX(), from.x, eastx));
            points.getFirst().setY(southy + DISTANCE_FROM_UMLBOX);
        } else if ((points.getFirst().getX() <= midx) && (points.getFirst().getX() - from.x <= points.getFirst().getY() - from.y) && (points.getFirst().getX() - from.x <= southy - points.getFirst().getY())) {
            // Levi kvadrant
            points.getFirst().setX(from.x - DISTANCE_FROM_UMLBOX);
            points.getFirst().setY((int)MathUtil.getBetween(points.getFirst().getY(), from.y, southy));
        } else {
            // Desni kvadrant
            points.getFirst().setX(eastx + DISTANCE_FROM_UMLBOX);
            points.getFirst().setY((int)MathUtil.getBetween(points.getFirst().getY(), from.y, southy));
        }

        Point2D.Double bestPoint = to.getClosestPoint(points.getLast().toPoint());
        points.getLast().setX(bestPoint.x);
        points.getLast().setY(bestPoint.y);
    	for (UMLRelation relation: relations)
    		relation.calculatePointLocations();
    	
    	bestPoint = this.getClosestPoint(new Point2D.Double (middleString.getBounds().x - 4, middleString.getBounds().y + middleString.getBounds().height + 4));
    	middleString.getBounds().x = bestPoint.x + 4;
    	middleString.getBounds().y = bestPoint.y - middleString.getBounds().height - 4;
    	if (from.x > points.getFirst().getX()) {
    		startNorthString.getBounds().x = from.x - startNorthString.getBounds().width - 4;
    		startSouthString.getBounds().x = from.x - startSouthString.getBounds().width - 4;
    	}
    	else {
    		startNorthString.getBounds().x = points.getFirst().getX() + 4;
    		startSouthString.getBounds().x = points.getFirst().getX() + 4;
    	}
    	startNorthString.getBounds().y = points.getFirst().getY() - startNorthString.getBounds().height - 4;
    	startNorthString.getBounds().y = points.getFirst().getY() + 4;
	}

	@Override
	public void paint(Graphics2D g) {
    	Stroke tempStroke = null;
    	if (line_dashed) {
    		tempStroke = ((Graphics2D)g).getStroke();
    		((Graphics2D)g).setStroke(new BasicStroke (
    				1.0f,
    				BasicStroke.CAP_BUTT,
    				BasicStroke.JOIN_MITER,
    				1.0f,
    				new float[] {5.0f, 5.0f},
    				0));
    	}

		super.paint(g);
        Color tempColor = g.getColor();

        g.setColor(Color.BLACK);
        if (from.y > points.getFirst().getY()) {
            // Pochinje na gore
            g.drawLine((int)points.getFirst().getX(), (int)points.getFirst().getY(), (int)points.getFirst().getX(), (int)from.y);
        } else if (from.x > points.getFirst().getX()) {
            // Pochinje na levo
            g.drawLine((int)points.getFirst().getX(), (int)points.getFirst().getY(), (int)from.x, (int)points.getFirst().getY());
        } else if (from.y + from.height < points.getFirst().getY()) {
            // Pochinje na dole
            g.drawLine((int)points.getFirst().getX(), (int)points.getFirst().getY(), (int)points.getFirst().getX(), (int)from.y + (int)from.height);
        } else if (from.x + from.width < points.getFirst().getX()) {
            // Pochinje na desno
            g.drawLine((int)points.getFirst().getX(), (int)points.getFirst().getY(), (int)from.x + (int)from.width, (int)points.getFirst().getY());
        }
        
        g.setColor(tempColor);
        if (line_dashed)
       		((Graphics2D)g).setStroke(tempStroke);
	}
}
