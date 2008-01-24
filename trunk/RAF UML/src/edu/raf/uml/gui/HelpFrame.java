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
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * Na divno zaprepastenje ova klasa prikazuje HEEEELL. P.
 * @author Srecko Toroman
 *
 */
public class HelpFrame extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = -5821719758989897082L;
    private JEditorPane helpPane;
    private JScrollPane scrollPane;
    
    public HelpFrame() {
        super("Help");
        initComponents();
        setSize(600, 350);
    }
    
    private void initComponents() {
        try {
            helpPane = new JEditorPane(getClass().getClassLoader().getResource("help.html"));
        }
        catch (IOException e) {
            helpPane = new JEditorPane("text/html", "<b>Error: help.html couldn't be found</b>");
        }
        helpPane.setEditable(false);
        helpPane.setOpaque(false);
        scrollPane = new JScrollPane(helpPane);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
    
    
}
