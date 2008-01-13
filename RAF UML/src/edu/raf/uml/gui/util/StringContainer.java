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

import java.awt.Cursor;
import java.awt.geom.Point2D;

public interface StringContainer {
	public void stringClicked(GuiString guiString, Point2D.Double clickLocation);

	public void stringDoubleClicked(GuiString guiString,
			Point2D.Double clickLocation);

	public void stringDragStarted(GuiString guiString, double x, double y);

	public void stringDragged(GuiString guiString, double x, double y);

	public void stringDragEnded(GuiString guiString);

	public void moveString(GuiString guiString, double x, double y);

	public void deleteString(GuiString guiString);

	public void stringSizeChanged(GuiString guiString);

	public void stringTextChanged(GuiString guiString);

	public Cursor giveCursorTo(GuiString guiString);
}
