package edu.raf.uml.gui.util;

import java.awt.Cursor;
import java.awt.geom.Point2D;

public interface StringContainer {
    public void stringClicked(GuiString guiString, Point2D.Double clickLocation);
    public void stringDoubleClicked(GuiString guiString, Point2D.Double clickLocation);
    public void stringDragStarted(GuiString guiString, double x, double y);
    public void stringDragged(GuiString guiString, double x, double y);
    public void stringDragEnded(GuiString guiString);
    public void moveString(GuiString guiString, double x, double y);
    public void deleteString(GuiString guiString);  
    public void stringSizeChanged (GuiString guiString);
    public void stringTextChanged (GuiString guiString);
    public Cursor giveCursorTo (GuiString guiString);
}
