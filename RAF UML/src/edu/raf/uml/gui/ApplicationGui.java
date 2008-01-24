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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import edu.raf.uml.gui.properties.PropertiesPanel;
import edu.raf.uml.gui.util.ColorHelper;
import edu.raf.uml.model.UMLDiagram;
import edu.raf.uml.model.UMLObject;
import edu.raf.uml.serialization.FontConverter;

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
	public JButton toolZoomIn;
	public JButton toolZoomOut;
	public JScrollPane mainScrollPane;
	public ArrayList<JButton> toolButtons;
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

	public ApplicationGui() {
		super("RAF-UML Editor");
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
		menuItemExport = new JMenuItem("Export to PNG");
		menuItemExport.setMnemonic('e');
		menuItemExport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onMenuItemExportClicked();
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
						if (f.isDirectory()
								|| f.toString().toLowerCase().endsWith(".xml")) {
							return true;
						}
						return false;
					}

					@Override
					public String getDescription() {
						return "XML Files";
					}
				});
				XStream xstream = createXstream();
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
						if (f.isDirectory()
								|| f.toString().toLowerCase().endsWith(".xml")) {
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
					if (!file.getName().toLowerCase().endsWith(".xml"))
						file = new File(file.getAbsolutePath() + ".xml");
					XStream xstream = createXstream();
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
		menuItemHelpContents.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showConfirmDialog(null,
						"<html><h1>Meni</h1><hr />" +
						"<p><strong><em>New </em></strong>- Uklanja trenutni dijagram kako bi korisnik mogao da pravi novi.</p>"+
						"<p><strong><em>Open/Save </em></strong>- Ove opcije služe za snimanje i učitavanje trenutnog dijagrama na tvrdi disk. <br />Snimaju se u formatu prepoznatljivom za program, kako bi ih mogao lako učitati.</p>"+
						"<p><em><strong>Export to PNG</strong></em> - Snima ceo dijagram na izabranu lokaciju na tvrdom disku u sliku formata png.</p>"+
						"<p><em><strong>Exit</strong></em> - Izlazi iz programa.</p>"+
						"<p><strong><em>Help contents</em></strong> - Prikazuje pomoć za korišćenje programa.</p>"+
						"<p><strong><em>About</em></strong> - Prikazuje informacije o autorima programa.</p>"+
						"<h1>Kutija sa alatkama</h1><hr />"+
						"<p>Kutija sa alatkama je podeljena na nekoliko delova, tako da su alatke grupisane po svrsi.</p>"+
						"<p><strong><em>Selection tool</em></strong> - Služi za selektovanje svih elemenata na dijagramu.</p>"+
						"<p><strong><em>Delete object</em></strong> - Služi za brisanje objekata sa dijagrama.</p>"+
						"<p><strong><em>Add new class</em></strong> - Dodaje novu klasu na dijagram.</p>"+
						"<p><strong><em>Add new interface</em></strong> - Dodaje novi interfejs na dijagram</p>"+
						"<p><strong><em>Add comment box</em></strong> - Dodaje novu kutiju sa komentarom na dijagram.</p>"+
						"<p>Za prikazivanje odnosa između klasa i interfejsa i komentara na dijagramu imamo više alatki <br /><strong><em>Add new generalization</em></strong>, <strong><em>realization</em></strong>, <strong><em>agregation</em></strong>, <strong><em>association</em></strong>, <strong><em>composition</em></strong> i <strong><em>comment link</em></strong>.</p>"+
						"<p><strong><em>Zoom in</em></strong> - Zumira dijagram.</p>"+
						"<p><strong><em>Zoom out</em></strong> - Od zumira dijagram.</p>"+
						"<h1>Properties panel</h1><hr />"+
						"<p>Svaki objekat na dijagramu ima svoje osobine. Da bi prikazali te osobine napravili smo <br />properties panel koji se nalazi u desnom delu programa. Svaki put kada selektujemo neki <br />objekat njegove osobine se prikažu u tom panelu. U zavisnosti od selektovanog objekta <br />prikazaće se različite osobine koje možemo podešavati. Na primer, ako selektujemo klasu, <br />nećemo videti podešavanje za tip, koje vidimo ako selektujemo metod klase. Dodavanje <br />atributa i metoda klasi se vrši duplim klikom miša u odgovarajući deo grafičkog prikaza klase.</p>"+
						"<p>&nbsp;</p>", "Help contents", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
			}
		});
		menuItemAbout = new JMenuItem("About");
		menuItemAbout.setMnemonic('a');
		menuItemAbout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showConfirmDialog(null, "UML editor by Ivan Bocić, Srećko Toroman and Saša Šijak", "About", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE);
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

	private XStream createXstream() {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(diagramPanel.getConverter());
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

			for (UMLObject obj : diagramPanel.diagram.objects) {
				// if (obj instanceof GuiPoint) {
				// GuiPoint p = (GuiPoint) obj;
				// sx = Math.min(sx, (int) p.getX());
				// sy = Math.min(sy, (int) p.getY());
				// ex = Math.max(ex, (int) p.getX());
				// ey = Math.max(ey, (int) p.getY());
				// }
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
			if (diagramPanel.diagram.onFocus != null)
				diagramPanel.diagram.onFocus.loseFocus(diagramPanel.diagram);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) image.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);
			g.translate(-sx, -sy);
			diagramPanel.diagram.paint(g);
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
		createDiagramPanel();
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
		splitPane.add(mainScrollPane);
		splitPane.add(propertiesPanel);
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
		// mainScrollPane.getViewport().setViewPosition(
		// new Point(UMLDiagram.MAX_DIMENSION.width / 2
		// - this.getPreferredSize().width / 2,
		// UMLDiagram.MAX_DIMENSION.height / 2
		// - this.getPreferredSize().height / 2));

	}

	private void createToolbar() {
		toolBar = new JToolBar();
		toolButtons = new ArrayList<JButton>();

		toolZoomIn = new JButton("", createImageIcon("ZoomInToolIcon.PNG"));
		toolZoomIn.setToolTipText("Zoom in");
		toolZoomIn.setFocusable(false);
		toolZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diagramPanel.zoomIn();
			}
		});

		toolZoomOut = new JButton("", createImageIcon("ZoomOutToolIcon.PNG"));
		toolZoomOut.setFocusable(false);
		toolZoomOut.setToolTipText("Zoom out");
		toolZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diagramPanel.zoomOut();
			}
		});

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

	private static final Logger log = Logger.getLogger(ApplicationGui.class
			.getName());
}
