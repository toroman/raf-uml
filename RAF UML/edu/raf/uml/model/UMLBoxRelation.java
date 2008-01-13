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

		midx = to.x + to.width / 2;
		eastx = to.x + to.width;
		midy = to.y + to.height / 2;
		southy = to.y + to.height;
		if ((points.getLast().getY() <= midy) && (points.getLast().getX() - to.x >= points.getLast().getY() - to.y) && (eastx - points.getLast().getX() >= points.getLast().getY() - to.y)) {
			// Gornji kvadrant
			points.getLast().setX((int)MathUtil.getBetween(points.getLast().getX(), to.x, eastx));
			points.getLast().setY(to.y - DISTANCE_FROM_UMLBOX);
		} else if ((points.getLast().getY() >= midy) && (southy - points.getLast().getY() <= points.getLast().getX() - to.x) && (southy - points.getLast().getY() <= eastx - points.getLast().getX())) {
			// Donji kvadrant
			points.getLast().setX((int)MathUtil.getBetween(points.getLast().getX(), to.x, eastx));
			points.getLast().setY(southy + DISTANCE_FROM_UMLBOX);
		} else if ((points.getLast().getX() <= midx) && (points.getLast().getX() - to.x <= points.getLast().getY() - to.y) && (points.getLast().getX() - to.x <= southy - points.getLast().getY())) {
			// Levi kvadrant
			points.getLast().setX(to.x - DISTANCE_FROM_UMLBOX);
			points.getLast().setY((int)MathUtil.getBetween(points.getLast().getY(), to.y, southy));
		} else {
			// Desni kvadrant
			points.getLast().setX(eastx + DISTANCE_FROM_UMLBOX);
			points.getLast().setY((int)MathUtil.getBetween(points.getLast().getY(), to.y, southy));
		}
		for (UMLRelation relation: relations)
			relation.calculatePointLocations();
		
    	Point2D.Double bestPoint = this.getClosestPoint(new Point2D.Double (middleString.getBounds().x - 4, middleString.getBounds().y + middleString.getBounds().height + 4));
    	middleString.setX(bestPoint.x + 4);
    	middleString.setY(bestPoint.y - middleString.getBounds().height - 4);
    	if (from.x > points.getFirst().getX()) {
    		startNorthString.setX(from.x - startNorthString.getBounds().width - 4);
    		startSouthString.setX(from.x - startSouthString.getBounds().width - 4);
    	}
    	else {
    		startNorthString.setX(points.getFirst().getX() + 4);
    		startSouthString.setX(points.getFirst().getX() + 4);
    	}
    	startNorthString.setY(points.getFirst().getY() - startNorthString.getBounds().height - 4);
    	startSouthString.setY(points.getFirst().getY() + 4);
    	if (to.x > points.getLast().getX()) {
    		endNorthString.setX(to.x - endNorthString.getBounds().width - 4);
    		endSouthString.setX(to.x - endSouthString.getBounds().width - 4);
    	}
    	else {
    		endNorthString.setX(points.getLast().getX() + 4);
    		endSouthString.setX(points.getLast().getX() + 4);
    	}
    	endNorthString.setY (points.getLast().getY() - endNorthString.getBounds().height - 4);
    	endSouthString.setY (points.getLast().getY() + 4);
	}

	/*
	 * UMLObject methods
	 */

	@Override
	public void paint(Graphics2D g) {
		Stroke tempStroke = null;
		if (lineDashed) {
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

		if (to.y > points.getLast().getY()) {
			// Zavrshava se gore
			g.drawLine((int)points.getLast().getX(), (int)points.getLast().getY(), (int)points.getLast().getX(), (int)to.y);
		} else if (to.x > points.getLast().getX()) {
			// Zavrshava se levo
			g.drawLine((int)points.getLast().getX(), (int)points.getLast().getY(), (int)to.x, (int)points.getLast().getY());
		} else if (to.y + to.height < points.getLast().getY()) {
			// Zavrshava se na dole
			g.drawLine((int)points.getLast().getX(), (int)points.getLast().getY(), (int)points.getLast().getX(), (int)to.y + (int)to.height);
		} else {
			// Zavrshava se desno
			g.drawLine((int)points.getLast().getX(), (int)points.getLast().getY(), (int)to.x + (int)to.width, (int)points.getLast().getY());
		}

		g.setColor(tempColor); 
		if (lineDashed)
			((Graphics2D)g).setStroke(tempStroke);
	}

	@Override
	public boolean contains(Point2D.Double argumentPoint) {
		if (super.contains(argumentPoint))
			return true;

		if (from.y > points.getFirst().getY()) {
			// Pochinje na gore
			if (new Rectangle2D.Double(points.getFirst().getX() - CLICK_MISS_DISTANCE, points.getFirst().getY(), 2 * CLICK_MISS_DISTANCE + 1, DISTANCE_FROM_UMLBOX).contains(argumentPoint)) {
				return true;
			}
		} else if (from.x > points.getFirst().getX()) {
			// Pochinje na levo
			if (new Rectangle2D.Double(points.getFirst().getX(), points.getFirst().getY() - CLICK_MISS_DISTANCE, DISTANCE_FROM_UMLBOX, 2 * CLICK_MISS_DISTANCE + 1).contains(argumentPoint)) {
				return true;
			}
		} else if (from.y + from.height < points.getFirst().getY()) {
			// Pochinje na dole
			if (new Rectangle2D.Double(points.getFirst().getX() - CLICK_MISS_DISTANCE, from.y + from.height, 2 * CLICK_MISS_DISTANCE + 1, DISTANCE_FROM_UMLBOX).contains(argumentPoint)) {
				return true;
			}
		} else if (from.x + from.width < points.getFirst().getX()) {
			// Pochinje na desno
			if (new Rectangle2D.Double(from.x + from.width, points.getFirst().getY() - CLICK_MISS_DISTANCE, DISTANCE_FROM_UMLBOX, 2 * CLICK_MISS_DISTANCE + 1).contains(argumentPoint)) {
				return true;
			}
		}

		if (to.y > points.getLast().getY()) {
			// Pochinje na gore
			if (new Rectangle2D.Double(points.getLast().getX() - CLICK_MISS_DISTANCE, points.getLast().getY(), 2 * CLICK_MISS_DISTANCE + 1, DISTANCE_FROM_UMLBOX).contains(argumentPoint)) {
				return true;
			}
		} else if (to.x > points.getLast().getX()) {
			// Pochinje na levo
			if (new Rectangle2D.Double(points.getLast().getX(), points.getLast().getY() - CLICK_MISS_DISTANCE, DISTANCE_FROM_UMLBOX, 2 * CLICK_MISS_DISTANCE + 1).contains(argumentPoint)) {
				return true;
			}
		} else if (to.y + to.height < points.getLast().getY()) {
			// Pochinje na dole
			if (new Rectangle2D.Double(points.getLast().getX() - CLICK_MISS_DISTANCE, to.y + to.height, 2 * CLICK_MISS_DISTANCE + 1, DISTANCE_FROM_UMLBOX).contains(argumentPoint)) {
				return true;
			}
		} else if (to.x + to.width < points.getLast().getX()) {
			// Pochinje na desno
			if (new Rectangle2D.Double(to.x + to.width, points.getLast().getY() - CLICK_MISS_DISTANCE, DISTANCE_FROM_UMLBOX, 2 * CLICK_MISS_DISTANCE + 1).contains(argumentPoint)) {
				return true;
			}
		}

		return false;
	}
}
