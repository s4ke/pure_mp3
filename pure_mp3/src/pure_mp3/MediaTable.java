 /**
 * @author Martin Braun
 *   
 * This file is part of pure.mp3.
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

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MediaTable extends JScrollPane
{
	private static final long serialVersionUID = 2385007980763532219L;
    private JTable table;
    public MediaTable()
    {
        super();
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);          
        table = new JTable() 
        {
        	private static final long serialVersionUID = 20100125;
            public boolean isCellEditable(int x, int y) 
            {
                return false;
            }
        };
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Artist");
        model.addColumn("Title");
        model.addColumn("Album");
        model.addColumn("Length");
        String string[]={"This","feature","is not implemented","yet"};
        
        model.addRow(string);
        
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false); 
        add(table);
        setViewportView(table);
//        setBackground(Color.white);
    }
}
