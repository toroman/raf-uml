package edu.raf.uml.serialization;

import java.awt.Font;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class FontConverter implements Converter {

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Font font = (Font) value;
		writer.addAttribute("name", font.getName());
		writer.addAttribute("size", Integer.toString(font.getSize()));
		writer.addAttribute("style", Integer.toString(font.getStyle()));
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext arg1) {
		String name = reader.getAttribute("name");
		int size = Integer.valueOf(reader.getAttribute("size"));
		int style = Integer.valueOf(reader.getAttribute("style"));
		return new Font(name, style, size);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean canConvert(Class clazz) {
		return clazz.getName().equals("java.awt.Font");
	}

}
