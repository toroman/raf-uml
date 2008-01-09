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
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import com.thoughtworks.xstream.XStream;

import edu.raf.uml.gui.properties.PropertiesPanel;
import edu.raf.uml.gui.util.ColorHelper;
import edu.raf.uml.model.UMLDiagram;

@SuppressWarnings("serial")
public class ApplicationGui extends JFrame {
	public PropertiesPanel propertiesPanel;
	public JPanel mainPanel;
	public JToolBar toolBar;
	public DiagramPanel diagramPanel;
	public JButton toolDefault;
	public JButton toolAddClass;
	public JButton toolAddInterface;
	public JButton toolAddInheritance;
	public JButton toolDelete;
	public JButton toolAddCommentBox;
	public JButton toolAddCommentRelation;
	public JButton toolAddAssociationRelation;
	public JButton toolAddAggregationRelation;
	public JButton toolAddCompositionRelation;
	public JButton toolAddRealisationRelation;
	public JButton toolAddAssociationClass;
	public JScrollPane mainScrollPane;
	public ArrayList<JButton> toolButtons;
	public JMenuBar mainMenu;
	public JMenu menuFile;
	public JMenu menuHelp;
	public JMenuItem menuItemNew;
	public JMenuItem menuItemOpen;
	public JMenuItem menuItemSave;
	public JMenuItem menuItemPrint;
	public JMenuItem menuItemExit;
	public JMenuItem menuItemHelpContents;
	public JMenuItem menuItemAbout;
	public JFileChooser fileChooserOpen = new JFileChooser();
	public JFileChooser fileChooserSave = new JFileChooser();

	public ApplicationGui() {
		super("RAF-UML Editor");
		setPreferredSize(new Dimension(800, 600));
		setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - this
				.getPreferredSize().width) / 2, (Toolkit.getDefaultToolkit()
				.getScreenSize().height - this.getPreferredSize().height) / 2,
				this.getPreferredSize().width, this.getPreferredSize().height);
		createPropertiesPanel();
		createMainPanel();
		createMenu();
		setContentPane(mainPanel);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack();
	}

	private void createMenu() {
		mainMenu = new JMenuBar();

		menuFile = new JMenu("File");
		menuFile.setMnemonic('f');
		menuHelp = new JMenu("Help");
		menuHelp.setMnemonic('h');

		menuItemNew = new JMenuItem("New");
		menuItemNew.setMnemonic('n');
		menuItemNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				diagramPanel.diagram = new UMLDiagram(diagramPanel);
				mainPanel.repaint();
			}
		});
		menuItemOpen = new JMenuItem("Open");
		menuItemOpen.setMnemonic('o');
		menuItemOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooserOpen.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooserOpen.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						if (f.isDirectory() || f.toString().toLowerCase().endsWith(".xml")) {
							return true;
						}
						return false;
					}

					@Override
					public String getDescription() {
						return "XML Files";
					}
				});
				XStream xstream = new XStream();
				int returnVal = fileChooserOpen.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooserOpen.getSelectedFile();
					FileInputStream fis;
					try {
						fis = new FileInputStream(file);
						FileChannel channel = fis.getChannel();
						ByteBuffer buf = ByteBuffer.allocateDirect(10000000);
						buf.clear();
						while (channel.read(buf) != -1)
							buf.flip();
						CharsetDecoder decoder = Charset.forName("UTF-8")
								.newDecoder();
						CharBuffer cb = decoder.decode(buf);
						UMLDiagram openedDiagram = (UMLDiagram) xstream
								.fromXML(cb.toString());
						diagramPanel.diagram = openedDiagram;
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					mainPanel.repaint();
				}
			}
		});
		menuItemSave = new JMenuItem("Save");
		menuItemSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooserSave.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						if (f.isDirectory() || f.toString().toLowerCase().endsWith(".xml")) {
							return true;
						}
						return false;
					}

					@Override
					public String getDescription() {
						return "XML Files";
					}
				});
				int returnVal = fileChooserSave.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooserSave.getSelectedFile();
					XStream xstream = new XStream();
					String xml = xstream.toXML(diagramPanel.diagram);
					try {
						FileOutputStream out = new FileOutputStream(file);
						PrintStream p = new PrintStream(out);
						p.print(xml);
						p.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		menuItemSave.setMnemonic('s');
		menuItemPrint = new JMenuItem("Print");
		menuItemPrint.setMnemonic('p');
		menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuItemExit.setMnemonic('e');
		menuItemHelpContents = new JMenuItem("Help Contents");
		menuItemHelpContents.setMnemonic('h');
		menuItemAbout = new JMenuItem("About");
		menuItemAbout.setMnemonic('a');

		menuFile.add(menuItemNew);
		menuFile.add(new JSeparator());
		menuFile.add(menuItemOpen);
		menuFile.add(menuItemSave);
		menuFile.add(menuItemPrint);
		menuFile.add(new JSeparator());
		menuFile.add(menuItemExit);
		menuHelp.add(menuItemHelpContents);
		menuHelp.add(menuItemAbout);

		mainMenu.add(menuFile);
		mainMenu.add(menuHelp);
		this.setJMenuBar(mainMenu);
		
	}

	private void createPropertiesPanel() {
		propertiesPanel = new PropertiesPanel();
	}

	private void createMainPanel() {
		createToolbar();
		createDiagramPanel();
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(mainScrollPane, BorderLayout.CENTER);
		mainPanel.add(toolBar, BorderLayout.PAGE_START);
		mainPanel.add(propertiesPanel, BorderLayout.EAST);
	}

	private void createDiagramPanel() {
		diagramPanel = new DiagramPanel(this);
		diagramPanel.setTool(DiagramPanel.DEFAULT_TOOL);
		mainScrollPane = new JScrollPane(diagramPanel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		mainScrollPane.setWheelScrollingEnabled(false);
		mainScrollPane.getHorizontalScrollBar().setUnitIncrement(40);
		mainScrollPane.getVerticalScrollBar().setUnitIncrement(40);
		mainScrollPane.getViewport().setViewPosition(
				new Point(UMLDiagram.MAX_DIMENSION.width / 2
						- this.getPreferredSize().width / 2,
						UMLDiagram.MAX_DIMENSION.height / 2
								- this.getPreferredSize().height / 2));
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
		toolAddInheritance.setToolTipText("Add new generalisation");
		toolAddInheritance.setFocusable(false);
		toolAddInheritance.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				diagramPanel.setTool(DiagramPanel.ADD_INHERITANCE_TOOL);
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

		toolAddCommentBox = new JButton("",
				createImageIcon("AddCommentBoxToolIcon.PNG"));
		toolAddCommentBox.setToolTipText("Add comment box");
		toolAddCommentBox.setFocusable(false);
		toolAddCommentBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				diagramPanel.setTool(DiagramPanel.ADD_COMMENT_BOX_TOOL);
			}
		});

		toolAddCommentRelation = new JButton("",
				createImageIcon("AddCommentRelationToolIcon.PNG"));
		toolAddCommentRelation.setToolTipText("Add comment link");
		toolAddCommentRelation.setFocusable(false);
		toolAddCommentRelation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				diagramPanel.setTool(DiagramPanel.ADD_COMMENT_RELATION_TOOL);
			}
		});

		toolAddAssociationRelation = new JButton("",
				createImageIcon("AddAssociationToolIcon.PNG"));
		toolAddAssociationRelation.setToolTipText("Add new association");
		toolAddAssociationRelation.setFocusable(false);
		toolAddAssociationRelation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				diagramPanel.setTool(DiagramPanel.ADD_ASSOCIATION_TOOL);
			}
		});

		toolAddAggregationRelation = new JButton("",
				createImageIcon("AddAggregationToolIcon.PNG"));
		toolAddAggregationRelation.setToolTipText("Add new aggregation");
		toolAddAggregationRelation.setFocusable(false);
		toolAddAggregationRelation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				diagramPanel.setTool(DiagramPanel.ADD_AGGREGATION_TOOL);
			}
		});

		toolAddCompositionRelation = new JButton("",
				createImageIcon("AddCompositionToolIcon.PNG"));
		toolAddCompositionRelation.setToolTipText("Add new composition");
		toolAddCompositionRelation.setFocusable(false);
		toolAddCompositionRelation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				diagramPanel.setTool(DiagramPanel.ADD_COMPOSITION_TOOL);
			}
		});

		toolAddInterface = new JButton("",
				createImageIcon("AddInterfaceToolIcon.PNG"));
		toolAddInterface.setToolTipText("Add new interface");
		toolAddInterface.setFocusable(false);
		toolAddInterface.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				diagramPanel.setTool(DiagramPanel.ADD_INTERFACE_TOOL);
			}
		});

		toolAddRealisationRelation = new JButton("",
				createImageIcon("AddRealizationToolIcon.PNG"));
		toolAddRealisationRelation.setToolTipText("Add new realization");
		toolAddRealisationRelation.setFocusable(false);
		toolAddRealisationRelation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				diagramPanel.setTool(DiagramPanel.ADD_REALISATION_TOOL);
			}
		});

		toolAddAssociationClass = new JButton("",
				createImageIcon("AddAssociationClassToolIcon.PNG"));
		toolAddAssociationClass.setToolTipText("Add new association class");
		toolAddAssociationClass.setFocusable(false);
		toolAddAssociationClass.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				diagramPanel.setTool(DiagramPanel.ADD_ASSOCIATION_CLASS_TOOL);
			}
		});

		toolBar.setFloatable(false);
		toolBar.add(toolDefault);
		toolBar.addSeparator();
		toolBar.add(toolAddClass);
		toolBar.add(toolAddInheritance);
		toolBar.addSeparator();
		toolBar.add(toolAddInterface);
		toolBar.add(toolAddRealisationRelation);
		toolBar.addSeparator();
		toolBar.add(toolAddAssociationRelation);
		toolBar.add(toolAddAggregationRelation);
		toolBar.add(toolAddCompositionRelation);
		toolBar.addSeparator();
		toolBar.add(toolAddAssociationClass);
		toolBar.addSeparator();
		toolBar.add(toolDelete);
		toolBar.addSeparator();
		toolBar.add(toolAddCommentBox);
		toolBar.add(toolAddCommentRelation);
		toolButtons.add(toolDefault);
		toolButtons.add(toolAddClass);
		toolButtons.add(toolAddInheritance);
		toolButtons.add(toolDelete);
		toolButtons.add(toolAddCommentBox);
		toolButtons.add(toolAddCommentRelation);
		toolButtons.add(toolAddAssociationRelation);
		toolButtons.add(toolAddAggregationRelation);
		toolButtons.add(toolAddCompositionRelation);
		toolButtons.add(toolAddInterface);
		toolButtons.add(toolAddRealisationRelation);
		toolButtons.add(toolAddAssociationClass);
	}

	public ImageIcon createImageIcon(String path) {
		URL url = getClass().getClassLoader().getResource("res/" + path);
		Image image = Toolkit.getDefaultToolkit().getImage(url);
		return new ImageIcon(ColorHelper.makeColorTransparent(image, new Color(
				255, 0, 255)));
	}
}
