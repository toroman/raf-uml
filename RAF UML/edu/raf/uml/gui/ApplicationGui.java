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
package edu.raf.uml.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import edu.raf.uml.gui.util.ColorHelper;

@SuppressWarnings("serial")
public class ApplicationGui extends JFrame {

	public JPanel mainPanel;
	public JToolBar toolBar;
	public DiagramPanel diagramPanel;
	public JButton toolDefault;
	public JButton toolAddClass;
	public JButton toolAddInheritance;
	public JButton toolDelete;
	public ArrayList<JButton> toolButtons;

	public ApplicationGui() {
		super("UML Frame");
		createMainPanel();
		setContentPane(mainPanel);
		setPreferredSize(new Dimension(640, 480));
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}

	private void createMainPanel() {
		diagramPanel = new DiagramPanel(this);
		createToolbar();
		diagramPanel.setTool(DiagramPanel.DEFAULT_TOOL);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(diagramPanel, BorderLayout.CENTER);
		mainPanel.add(toolBar, BorderLayout.PAGE_START);
	}

	private void createToolbar() {
		toolBar = new JToolBar();
		toolButtons = new ArrayList<JButton>();
		toolDefault = new JButton("", createImageIcon("SelectionToolIcon.PNG"));
		toolDefault.setToolTipText("Selection Tool");
		toolDefault.setFocusable(false);
		toolDefault.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				diagramPanel.setTool(DiagramPanel.DEFAULT_TOOL);
			}
		});

		toolAddClass = new JButton("", createImageIcon("AddClassToolIcon.PNG"));
		toolAddClass.setToolTipText("Add new Class");
		toolAddClass.setFocusable(false);
		toolAddClass.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				diagramPanel.setTool(DiagramPanel.ADD_CLASS_TOOL);
			}
		});

		toolAddInheritance = new JButton("",
				createImageIcon("AddInheritanceToolIcon.PNG"));
		toolAddInheritance.setToolTipText("Add new Inheritance relationship");
		toolAddInheritance.setFocusable(false);
		toolAddInheritance.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				diagramPanel.setTool(DiagramPanel.ADD_RELATION_TOOL);
			}
		});

		toolDelete = new JButton("", createImageIcon("DeleteToolIcon.PNG"));
		toolDelete.setToolTipText("Delete object");
		toolDelete.setFocusable(false);
		toolDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				diagramPanel.setTool(DiagramPanel.DELETE_TOOL);
			}
		});

		toolBar.setFloatable(false);
		toolBar.add(toolDefault);
		toolBar.addSeparator();
		toolBar.add(toolAddClass);
		toolBar.addSeparator();
		toolBar.add(toolAddInheritance);
		toolBar.addSeparator();
		toolBar.add(toolDelete);
		toolButtons.add(toolDefault);
		toolButtons.add(toolAddClass);
		toolButtons.add(toolAddInheritance);
		toolButtons.add(toolDelete);
	}

	public ImageIcon createImageIcon(String path) {
		URL url = getClass().getClassLoader().getResource("res/" + path);
		Image image = Toolkit.getDefaultToolkit().getImage(url);
		return new ImageIcon(ColorHelper.makeColorTransparent(image, new Color(
				255, 0, 255)));
	}
}
