package edu.raf.uml.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;

import edu.raf.uml.model.property.Property;
import edu.raf.uml.model.property.StringProperty;

public class UMLCommentBox extends UMLBox {

	public ArrayList<String> text;
	private transient Polygon shapePolygon = null;
	private transient Polygon edgeTriangle = null;
	private transient String textStringCached = null;

	public UMLCommentBox(UMLDiagram diagram, int x, int y) {
		super(diagram, x, y, 0, 0);
		text = new ArrayList<String>();
		text.add("Komentar...");
		this.movePoint(sePoint, -1, -1);
	}

	@Override
	public double calculateMinHeight() {
		return Math.max(text.size(), 1) * 16 + 18;
	}

	@Override
	public double calculateMinWidth() {
		Graphics2D g = (Graphics2D) diagram.panel.getGraphics();
		FontMetrics metrics = g.getFontMetrics(Font.decode("Monospaced"));
		double minw = -1;
		for (String str : text) {
			double strWidth = metrics.stringWidth(str);
			if (strWidth > minw)
				minw = strWidth;
		}
		return Math.max(minw - 15, 80);
	}

	@Override
	public void paint(Graphics2D g) {
		Color tempColor = g.getColor();
		g.setColor(getBackgroundColor());
		g.fillPolygon(shapePolygon);
		g.setColor(Color.DARK_GRAY);
		g.drawPolygon(shapePolygon);
		g.setColor(Color.GRAY);
		g.fillPolygon(edgeTriangle);
		g.setColor(Color.DARK_GRAY);
		g.drawPolygon(edgeTriangle);
		g.setColor(Color.BLACK);
		
		for (int i = 0; i < text.size(); i++)
			g.drawString(text.get(i), (int) x + 3, (int) y + 28 + i * 16);
		g.setColor(tempColor);
	}

	@Override
	public void calculatePointLocations() {
		if (shapePolygon == null) {
			shapePolygon = new Polygon(new int[5], new int[5], 5);
			edgeTriangle = new Polygon(new int[3], new int[3], 3);
		}
		super.calculatePointLocations();
		shapePolygon.xpoints[0] = (int) x;
		shapePolygon.xpoints[1] = (int) (x + width - EDGE_TRIM_SIZE);
		shapePolygon.xpoints[2] = (int) (x + width);
		shapePolygon.xpoints[3] = (int) (x + width);
		shapePolygon.xpoints[4] = (int) x;

		shapePolygon.ypoints[0] = (int) y;
		shapePolygon.ypoints[1] = (int) y;
		shapePolygon.ypoints[2] = (int) (y + EDGE_TRIM_SIZE);
		shapePolygon.ypoints[3] = (int) (y + height);
		shapePolygon.ypoints[4] = (int) (y + height);

		edgeTriangle.xpoints[0] = (int) (x + width - EDGE_TRIM_SIZE);
		edgeTriangle.xpoints[1] = (int) (x + width - EDGE_TRIM_SIZE);
		edgeTriangle.xpoints[2] = (int) (x + width);

		edgeTriangle.ypoints[0] = (int) y;
		edgeTriangle.ypoints[1] = (int) (y + EDGE_TRIM_SIZE);
		edgeTriangle.ypoints[2] = (int) (y + EDGE_TRIM_SIZE);
	}

	public void setText(String newText) {
		textStringCached = null; // invalidate cache
		String[] array = newText.split("\\n");
		text = new ArrayList<String>();
		for (String str : array)
			text.add(str);
		calculatePointLocations();
		diagram.panel.repaint();
	}

	@Property
	@StringProperty(multiline = true)
	public String getText() {
		if (textStringCached == null) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < text.size() - 1; i++)
				sb.append(text.get(i)).append('\n');
			if (text.size() > 0)
				sb.append(text.get(text.size() - 1));
			textStringCached = sb.toString();
			;
		}
		return textStringCached;
	}

	/**
	 * Velichina trougle u tjoshku, merena u pixelima.
	 */
	private static final int EDGE_TRIM_SIZE = 14;
}
