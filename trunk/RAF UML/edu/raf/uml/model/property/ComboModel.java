package edu.raf.uml.model.property;

public interface ComboModel<T> {
	public int getSelected();
	public void setSelected(int i);
	public T[] getElements();
}
