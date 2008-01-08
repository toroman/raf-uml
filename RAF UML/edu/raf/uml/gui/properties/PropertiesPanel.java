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

package edu.raf.uml.gui.properties;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.raf.uml.model.property.Property;

/**
 * Prikazuje osobine nekog objekta.
 * 
 * @author Srecko Toroman
 * 
 */
public class PropertiesPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3544816945480445707L;
	private ArrayList<PropertyPair> properties;
	private Object object;
	private JPanel table;
	private JLabel tooltipLabel;

	public PropertiesPanel() {
		super();
		properties = new ArrayList<PropertyPair>();
		table = new JPanel();
		table.setFocusCycleRoot(true);
		table.setFocusable(true);
		table.setBackground(Color.WHITE);
		tooltipLabel = new JLabel("Tooltip");
		JLabel propertiesLabel = new JLabel("Properties:");

		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);

		this.add(propertiesLabel, BorderLayout.NORTH);
		this.add(table, BorderLayout.CENTER);
		this.add(tooltipLabel, BorderLayout.SOUTH);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 100);
	}

	/**
	 * Ovom metodom se saopstava panelu cije propertiese da prikaze.
	 * 
	 * @param activeObject
	 */
	public void setObject(Object activeObject) {
		this.object = activeObject;
		properties.clear();
		if (activeObject != null) {
			createProperties();
			System.out.println("PropertiesPanel: " + properties.size());
		} else {
			System.out.println("PropertiesPanel: deselected");
		}
		refreshTable();
	}

	private void refreshTable() {
		table.removeAll();
		if (properties.size() == 0) {
			table.validate();
			table.repaint();
			return;
		}
		table.setLayout(null);
		int x = 0;
		int y = 0;
		Dimension d = PropertyName.DIMENSION;
		for (PropertyPair pair : properties) {
			table.add(pair.namePanel);
			pair.namePanel.setBounds(x, y, d.width, d.height);
			table.add(pair.fieldPanel);
			pair.fieldPanel.setBounds(x + d.width, y, d.width, d.height);
			y += d.height;
		}
		table.validate();
		table.repaint();
	}

	private void createProperties() {
		properties = new ArrayList<PropertyPair>();
		Class<?> klass = object.getClass();
		for (Method mth : klass.getMethods()) {
			Annotation ann = mth.getAnnotation(Property.class);
			if (ann == null)
				continue;
			PropertyPair prop = new PropertyPair((Property) ann, mth, object);
			properties.add(prop);
		}
	}

}
