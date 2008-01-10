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
package edu.raf.uml.model;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import edu.raf.uml.gui.util.GuiString;
import edu.raf.uml.model.property.Property;
import edu.raf.uml.model.property.TypeModel;

public class UMLClass extends UMLBox {

    public GuiString className;
    public ArrayList<GuiString> methods;
    public ArrayList<UMLField> fields;
    public static FontMetrics fontMetrics;
    
    public UMLClass(UMLDiagram diagram, double x, double y) {
        super(diagram, x, y, 0, 0);
        methods = new ArrayList<GuiString>();
        fields = new ArrayList<UMLField>();
        className = new GuiString (diagram, this);
        className.setText("New Class");
        className.setVisible(true);
        this.movePoint(sePoint, -1, -1);
    }
    
    @Property
    public String getName() {
    	return className.getText();
    }
    
    public void setName(String className){
    	if (!className.matches("[A-z0-9_$]+"))
    		throw new IllegalArgumentException("Ime klase mora biti od slova, brojeva i _$");
    	this.className.setText(className);
    	diagram.panel.repaint();
    }
    
    public void addMethod () {
    	GuiString newString; 
    	methods.add(newString = new GuiString (diagram, this));
    	newString.setVisible(true);
    	newString.setText("New Method");
    	calculatePointLocations();
    	diagram.giveFocus(this);
    }

    public void addField () {
    	UMLField field = new UMLField (diagram, this); 
    	fields.add(field);
    	field.setVisible(true);
    	field.setType(new TypeModel());
    	field.setModifiers("");
    	field.setName("value");
    	calculatePointLocations();
    	diagram.giveFocus(this);
    }

    
    @Override
    public double calculateMinHeight() {
        if (className != null)
        	return (className.getBounds().height + 2) * (1 + Math.max(fields.size(), 1) + Math.max(methods.size(), 1)) + 12;
        else
        	return 10;
    }

    @Override
    public double calculateMinWidth() {
    	double minw;
        if (className != null)
        	minw = className.calculateMinWidth();
        else
        	minw = 0;
    	for (GuiString kme: fields)
    		if (kme.getBounds().width > minw)
    			minw = kme.calculateMinWidth();
    	for (GuiString kme: methods)
    		if (kme.getBounds().width > minw)
    			minw = kme.calculateMinWidth();
        return minw + RIGHT_BLANK_SPACE_WIDTH;
    }

    @Override
    public void paint(Graphics2D g) {
        super.paint(g);
        Color tempColor = g.getColor();
        g.setColor(Color.BLACK);
        g.drawLine((int)x, (int)y + (int)className.getBounds().height + 6, (int)x + (int)width, (int)y + (int)className.getBounds().height + 6);
        double linemody = Math.max(1, fields.size()) * (className.getBounds().height + 2) + 10 + (int)className.getBounds().height
        		+ (height - calculateMinHeight()) / 2;
        g.drawLine((int)x, (int)y + (int)linemody, (int)x + (int)width, (int)y + (int)linemody);
        g.setColor(tempColor);
    }
    
    @Override
    public void gainFocus(UMLDiagram diagram) {
    	super.gainFocus(diagram);
    	className.isBackgroundRectVisible = true;
    	diagram.moveForward(className);
    	for (GuiString kme: fields) {
        	kme.isBackgroundRectVisible = true;
        	diagram.moveForward(kme);
    	}
    	for (GuiString kme: methods) {
        	kme.isBackgroundRectVisible = true;
        	diagram.moveForward(kme);
    	}
    }
    
    @Override
    public void loseFocus(UMLDiagram diagram) {
    	super.loseFocus(diagram);
       	className.isBackgroundRectVisible = false;
    	for (GuiString kme: fields) {
        	kme.isBackgroundRectVisible = false;
    	}
    	for (GuiString kme: methods) {
        	kme.isBackgroundRectVisible = false;
    	}
    }
    
    @Override
    public void delete() {
    	super.delete();
    	className.delete(false);
       	for (GuiString kme: fields) {
        	kme.delete(false);
    	}
    	for (GuiString kme: methods) {
        	kme.delete(false);
    	}
    }
    
    @Override
    public void calculatePointLocations() {
    	super.calculatePointLocations();
    	if (className != null) {
    		className.setX (x + (width - className.getBounds().width)/2);
    		className.setY (y + 3);
            double linemody = Math.max(1, fields.size()) * (className.getBounds().height + 2) + 10 + (int)className.getBounds().height
					+ (height - calculateMinHeight()) / 2;
            for (int i = 0; i < fields.size(); i++) {
            	fields.get(i).setX (x + 3);
            	fields.get(i).setY (y + className.getBounds().height + 6 + i*(className.getBounds().height+2) + 3); 
             	fields.get(i).setWidth (width - RIGHT_BLANK_SPACE_WIDTH);
            }
            for (int i = 0; i < methods.size(); i++) {
            	methods.get(i).setX (x + 3);
            	methods.get(i).setY (y + linemody + i*(className.getBounds().height+2) + 3); 
            	methods.get(i).setWidth (width - RIGHT_BLANK_SPACE_WIDTH);
            }            
    	}
    }
    
    @Override
    public void DoubleclickOn(Point2D.Double point) {
        double linemody = Math.max(1, fields.size()) * (className.getBounds().height + 2) + 10 + (int)className.getBounds().height
        		+ (height - calculateMinHeight()) / 2;
    	if (point.y > y + linemody)
    		addMethod ();
    	else if (point.y > y + className.getBounds().height + 6)
    		addField();
    	super.DoubleclickOn(point);
    }
    
    @Override
    public void deleteString(GuiString guiString) {
    	if (methods.contains(guiString)) {
    		methods.remove(guiString);
    		guiString.delete(false);
    		calculatePointLocations();
    		return;
    	}
    	else if (fields.contains(guiString)) {
    		fields.remove(guiString);
    		guiString.delete(false);
    		calculatePointLocations();
    		return;
    	}
    	else
    		delete();
    }
    
    @Override
    public void stringTextChanged(GuiString guiString) {
    	if (guiString != className && guiString.getText().equals(""))
    		guiString.delete();
    	super.stringTextChanged(guiString);
    }
    
    protected static final int RIGHT_BLANK_SPACE_WIDTH = 17;
}
