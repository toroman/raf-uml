package edu.raf.uml.gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public class DiagramWindow extends JInternalFrame implements InternalFrameListener {
    /**
     * 
     */
    private static final long serialVersionUID = 7238937672104174627L;
    private JScrollPane scrollPane;
    public final DiagramPanel panel;
    private ApplicationGui gui;
    
    public DiagramWindow(ApplicationGui gui, DiagramPanel panelToWrap) {
        super("Class Diagram", true, true, true, true);
        this.panel = panelToWrap;
        this.gui = gui;
        scrollPane = new JScrollPane(panelToWrap, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setWheelScrollingEnabled(false);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(40);
        scrollPane.getVerticalScrollBar().setUnitIncrement(40);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        panelToWrap.setWindow(this);
        this.addInternalFrameListener(this);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public boolean isActive() {
        return panel.isActive();
    }

    public void setActive(boolean b) {
        panel.setActive(b);
    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
        gui.setActiveWindow(this);
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
        
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        gui.setActiveWindow(null);
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
        
    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {
                
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {
        gui.setActiveWindow(null);
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
        gui.setActiveWindow(this);
    }
        
}
