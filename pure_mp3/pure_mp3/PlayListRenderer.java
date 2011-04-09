package pure_mp3;
//Changed example from http://download.oracle.com/javase/1.4.2/docs/api/javax/swing/JList.html
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

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
