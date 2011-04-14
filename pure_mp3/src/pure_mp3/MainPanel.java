package pure_mp3;

/**
 * Write a description of class MainPanel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import javax.swing.JPanel;
import javax.swing.JSlider;

import net.miginfocom.swing.MigLayout;
public class MainPanel extends JPanel
{
	private static final long serialVersionUID = 2385007980763532219L;
    private PlayerMenu playerMenu;
    private Info info;
    private PlayList playList;
    private Player player;
    private JSlider progress;
    private Media media;
    public MainPanel()
    {
        setLayout(new MigLayout("insets 5 5 5 5","[fill][grow,fill]","[][][grow,fill]"));
       
        playerMenu = new PlayerMenu();
        add(playerMenu, "h ::80");
        
        info = new Info();
        add(info,"align left, gapright 0, wrap"); 
        
        playList = new PlayList();
        add(playList, "east, gapright 5, gaptop 5, gapbottom 5, w 33%!, h 100%");
        
        progress = new Progress();
        add(progress,"span 2, gapright 0, wrap");
        
        media = new Media();
        add(media,"span 2 2, gapright 0, wrap"); 
    }
    
    public PlayList getPlayList()
    {
    	return playList;
    }
    
    public Player getPlayer()
    {
    	return player;
    }
}
