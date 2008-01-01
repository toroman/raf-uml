package edu.raf.uml.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import edu.raf.uml.gui.util.GuiPoint;
import edu.raf.uml.gui.util.MathUtil;

public abstract class UMLBoxRelation extends UMLRelation {
	public UMLBox from, to;

	public UMLBoxRelation(UMLDiagram diagram, UMLBox from, UMLBox to) {
		super(diagram);
		this.from = from;
		this.to = to;
		from.addRelation(this);
		to.addRelation(this);
		points.addFirst(new GuiPoint(diagram, this, from.x + from.width / 2, from.y - DISTANCE_FROM_UMLBOX));
		points.addLast(new GuiPoint(diagram, this, to.x + to.width / 2, to.y + +to.height + DISTANCE_FROM_UMLBOX));

		startString.setVisible(true);
		middleString.setVisible(true);
		endString.setVisible(true);
		calculatePointLocations();
}

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

		midx = to.x + to.width / 2;
		eastx = to.x + to.width;
		midy = to.y + to.height / 2;
		southy = to.y + to.height;
		if ((points.getLast().y <= midy) && (points.getLast().x - to.x >= points.getLast().y - to.y) && (eastx - points.getLast().x >= points.getLast().y - to.y)) {
			// Gornji kvadrant
			points.getLast().x = (int)MathUtil.getBetween(points.getLast().x, to.x, eastx);
			points.getLast().y = to.y - DISTANCE_FROM_UMLBOX;
		} else if ((points.getLast().y >= midy) && (southy - points.getLast().y <= points.getLast().x - to.x) && (southy - points.getLast().y <= eastx - points.getLast().x)) {
			// Donji kvadrant
			points.getLast().x = (int)MathUtil.getBetween(points.getLast().x, to.x, eastx);
			points.getLast().y = southy + DISTANCE_FROM_UMLBOX;
		} else if ((points.getLast().x <= midx) && (points.getLast().x - to.x <= points.getLast().y - to.y) && (points.getLast().x - to.x <= southy - points.getLast().y)) {
			// Levi kvadrant
			points.getLast().x = to.x - DISTANCE_FROM_UMLBOX;
			points.getLast().y = (int)MathUtil.getBetween(points.getLast().y, to.y, southy);
		} else {
			// Desni kvadrant
			points.getLast().x = eastx + DISTANCE_FROM_UMLBOX;
			points.getLast().y = (int)MathUtil.getBetween(points.getLast().y, to.y, southy);
		}
		for (UMLRelation relation: relations)
			relation.calculatePointLocations();
		
    	Point2D.Double bestPoint = this.getClosestPoint(new Point2D.Double (middleString.getBounds().x - 4, middleString.getBounds().y + middleString.getBounds().height + 4));
    	middleString.setX(bestPoint.x + 4);
    	middleString.setY(bestPoint.y - middleString.getBounds().height - 4);
    	if (from.x > points.getFirst().x)
    		startString.setX(from.x - startString.getBounds().width - 4);
    	else
    		startString.setX(points.getFirst().x + 4);
    	startString.setY(points.getFirst().y - startString.getBounds().height - 4);
    	if (to.x > points.getLast().x)
    		endString.setX(to.x - endString.getBounds().width - 4);
    	else
    		endString.setX(points.getLast().x + 4);
    	endString.setY (points.getLast().y - endString.getBounds().height - 4);
	}

	/*
	 * UMLObject methods
	 */

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

		if (to.y > points.getLast().y) {
			// Zavrshava se gore
			g.drawLine((int)points.getLast().x, (int)points.getLast().y, (int)points.getLast().x, (int)to.y);
		} else if (to.x > points.getLast().x) {
			// Zavrshava se levo
			g.drawLine((int)points.getLast().x, (int)points.getLast().y, (int)to.x, (int)points.getLast().y);
		} else if (to.y + to.height < points.getLast().y) {
			// Zavrshava se na dole
			g.drawLine((int)points.getLast().x, (int)points.getLast().y, (int)points.getLast().x, (int)to.y + (int)to.height);
		} else {
			// Zavrshava se desno
			g.drawLine((int)points.getLast().x, (int)points.getLast().y, (int)to.x + (int)to.width, (int)points.getLast().y);
		}

		g.setColor(tempColor); 
		if (line_dashed)
			((Graphics2D)g).setStroke(tempStroke);
	}

	@Override
	public boolean contains(Point2D.Double argumentPoint) {
		if (super.contains(argumentPoint))
			return true;

		if (from.y > points.getFirst().y) {
			// Pochinje na gore
			if (new Rectangle2D.Double(points.getFirst().x - CLICK_MISS_DISTANCE, points.getFirst().y, 2 * CLICK_MISS_DISTANCE + 1, DISTANCE_FROM_UMLBOX).contains(argumentPoint)) {
				return true;
			}
		} else if (from.x > points.getFirst().x) {
			// Pochinje na levo
			if (new Rectangle2D.Double(points.getFirst().x, points.getFirst().y - CLICK_MISS_DISTANCE, DISTANCE_FROM_UMLBOX, 2 * CLICK_MISS_DISTANCE + 1).contains(argumentPoint)) {
				return true;
			}
		} else if (from.y + from.height < points.getFirst().y) {
			// Pochinje na dole
			if (new Rectangle2D.Double(points.getFirst().x - CLICK_MISS_DISTANCE, from.y + from.height, 2 * CLICK_MISS_DISTANCE + 1, DISTANCE_FROM_UMLBOX).contains(argumentPoint)) {
				return true;
			}
		} else if (from.x + from.width < points.getFirst().x) {
			// Pochinje na desno
			if (new Rectangle2D.Double(from.x + from.width, points.getFirst().y - CLICK_MISS_DISTANCE, DISTANCE_FROM_UMLBOX, 2 * CLICK_MISS_DISTANCE + 1).contains(argumentPoint)) {
				return true;
			}
		}

		if (to.y > points.getLast().y) {
			// Pochinje na gore
			if (new Rectangle2D.Double(points.getLast().x - CLICK_MISS_DISTANCE, points.getLast().y, 2 * CLICK_MISS_DISTANCE + 1, DISTANCE_FROM_UMLBOX).contains(argumentPoint)) {
				return true;
			}
		} else if (to.x > points.getLast().x) {
			// Pochinje na levo
			if (new Rectangle2D.Double(points.getLast().x, points.getLast().y - CLICK_MISS_DISTANCE, DISTANCE_FROM_UMLBOX, 2 * CLICK_MISS_DISTANCE + 1).contains(argumentPoint)) {
				return true;
			}
		} else if (to.y + to.height < points.getLast().y) {
			// Pochinje na dole
			if (new Rectangle2D.Double(points.getLast().x - CLICK_MISS_DISTANCE, to.y + to.height, 2 * CLICK_MISS_DISTANCE + 1, DISTANCE_FROM_UMLBOX).contains(argumentPoint)) {
				return true;
			}
		} else if (to.x + to.width < points.getLast().x) {
			// Pochinje na desno
			if (new Rectangle2D.Double(to.x + to.width, points.getLast().y - CLICK_MISS_DISTANCE, DISTANCE_FROM_UMLBOX, 2 * CLICK_MISS_DISTANCE + 1).contains(argumentPoint)) {
				return true;
			}
		}

		return false;
	}
}
