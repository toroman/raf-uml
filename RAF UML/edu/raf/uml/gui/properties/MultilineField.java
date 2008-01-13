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
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;

import edu.raf.uml.gui.ApplicationGui;

public class MultilineField extends PropertyField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4533960177632508965L;

	public MultilineField(PropertyPair propertyPair) {
		super(propertyPair);
		JButton btn = new JButton("...");
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		add(btn, gbc);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showMultilineEditor();
			}
		});
		// updateUI();
	}

	protected void showMultilineEditor() {
		final JDialog dialog = new JDialog(ApplicationGui.getInstance(),
				"Multiline String Editor", true);
		dialog.setLayout(new BorderLayout());
		final JTextArea text = new JTextArea(parent.getValue().toString());
		dialog.add(text, BorderLayout.CENTER);
		JButton ok = new JButton("OK");
		dialog.add(ok, BorderLayout.SOUTH);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					parent.setValue(text.getText());
				} catch (Exception ex) {
					log.log(Level.SEVERE, "Greska kod setovanja vrednosti", ex);
				}
				dialog.setVisible(false);
				dialog.dispose();
			}
		});
		dialog.pack();
		dialog.setVisible(true);
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.drawString(parent.getValue().toString(), 4, 14);
	}

	private static final Logger log = Logger.getLogger(MultilineField.class
			.getName());
}