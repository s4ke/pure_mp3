package pure_mp3;

/**
 * Write a description of class MediaTable here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

// To retrieve the current selection, use JTable.getSelectedRows which returns an array of row indexes, and JTable.getSelectedColumns which returns an array of column indexes. To retrieve the coordinates of the lead selection, refer to the selection models for the table itself and for the table's column model. The following code formats a string containing the row and column of the lead selection:
// 
//     String.format("Lead Selection: %d, %d. ",
//         table.getSelectionModel().getLeadSelectionIndex(),
//         table.getColumnModel().getSelectionModel().getLeadSelectionIndex());

        
import java.awt.Color;

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
        table = new JTable() {
        	private static final long serialVersionUID = 20100125;
            public boolean isCellEditable(int x, int y) {
                return false;
            }
        };
        
        table.setSize(780,411);
        table.setLocation(0,0);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Artist");
        model.addColumn("Title");
        model.addColumn("Album");
        model.addColumn("Length");
        String string[]={"This","feature","is not implemented","yet"};
//        int i = 0;
//        while(i < 100)
//        {
            model.addRow(string);
//            i++;
//        }
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false); 
        add(table);
        setViewportView(table);
        setBackground(Color.white);
    }
}
