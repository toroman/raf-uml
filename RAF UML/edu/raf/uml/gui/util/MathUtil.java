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
    public static double getProjectionr(int ax, int ay, int bx, int by, int cx,
            int cy) {
        double r_numerator = (cx - ax) * (bx - ax) + (cy - ay) * (by - ay);
        double r_denomenator = (bx - ax) * (bx - ax) + (by - ay) * (by - ay);
        return r_numerator / r_denomenator;
    }

    /**
     * Slichno metodi getProjectionr, osim shto vratja tu tachku umesto njenog
     * linearnog faktora.
     */
    public static Point getProjectionPoint(int ax, int ay, int bx, int by,
            int cx, int cy) {
        double r = getProjectionr(ax, ay, bx, by, cx, cy);
        return getProjectionPoint(ax, ay, bx, by, r);
    }

    /**
     * Generishe tachku projekcije na osnovu duzhi AB i faktora r.
     */
    public static Point getProjectionPoint(int ax, int ay, int bx, int by,
            double r) {
        return new Point((int) (ax + r * (bx - ax)), (int) (ay + r * (by - ay)));
    }

    /**
     * Vratja daljinu izmedju dve tachke.
     */
    public static double pointDistance(int ax, int ay, int bx, int by) {
        return Math.sqrt((ax - bx) * (ax - bx) + (ay - by) * (ay - by));
    }

    /**
     * Vratja daljinu izmedju dve tachke.
     */
    public static double pointDistance(Point a, Point b) {
        return pointDistance(a.x, a.y, b.x, b.y);
    }

    /**
     * Ova metoda "pomera" neki broj tako da on pripadne nekom intervalu, ili ga
     * ne menja ukoliko je on unutra.
     */
    public static int getBetween(int num, int min, int max) {
        if (num < min) {
            return min;
        }
        if (num > max) {
            return max;
        }
        return num;
    }
}
