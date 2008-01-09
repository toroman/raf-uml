package edu.raf.uml.gui.properties;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;

public class EnumField extends PropertyField {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8001064617234376832L;

	private JComboBox combo = null;

	public EnumField(PropertyPair propertyPair) {
		super(propertyPair);
		setLayout(new BorderLayout());
		Class<?> type = parent.getValue().getClass();
		Object[] values;
		try {
			values = (Object[]) type.getMethod("values").invoke(null);
			Arrays.sort(values);
		} catch (Exception ex) {
			log.log(Level.SEVERE, "Cudna vrsta Enum tipa!", ex);
			values = new String[] { "" };
		}

		combo = new JComboBox(values);
		combo.setEditable(false);
		combo.setSelectedItem(parent.getValue());
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
			parent.setValue(choice);
		} catch (Exception ex) {
			// TODO: prikazi gresku u nekom prozoru
			System.out.println(ex.getMessage());
		}
	}

	private static final Logger log = Logger.getLogger(EnumField.class
			.getName());
}
