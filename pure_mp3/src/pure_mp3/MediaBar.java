package pure_mp3;

/**
 * Write a description of class SearchBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

//import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
public class MediaBar extends JPanel
{
	private static final long serialVersionUID = 2385007980763532219L;
    private JTextField input;
    private JButton search;
    public MediaBar()
    {
        super();
        setLayout(new MigLayout("insets 0 0 0 0","[fill][grow]",""));
        
        input = new JTextField("Search a Song...");
//        input.setBounds(0,0,200,20);
        add(input,"sizegroup mediabar");
        
        search = new JButton("Go!");
//        search.setBounds(200,0,60,20);
        add(search,"sizegroup mediabar");
    }  
}
