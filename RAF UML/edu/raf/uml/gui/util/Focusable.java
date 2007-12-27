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
package edu.raf.uml.gui.util;

import edu.raf.uml.model.UMLDiagram;

/**
 * Sve shto mozhe da se fokusira implementira ovaj interfejs
 */
public interface Focusable {

    /**
     * Ovo gui pozove kada taj objekat treba da dobije fokus. Taj objekat sam
     * dizhe z-order tog objekta, prikazuje anchor pointove itd.
     */
    public void gainFocus(UMLDiagram diagram);

    /**
     * Ovo gui pozove kada objekat gubi fokus. U principu treba da sakrije sve
     * anchor pointove
     */
    public void loseFocus(UMLDiagram diagram);
}
