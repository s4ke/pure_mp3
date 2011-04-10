package pure_mp3;

/**
 * Write a description of class Media here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import javax.swing.JPanel;
public class Media extends JPanel
{
	private static final long serialVersionUID = 2385007980763532219L;
    private MediaBar mediabar;
    private MediaTable mediatable;
    public Media()
    {
        super();
        setLayout(null);        
        
        mediabar = new MediaBar();
        mediabar.setBounds(0,0,515,20);
        add(mediabar);
        
        mediatable = new MediaTable();
        mediatable.setBounds(0,20,515,405);
        add(mediatable);
    }
}
