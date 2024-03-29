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
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import edu.raf.uml.gui.properties.PropertiesPanel;
import edu.raf.uml.gui.util.ColorHelper;
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLObject;
import edu.raf.uml.serialization.DiagramPanelConverter;
import edu.raf.uml.serialization.FontConverter;

@SuppressWarnings("serial")
public class ApplicationGui extends JFrame {
	public PropertiesPanel propertiesPanel;
	public JPanel mainPanel;
	public JToolBar toolBar;
	public JToggleButton toolDefault;
	public JToggleButton toolAddClass;
	public JToggleButton toolAddInterface;
	public JToggleButton toolAddInheritance;
	public JToggleButton toolDelete;
	public JToggleButton toolAddCommentBox;
	public JToggleButton toolAddCommentRelation;
	public JToggleButton toolAddAssociationRelation;
	public JToggleButton toolAddAggregationRelation;
	public JToggleButton toolAddCompositionRelation;
	public JToggleButton toolAddRealisationRelation;
	public JToggleButton toolAddAssociationClass;
	public JButton toolZoomIn;
	public JButton toolZoomOut;
	public ButtonGroup toolButtons;
	public JMenuBar mainMenu;
	public JMenu menuFile;
	public JMenu menuHelp;
	public JMenuItem menuItemNew;
	public JMenuItem menuItemOpen;
	public JMenuItem menuItemSave;
	public JMenuItem menuItemExport;
	public JMenuItem menuItemPrint;
	public JMenuItem menuItemExit;
	public JMenuItem menuItemHelpContents;
	public JMenuItem menuItemAbout;
	public JFileChooser fileChooserOpen = new JFileChooser();
	public JFileChooser fileChooserSave = new JFileChooser();
	private JSplitPane splitPane;
	private DiagramWindow activeWindow;
	private JDesktopPane desktopPane;

	private static ApplicationGui instance = null;

	/**
	 * Ovo nije singleton i ne treba da bude, vec je nacin da komponente iz
	 * unutrasnjosti dobiju referencu na aplikaciju.
	 * 
	 * @return
	 */
	public static ApplicationGui getInstance() {
		return instance;
	}

	public ApplicationGui(String[] args) {
		super("RAF-UML Editor");
		try {
			this.setIconImage(ImageIO.read(getClass().getClassLoader()
					.getResource("icon16x16.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		instance = this;
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
		if (args.length > 0) {
			try {
				if (args.length == 2 && "-open".equals(args[0]))
					args[0] = args[1]; // open by web start sometimes passes
										// "-open" :/
				File file = new File(args[0]);
				onOpenDocument(file);
			} catch (Exception ex) {
				log.log(Level.WARNING, "Couldn't open file: " + args[0], ex);
			}
		}
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
				onCreateNewDocument();
			}
		});
		menuItemExport = new JMenuItem("Export...");
		menuItemExport.setMnemonic('e');
		menuItemExport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onMenuItemExportClicked();
			}
		});
		menuItemOpen = new JMenuItem("Open...");
		menuItemOpen.setMnemonic('o');
		menuItemOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onOpenDocument(null);
			}
		});
		/*
		 * 
		 */
		menuItemSave = new JMenuItem("Save As...");
		menuItemSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onSaveDocument(null);
			}
		});
		menuItemSave.setMnemonic('s');
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
		menuItemHelpContents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new HelpFrame().setVisible(true);
			}
		});
		menuItemAbout = new JMenuItem("About");
		menuItemAbout.setMnemonic('a');
		menuItemAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane
						.showConfirmDialog(
								null,
								"UML editor by Ivan Bocić, Srećko Toroman and Saša Šijak",
								"About", JOptionPane.PLAIN_MESSAGE,
								JOptionPane.PLAIN_MESSAGE);
			}
		});

		menuFile.add(menuItemNew);
		menuFile.add(new JSeparator());
		menuFile.add(menuItemOpen);
		menuFile.add(menuItemSave);
		menuFile.add(menuItemExport);
		menuFile.add(new JSeparator());
		menuFile.add(menuItemExit);
		menuHelp.add(menuItemHelpContents);
		menuHelp.add(menuItemAbout);

		mainMenu.add(menuFile);
		mainMenu.add(menuHelp);
		this.setJMenuBar(mainMenu);

	}

	protected void onSaveDocument(File file) {
		if (file == null) {
			fileChooserSave.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(File f) {
					if (f.isDirectory()
							|| f.toString().toLowerCase().endsWith(".rml")) {
						return true;
					}
					return false;
				}

				@Override
				public String getDescription() {
					return "Raf UML Files (*.rml)";
				}
			});
			int returnVal = fileChooserSave.showSaveDialog(null);
			if (returnVal != JFileChooser.APPROVE_OPTION)
				return;
			file = fileChooserSave.getSelectedFile();
			if (!file.getName().toLowerCase().endsWith(".rml"))
				file = new File(file.getAbsolutePath() + ".rml");
		}

		XStream xstream = createXstreamStore();
		try {
			ZipOutputStream output = new ZipOutputStream(new FileOutputStream(
					file));
			output.putNextEntry(new ZipEntry("uml.xml"));
			xstream.toXML(getActiveWindow().panel.diagram, output);
			output.closeEntry();
			output.finish();
			output.close();
		} catch (IOException ioex) {
			log.log(Level.WARNING, "Document couldn't be saved.", ioex);
		}

	}

	protected void onOpenDocument(File file) {
		if (file == null) {
			fileChooserOpen.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooserOpen.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(File f) {
					if (f.isDirectory()
							|| f.toString().toLowerCase().endsWith(".rml")) {
						return true;
					}
					return false;
				}

				@Override
				public String getDescription() {
					return "Raf UML Files (*.rml)";
				}
			});
			int returnVal = fileChooserOpen.showOpenDialog(null);
			if (returnVal != JFileChooser.APPROVE_OPTION)
				return;
			file = fileChooserOpen.getSelectedFile();
		}

		try {
			DiagramWindow wnd = onCreateNewDocument();
			XStream xstream = createXstreamLoad(wnd.panel);
			ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
			while (!zis.getNextEntry().getName().equals("uml.xml"))
				; // just loop
			wnd.panel.diagram = (UMLDiagram) xstream.fromXML(zis);
			zis.close();
		} catch (IOException ioex) {
			log.log(Level.WARNING, "Document couldn't be read.", ioex);
		}
		mainPanel.repaint();
	}

	protected DiagramWindow onCreateNewDocument(UMLDiagram diagram) {
		DiagramPanel panel = createDiagramPanel();
		if (diagram == null)
			diagram = new UMLDiagram(panel);
		DiagramWindow wnd = new DiagramWindow(this, panel);
		this.setActiveWindow(wnd);
		wnd.setSize(600, 400);
		desktopPane.add(wnd);
		wnd.setVisible(true);
		return wnd;
	}

	protected DiagramWindow onCreateNewDocument() {
		return onCreateNewDocument(null);
	}

	private XStream createXstreamLoad(DiagramPanel diagramPanel) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new DiagramPanelConverter(diagramPanel));
		xstream.registerConverter(new FontConverter());
		return xstream;
	}

	private XStream createXstreamStore() {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new DiagramPanelConverter(
				getActiveWindow().panel));
		xstream.registerConverter(new FontConverter());
		return xstream;
	}

	protected void onMenuItemExportClicked() {
		fileChooserSave.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return true;
				if (!f.canWrite())
					return false;
				return f.toString().toLowerCase().endsWith(".png");
			}

			@Override
			public String getDescription() {
				return "PNG files";
			}
		});
		fileChooserSave.setName("untitled.png");
		int returnVal = fileChooserSave.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooserSave.getSelectedFile();
			if (!file.getName().toLowerCase().endsWith(".png")) {
				file = new File(file.getAbsolutePath() + ".png");
			}
			int sx = Integer.MAX_VALUE;
			int sy = Integer.MAX_VALUE;
			int ex = Integer.MIN_VALUE;
			int ey = Integer.MIN_VALUE;

			for (UMLObject obj : getActiveWindow().panel.diagram.objects) {
				Rectangle2D b = obj.getBounds();
				sx = Math.min(sx, (int) b.getX());
				sy = Math.min(sy, (int) b.getY());
				ex = Math.max(ex, (int) b.getMaxX());
				ey = Math.max(ey, (int) b.getMaxY());
			}

			int width = ex - sx + 40;
			int height = ey - sy + 40;
			sx -= 20;
			sy -= 20;
			if (getActiveWindow().panel.diagram.onFocus != null)
				getActiveWindow().panel.diagram.onFocus
						.loseFocus(getActiveWindow().panel.diagram);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) image.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);
			g.translate(-sx, -sy);
			getActiveWindow().panel.diagram.paint(g);
			try {
				ImageIO.write(image, "PNG", file);
			} catch (IOException e) {
				log.log(Level.SEVERE, "Greska pri snimanju slike!", e);
			}
		}
	}

	private void createPropertiesPanel() {
		propertiesPanel = new PropertiesPanel();
	}

	private void createMainPanel() {
		createToolbar();
		createSplitPane();
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(splitPane, BorderLayout.CENTER);
		mainPanel.add(toolBar, BorderLayout.PAGE_START);
	}

	/*
	 * TODO: ubaciti neki Docking frejmvork umesto ovog split-a
	 */
	private void createSplitPane() {
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setResizeWeight(1.0);
		splitPane.setDividerLocation(getWidth() - 200);
		desktopPane = new JDesktopPane();
		splitPane.add(desktopPane);
		splitPane.add(propertiesPanel);
	}

	private DiagramPanel createDiagramPanel() {
		DiagramPanel diagramPanel = new DiagramPanel(this);
		diagramPanel.setTool(DiagramPanel.DEFAULT_TOOL);
		return diagramPanel;
	}

	private void createToolbar() {
		toolBar = new JToolBar();
		toolButtons = new ButtonGroup();

		toolZoomIn = new JButton("", createImageIcon("ZoomInToolIcon.PNG"));
		toolZoomIn.setToolTipText("Zoom in");
		toolZoomIn.setFocusable(false);
		toolZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getActiveWindow() != null)
					getActiveWindow().panel.zoomIn();
			}
		});

		toolZoomOut = new JButton("", createImageIcon("ZoomOutToolIcon.PNG"));
		toolZoomOut.setFocusable(false);
		toolZoomOut.setToolTipText("Zoom out");
		toolZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getActiveWindow() != null)
					getActiveWindow().panel.zoomOut();
			}
		});

		toolDefault = new JToggleButton("", createImageIcon("SelectionToolIcon.PNG"));
		toolDefault.setToolTipText("Selection Tool");
		toolDefault.setFocusable(false);
		toolDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getActiveWindow().panel.setTool(DiagramPanel.DEFAULT_TOOL);
			}
		});

		toolAddClass = new JToggleButton("", createImageIcon("AddClassToolIcon.PNG"));
		toolAddClass.setToolTipText("Add new Class");
		toolAddClass.setFocusable(false);
		toolAddClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getActiveWindow() != null)
					getActiveWindow().panel
							.setTool(DiagramPanel.ADD_CLASS_TOOL);
			}
		});

		toolAddInheritance = new JToggleButton("",
				createImageIcon("AddInheritanceToolIcon.PNG"));
		toolAddInheritance.setToolTipText("Add new generalisation");
		toolAddInheritance.setFocusable(false);
		toolAddInheritance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getActiveWindow() != null)
					getActiveWindow().panel
							.setTool(DiagramPanel.ADD_INHERITANCE_TOOL);
			}
		});

		toolDelete = new JToggleButton("", createImageIcon("DeleteToolIcon.PNG"));
		toolDelete.setToolTipText("Delete object");
		toolDelete.setFocusable(false);
		toolDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getActiveWindow() != null)
					getActiveWindow().panel.setTool(DiagramPanel.DELETE_TOOL);
			}
		});

		toolAddCommentBox = new JToggleButton("",
				createImageIcon("AddCommentBoxToolIcon.PNG"));
		toolAddCommentBox.setToolTipText("Add comment box");
		toolAddCommentBox.setFocusable(false);
		toolAddCommentBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getActiveWindow() != null)
					getActiveWindow().panel
							.setTool(DiagramPanel.ADD_COMMENT_BOX_TOOL);
			}
		});

		toolAddCommentRelation = new JToggleButton("",
				createImageIcon("AddCommentRelationToolIcon.PNG"));
		toolAddCommentRelation.setToolTipText("Add comment link");
		toolAddCommentRelation.setFocusable(false);
		toolAddCommentRelation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getActiveWindow() != null)
					getActiveWindow().panel
							.setTool(DiagramPanel.ADD_COMMENT_RELATION_TOOL);
			}
		});

		toolAddAssociationRelation = new JToggleButton("",
				createImageIcon("AddAssociationToolIcon.PNG"));
		toolAddAssociationRelation.setToolTipText("Add new association");
		toolAddAssociationRelation.setFocusable(false);
		toolAddAssociationRelation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getActiveWindow() != null)
					getActiveWindow().panel
							.setTool(DiagramPanel.ADD_ASSOCIATION_TOOL);
			}
		});

		toolAddAggregationRelation = new JToggleButton("",
				createImageIcon("AddAggregationToolIcon.PNG"));
		toolAddAggregationRelation.setToolTipText("Add new aggregation");
		toolAddAggregationRelation.setFocusable(false);
		toolAddAggregationRelation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getActiveWindow() != null)
					getActiveWindow().panel
							.setTool(DiagramPanel.ADD_AGGREGATION_TOOL);
			}
		});

		toolAddCompositionRelation = new JToggleButton("",
				createImageIcon("AddCompositionToolIcon.PNG"));
		toolAddCompositionRelation.setToolTipText("Add new composition");
		toolAddCompositionRelation.setFocusable(false);
		toolAddCompositionRelation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getActiveWindow() != null)
					getActiveWindow().panel
							.setTool(DiagramPanel.ADD_COMPOSITION_TOOL);
			}
		});

		toolAddInterface = new JToggleButton("",
				createImageIcon("AddInterfaceToolIcon.PNG"));
		toolAddInterface.setToolTipText("Add new interface");
		toolAddInterface.setFocusable(false);
		toolAddInterface.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getActiveWindow() != null)
					getActiveWindow().panel
							.setTool(DiagramPanel.ADD_INTERFACE_TOOL);
			}
		});

		toolAddRealisationRelation = new JToggleButton("",
				createImageIcon("AddRealizationToolIcon.PNG"));
		toolAddRealisationRelation.setToolTipText("Add new realization");
		toolAddRealisationRelation.setFocusable(false);
		toolAddRealisationRelation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getActiveWindow() != null)
					getActiveWindow().panel
							.setTool(DiagramPanel.ADD_REALISATION_TOOL);
			}
		});

		toolAddAssociationClass = new JToggleButton("",
				createImageIcon("AddAssociationClassToolIcon.PNG"));
		toolAddAssociationClass.setToolTipText("Add new association class");
		toolAddAssociationClass.setFocusable(false);
		toolAddAssociationClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getActiveWindow() != null)
					getActiveWindow().panel
							.setTool(DiagramPanel.ADD_ASSOCIATION_CLASS_TOOL);
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
		toolBar.add(toolZoomIn);
		toolBar.add(toolZoomOut);
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
		toolButtons.add(toolZoomIn);
		toolButtons.add(toolZoomOut);
	}

	public ImageIcon createImageIcon(String path) {
		URL url = getClass().getClassLoader().getResource(path);
		Image image = Toolkit.getDefaultToolkit().getImage(url);
		return new ImageIcon(ColorHelper.makeColorTransparent(image, new Color(
				255, 0, 255)));
	}

	public void setActiveWindow(DiagramWindow window) {
		if (this.activeWindow != null)
			this.activeWindow.setActive(false);
		this.activeWindow = window;
		if (window != null)
			window.setActive(true);
	}

	public DiagramWindow getActiveWindow() {
		return activeWindow;
	}

	private static final Logger log = Logger.getLogger(ApplicationGui.class
			.getName());
}
