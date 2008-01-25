/*
 * RAF UML - Student project for Object oriented programming and design Copyright (C) <2007> Ivan
 * Bocic, Sasa Sijak, Srecko Toroman
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */
package edu.raf.uml.serialization;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import edu.raf.uml.gui.DiagramPanel;

public class DiagramPanelConverter implements Converter {
    private DiagramPanel who;

    public DiagramPanelConverter(DiagramPanel instance) {
        this.who = instance;
    }

    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {}

    public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
        return who;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean canConvert(Class type) {
        return type.getName().equals(DiagramPanel.class.getName());
    }

}