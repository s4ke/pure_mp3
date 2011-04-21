/*
 *  This file is part of pure.mp3.
 *
 *  pure.mp3 is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  pure.mp3 is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with pure.mp3.  If not, see <http://www.gnu.org/licenses/>.
 */
package pure_mp3;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Renders the PlayList from the Songs
 * @author Martin Braun
*/
public class PlayListRenderer extends JLabel implements ListCellRenderer {
	 private static final long serialVersionUID = 2385007980763532219L;

     public Component getListCellRendererComponent(
       JList list,
       Object value,            // value to display
       int index,               // cell index
       boolean isSelected,      // is the cell selected
       boolean cellHasFocus)    // the list and the cell have the focus
      {
           Song song = (Song) value;
           String s = song.getData();
           setText(s);
       	   if (isSelected) 
       	   {
                 setBackground(list.getSelectionBackground());
    	         setForeground(list.getSelectionForeground());
    	   }
           else
           {
    	       setBackground(list.getBackground());
    	       setForeground(list.getForeground());
    	   }
    	   setEnabled(list.isEnabled());
    	   setFont(list.getFont());
           setOpaque(true);
           return this;
     }
 }
