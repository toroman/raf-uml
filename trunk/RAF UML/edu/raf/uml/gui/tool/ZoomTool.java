package edu.raf.uml.gui.tool;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import edu.raf.uml.gui.DiagramPanel;

/**
 * Ovo bash i nije tool ali ne znam gde da ga stavim, yet.
 * @author Srecko Toroman
 *
 */
public class ZoomTool {
	DiagramPanel panel;
	public ZoomTool(DiagramPanel panel) {
		this.panel = panel;
	}
	
	public static void zoomIn(Graphics2D g) {
		AffineTransform tf = g.getTransform();
		tf.setToScale(1.2, 1.2);
		g.setTransform(tf);
	}
	
	public static void zoomOut(Graphics2D g) {
		AffineTransform tf = g.getTransform();
		tf.setToScale(5.0/6.0, 5.0/6.0);
		g.setTransform(tf);
	}
}
