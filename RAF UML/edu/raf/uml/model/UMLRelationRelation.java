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
        
        double newx = (to.points.getFirst().x + to.points.getLast().x) / 2;
        double newy = (to.points.getFirst().y + to.points.getLast().y) / 2;
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
        if ((points.getFirst().y <= midy) && (points.getFirst().x - from.x >= points.getFirst().y - from.y) && (eastx - points.getFirst().x >= points.getFirst().y - from.y)) {
            // Gornji kvadrant
            points.getFirst().x = (int)MathUtil.getBetween(points.getFirst().x, from.x, eastx);
            points.getFirst().y = from.y - DISTANCE_FROM_UMLBOX;
        } else if ((points.getFirst().y >= midy) && (southy - points.getFirst().y <= points.getFirst().x - from.x) && (southy - points.getFirst().y <= eastx - points.getFirst().x)) {
            // Donji kvadrant
            points.getFirst().x = (int)MathUtil.getBetween(points.getFirst().x, from.x, eastx);
            points.getFirst().y = southy + DISTANCE_FROM_UMLBOX;
        } else if ((points.getFirst().x <= midx) && (points.getFirst().x - from.x <= points.getFirst().y - from.y) && (points.getFirst().x - from.x <= southy - points.getFirst().y)) {
            // Levi kvadrant
            points.getFirst().x = from.x - DISTANCE_FROM_UMLBOX;
            points.getFirst().y = (int)MathUtil.getBetween(points.getFirst().y, from.y, southy);
        } else {
            // Desni kvadrant
            points.getFirst().x = eastx + DISTANCE_FROM_UMLBOX;
            points.getFirst().y = (int)MathUtil.getBetween(points.getFirst().y, from.y, southy);
        }

        Point2D.Double bestPoint = to.getClosestPoint(points.getLast().toPoint());
        points.getLast().x = bestPoint.x;
        points.getLast().y = bestPoint.y;
    	for (UMLRelation relation: relations)
    		relation.calculatePointLocations();
    	
    	bestPoint = this.getClosestPoint(new Point2D.Double (middleString.getBounds().x - 4, middleString.getBounds().y + middleString.getBounds().height + 4));
    	middleString.getBounds().x = bestPoint.x + 4;
    	middleString.getBounds().y = bestPoint.y - middleString.getBounds().height - 4;
    	if (from.x > points.getFirst().x) {
    		startNorthString.getBounds().x = from.x - startNorthString.getBounds().width - 4;
    		startSouthString.getBounds().x = from.x - startSouthString.getBounds().width - 4;
    	}
    	else {
    		startNorthString.getBounds().x = points.getFirst().x + 4;
    		startSouthString.getBounds().x = points.getFirst().x + 4;
    	}
    	startNorthString.getBounds().y = points.getFirst().y - startNorthString.getBounds().height - 4;
    	startNorthString.getBounds().y = points.getFirst().y + 4;
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
        if (from.y > points.getFirst().y) {
            // Pochinje na gore
            g.drawLine((int)points.getFirst().x, (int)points.getFirst().y, (int)points.getFirst().x, (int)from.y);
        } else if (from.x > points.getFirst().x) {
            // Pochinje na levo
            g.drawLine((int)points.getFirst().x, (int)points.getFirst().y, (int)from.x, (int)points.getFirst().y);
        } else if (from.y + from.height < points.getFirst().y) {
            // Pochinje na dole
            g.drawLine((int)points.getFirst().x, (int)points.getFirst().y, (int)points.getFirst().x, (int)from.y + (int)from.height);
        } else if (from.x + from.width < points.getFirst().x) {
            // Pochinje na desno
            g.drawLine((int)points.getFirst().x, (int)points.getFirst().y, (int)from.x + (int)from.width, (int)points.getFirst().y);
        }
        
        g.setColor(tempColor);
        if (line_dashed)
       		((Graphics2D)g).setStroke(tempStroke);
	}
}
