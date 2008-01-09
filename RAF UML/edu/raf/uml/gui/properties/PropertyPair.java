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

package edu.raf.uml.gui.properties;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.raf.uml.model.property.Property;

/**
 * Kontejner za neki field/property. Sadrzi sve potrebne objekte za rad
 * propertyja tj refleksije.
 * 
 * @author Srecko Toroman
 * 
 */
public class PropertyPair implements Comparable<PropertyPair> {
	protected final PropertyName namePanel;
	protected final PropertyField fieldPanel;
	protected final Method setter;
	protected final Method getter;
	protected final Object object;
	protected final Property property;
	protected String name = null;

	private String getName() {
		if (name == null) {
			String getterName = getter.getName();
			if (getterName.startsWith("get"))
				name = getterName.substring(3);
			else if (getterName.startsWith("is"))
				name = getterName.substring(2);
			else
				throw new IllegalArgumentException(
						"Property must conform to JavaBeans specification!");
		}
		return name;
	}

	public String getTitle() {
		if (property.title().length() > 0)
			return property.title();
		return getName();
	}

	public PropertyPair(Property property, Method getter, Object object) {
		this.object = object;
		this.property = property;
		this.getter = getter;

		this.namePanel = new PropertyName(this);
		Class<?> type = getter.getReturnType();
		String typeName = type.getName();
		//TODO: mozda zameni ovo sa nekim registrom
		if ("java.lang.String".equals(typeName))
			this.fieldPanel = new StringField(this);
		else if ("Double".equals(typeName) || "double".equals(typeName))
			this.fieldPanel = new DoubleField(this);
		else if ("java.awt.Color".equals(typeName))
			this.fieldPanel = new ColorField(this);
		else if (type.isEnum())
			this.fieldPanel = new EnumField(this);
		else {
			throw new RuntimeException("Type " + type.getName()
					+ " not supported!");
		}
		if (this.getter.getAnnotation(Property.class).editable()) {
			try {
				setter = object.getClass().getMethod("set" + getName(),
						this.getter.getReturnType());
			} catch (Exception e) {
				throw new RuntimeException(
						"Property is declared as editable, but setter doesn't exist");
			}
		} else {
			setter = null;
		}
	}

	public boolean isEditable() {
		return setter != null;
	}

	public Object getValue() {
		try {
			return getter.invoke(this.object);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE,
					"Error getting value", ex);
			return null;
		}
	}

	public void setValue(Object value) {
		try {
			setter.invoke(this.object, value);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE,
					"Error setting value", ex);
		}
	}

	/**
	 * Poredi po alfabetu.
	 */
	@Override
	public int compareTo(PropertyPair o) {
		return getTitle().compareTo(o.getTitle());
	}
}