package pure_mp3;

/**
 * Write a description of class Media here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class Media extends JPanel
{
	private static final long serialVersionUID = 2385007980763532219L;
    private MediaBar mediabar;
    private MediaTable mediatable;
    public Media()
    {
        super();
        setLayout(new MigLayout("insets 0 0 0 0","[grow,fill]","[][grow,fill]"));        
        
        mediabar = new MediaBar();
        add(mediabar,"wrap");
        
        mediatable = new MediaTable();
        add(mediatable);
    }
}
