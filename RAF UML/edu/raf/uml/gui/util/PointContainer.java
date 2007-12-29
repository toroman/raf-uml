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

import java.awt.geom.Point2D;;

/**
 * Ovaj interfejs implementira svaka klasa koja "sadrzhi" GuiPoint objekte.
 * Pogledaj GuiPoint..
 */
public interface PointContainer {

    /**
     * Kada se jednom klikne na neku tachku, ona prijavi svom parentu da je
     * kliknuta, i parent obradi taj klik u ovoj metodi.
     */
    public void pointClicked(GuiPoint guiPoint, Point2D.Double clickLocation);

    /**
     * Kada se dvaput klikne na neku tachku, ona prijavi svom parentu da je
     * kliknuta, i parent obradi taj klik u ovoj metodi.
     */
    public void pointDoubleClicked(GuiPoint guiPoint, Point2D.Double clickLocation);

    /**
     * GuiPoint je Draggable, i mora da prijavi svom parentu da je njen drag
     * otpocheo.
     */
    public void pointDragStarted(GuiPoint guiPoint, double x, double y);

    /**
     * GuiPoint je Draggable, i mora da prijavi svom parentu da je drag u toku.
     */
    public void pointDragged(GuiPoint guiPoint, double x, double y);

    /**
     * GuiPoint je Draggable, i mora da prijavi svom parentu da je drag gotov.
     */
    public void pointDragEnded(GuiPoint guiPoint);

    /**
     * Kada god GuiPoint promeni koordinate, iz bilo kog razloga, on to javi
     * parentu.
     */
    public void movePoint(GuiPoint guiPoint, double x, double y);

    /**
     * Kada tachka treba da se obrishe, ona javi parentu da je upravo obrisana.
     */
    public void deletePoint(GuiPoint guiPoint);
}
