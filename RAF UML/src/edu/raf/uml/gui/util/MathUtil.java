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

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Klasa sa statichkim metodama koje se koriste za izrachunavanja tachaka
 * projekcije i tako toga. Uglavnom vezano za linearnu algebru.
 */
public final class MathUtil {

	/**
	 * Ova metoda vratja vaktor projekcije tachke C na duzh AB. Ako je r izmedju
	 * 0 i 1, onda je projekcija unutar AB, u suprotnom je na pravoj AB ali ne
	 * na duzhi.
	 */
	public static double getProjectionr(double ax, double ay, double bx,
			double by, double cx, double cy) {
		double r_numerator = (cx - ax) * (bx - ax) + (cy - ay) * (by - ay);
		double r_denomenator = (bx - ax) * (bx - ax) + (by - ay) * (by - ay);
		return r_numerator / r_denomenator;
	}

	/**
	 * Ova metoda vratja vaktor projekcije tachke C na duzh AB. Ako je r izmedju
	 * 0 i 1, onda je projekcija unutar AB, u suprotnom je na pravoj AB ali ne
	 * na duzhi.
	 */
	public static double getProjectionr(Point2D.Double a, Point2D.Double b,
			Point2D.Double c) {
		double r_numerator = (c.x - a.x) * (b.x - a.x) + (c.y - a.y)
				* (b.y - a.y);
		double r_denomenator = (b.x - a.x) * (b.x - a.x) + (b.y - a.y)
				* (b.y - a.y);
		return r_numerator / r_denomenator;
	}

	/**
	 * Slichno metodi getProjectionr, osim shto vratja tu tachku umesto njenog
	 * linearnog faktora.
	 */
	public static Point2D.Double getProjectionPoint(double ax, double ay,
			double bx, double by, double cx, double cy) {
		double r = getProjectionr(ax, ay, bx, by, cx, cy);
		return getProjectionPoint(ax, ay, bx, by, r);
	}

	/**
	 * Generishe tachku projekcije na osnovu duzhi AB i faktora r.
	 */
	public static Point2D.Double getProjectionPoint(double ax, double ay,
			double bx, double by, double r) {
		return new Point2D.Double(ax + r * (bx - ax), ay + r * (by - ay));
	}

	/**
	 * Vratja daljinu izmedju dve tachke.
	 */
	public static double pointDistance(double ax, double ay, double bx,
			double by) {
		return Math.sqrt((ax - bx) * (ax - bx) + (ay - by) * (ay - by));
	}

	/**
	 * Vratja daljinu izmedju dve tachke.
	 */
	public static double pointDistance(Point2D.Double a, Point2D.Double b) {
		return pointDistance(a.x, a.y, b.x, b.y);
	}

	/**
	 * Ova metoda "pomera" neki broj tako da on pripadne nekom intervalu, ili ga
	 * ne menja ukoliko je on unutra.
	 */
	public static double getBetween(double num, double min, double max) {
		if (num < min) {
			return min;
		}
		if (num > max) {
			return max;
		}
		return num;
	}

	public static Point2D.Double toPoint2D(Point point) {
		return new Point2D.Double(point.x, point.y);
	}
}
