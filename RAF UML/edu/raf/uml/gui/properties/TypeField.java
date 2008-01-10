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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;

import edu.raf.uml.model.property.TypeModel;

public class TypeField extends PropertyField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2679461150310004049L;

	private JComboBox combo = null;

	public TypeField(PropertyPair propertyPair) {
		super(propertyPair);
		setLayout(new BorderLayout());
		TypeModel type = (TypeModel) parent.getValue();
		String[] types = type.getTypes();
		combo = new JComboBox(types);
		combo.setEditable(true);
		combo.setSelectedItem(type.toString());
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object item = combo.getSelectedItem();
				setValue(item);
			}
		});
		add(combo, BorderLayout.CENTER);
	}

	protected void setValue(Object choice) {
		try {
			TypeModel model = new TypeModel();
			model.setType(choice.toString());
			parent.setValue(model);
		} catch (Exception ex) {
			// TODO: prikazi gresku u nekom prozoru
			log.log(Level.SEVERE, "Nisam uspeo da setujem tip!", ex);
		}
	}

	private static final Logger log = Logger.getLogger(TypeField.class
			.getName());
}
