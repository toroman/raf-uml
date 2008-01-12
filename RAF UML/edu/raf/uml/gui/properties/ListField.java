package edu.raf.uml.gui.properties;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import edu.raf.uml.gui.ApplicationGui;

public class ListField extends PropertyField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8784981716228472815L;
	private Class<?> type;
	private JPanel editori = null;
	private ArrayList<Object> args;

	public ListField(PropertyPair propertyPair) {
		super(propertyPair);
		JButton btn = new JButton("...");
		setLayout(new GridBagLayout());
		// btn.setPreferredSize(new Dimension(getHeight(), getHeight()));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		type = parent.property.type(); // tip Liste
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onShowEditor();
			}
		});
		add(btn, gbc);
	}

	@SuppressWarnings("unchecked")
	protected void onShowEditor() {
		args = new ArrayList<Object>((List) parent.getValue());
		final JDialog dlg = new JDialog(ApplicationGui.getInstance(), "List editor",
				true);
		dlg.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int w = dlg.getWidth();
				int h = dlg.getPreferredSize().height;
				dlg.setSize(w, h);
			}
		});
		Container c = dlg.getContentPane();
		c.setLayout(new BorderLayout());
		JPanel komande = new JPanel();
		komande.setLayout(new FlowLayout(FlowLayout.RIGHT));
		c.add(komande, BorderLayout.SOUTH);
		
		JButton cancel = new JButton("Odustani");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlg.setVisible(false);
				dlg.dispose();
			}			
		});
		komande.add(cancel);
		
		JButton add = new JButton("Dodaj");
		add.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				try {
					args.add(type.newInstance());
					refreshList(dlg);
				} catch (Exception ex) {
					log.log(Level.SEVERE, "Greska kod pravljenja instance", ex);
				}
			}
		});
		komande.add(add);
		
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					parent.setValue(args);
				} catch (Exception ex) {
					log.log(Level.SEVERE, "Nisam uspeo da setujem vrednost!", ex);
				} finally {
					dlg.setVisible(false);
					dlg.dispose();
				}				
			}			
		});
		komande.add(ok);
		
		komande.updateUI();
		editori = new JPanel();
		c.add(editori, BorderLayout.CENTER);
		refreshList(dlg);
		dlg.pack();
		dlg.setVisible(true);
		
	}

	@SuppressWarnings("unchecked")
	protected void refreshList(JDialog dlg) {
		editori.removeAll();
		editori.setLayout(new GridLayout(args.size(), 1));
		for (Object each : args) {
			PropertiesPanel prop = new PropertiesPanel();
			prop.remove(prop.propertiesLabel);
			prop.remove(prop.tooltipLabel);
			prop.setObject(each);
			editori.add(prop);
		}
		editori.updateUI();
		dlg.pack();
	}

	@Override
	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;
		List<?> list = (List<?>) parent.getValue();
		g.drawString("Count: " + list.size(), 2, 14);
	}

	private static final Logger log = Logger.getLogger(ListField.class
			.getName());
}
