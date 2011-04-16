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
        setLayout(new MigLayout("insets 5 5 5 5, nogrid, nocache"));
        
        playerMenu = new PlayerMenu();
        add(playerMenu, "pos 5 5 n 90, id playerMenu");
        
        info = new Info();
        add(info,"x (playerMenu.x2 + 5), x2 (playList.x - 5), y 5, id info"); 
        
        playList = new PlayList();
        add(playList, "pos (66% - 5) 5 (100% - 5) (100% -5), id playList");
        
        progress = new Progress();
        add(progress,"pos 0 (info.y2 + 5) (playList.x) n, id progress");
        
        media = new Media();
        add(media,"pos 5 (progress.y2 + 5) (playList.x - 5) (100% - 5), id media"); 
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
