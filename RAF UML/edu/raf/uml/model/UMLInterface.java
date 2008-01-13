package edu.raf.uml.model;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import edu.raf.uml.gui.util.GuiString;
import edu.raf.uml.model.property.Property;

public class UMLInterface extends UMLBox {
    public GuiString interfaceName;
    public ArrayList<UMLMethod> methods;
    public static FontMetrics fontMetrics;

    public UMLInterface(UMLDiagram diagram, double x, double y) {
        super(diagram, x, y, 0, 0);
        methods = new ArrayList<UMLMethod>();
        interfaceName = new GuiString (diagram, this);
        interfaceName.setText("New Interface");
        interfaceName.setVisible(true);
        this.movePoint(sePoint, -1, -1);
    }
    
    public void addMethod () {
    	UMLMethod method = new UMLMethod(diagram, this);
    	methods.add(method);
    	method.setVisible(true);
    	method.setName("method");
    	calculatePointLocations();
    	diagram.giveFocus(this);
    }

    @Override
    public double calculateMinHeight() {
        if (interfaceName != null)
        	return (interfaceName.getBounds().height + 2) * (1 + Math.max(methods.size(), 1)) + 24;
        else
        	return 10;
    }

    @Override
    public double calculateMinWidth() {
    	double minw;
        if (interfaceName != null)
        	minw = interfaceName.calculateMinWidth();
        else
        	minw = 0;
    	for (GuiString kme: methods)
    		if (kme.getBounds().width > minw)
    			minw = kme.calculateMinWidth();
        return (minw < 80 ? 80 : minw) + RIGHT_BLANK_SPACE_WIDTH;
    }

    @Override
    public void paint(Graphics2D g) {
        super.paint(g);
        Color tempColor = g.getColor();
        g.setColor(Color.BLACK);
        g.drawString("<< interface >>", (int)(x + width/2) - 40, (int)y + 15);
        g.drawLine((int)x, (int)y + (int)interfaceName.getBounds().height + 22, (int)x + (int)width, (int)y + (int)interfaceName.getBounds().height + 22);
        g.setColor(tempColor);
    }
    
    @Override
    public void gainFocus(UMLDiagram diagram) {
    	super.gainFocus(diagram);
    	interfaceName.isBackgroundRectVisible = true;
    	diagram.moveForward(interfaceName);
    	for (GuiString kme: methods) {
        	kme.isBackgroundRectVisible = true;
        	diagram.moveForward(kme);
    	}
    }
    
    @Override
    public void loseFocus(UMLDiagram diagram) {
    	super.loseFocus(diagram);
       	interfaceName.isBackgroundRectVisible = false;
    	for (GuiString kme: methods) {
        	kme.isBackgroundRectVisible = false;
    	}
    }
    
    @Override
    public void delete() {
    	super.delete();
    	interfaceName.delete(false);
    	for (GuiString kme: methods) {
        	kme.delete(false);
    	}
    }
    
    @Override
    public void calculatePointLocations() {
		//TODO djoksi
    	super.calculatePointLocations();
    	if (interfaceName != null) {
    		interfaceName.setX (x + (width - interfaceName.getBounds().width)/2);
    		interfaceName.setY (y + 18);
            for (int i = 0; i < methods.size(); i++) {
            	methods.get(i).setX (x + 3);
            	methods.get(i).setY (y + 44 + i*(interfaceName.getBounds().height+2) + 3); 
            	methods.get(i).setWidth (width - RIGHT_BLANK_SPACE_WIDTH);
            }            
    	}
    }
    
    @Override
    public void dblClickOn(Point2D.Double point) {
    	if (point.y > y + interfaceName.getBounds().height + 22)
    		addMethod ();
    	super.dblClickOn(point);
    }
    
    @Override
    public void deleteString(GuiString guiString) {
    	if (methods.contains(guiString)) {
    		methods.remove(guiString);
    		guiString.delete(false);
    		calculatePointLocations();
    		return;
    	}
    	else
    		delete();
    }
    
    @Override
    public void stringTextChanged(GuiString guiString) {
    	if (guiString != interfaceName && guiString.getText().equals(""))
    		guiString.delete();
    	super.stringTextChanged(guiString);
    }
    
    @Property
    public String getName() {
    	return this.interfaceName.getText();
	}
    
    public void setName(String name) {
    	if (!name.matches("[A-z0-9_$]+"))
    		throw new IllegalArgumentException("Ime klase mora biti od slova, brojeva i _$");
    	this.interfaceName.setText(name);
    	diagram.panel.repaint();
	}
    
    protected static final int RIGHT_BLANK_SPACE_WIDTH = 17;

}
