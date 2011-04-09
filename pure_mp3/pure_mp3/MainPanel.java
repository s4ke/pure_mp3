package pure_mp3;

/**
 * Write a description of class MainPanel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import javax.swing.JPanel;
import javax.swing.JSlider;
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
        setLayout(null);
       
        playList = new PlayList();
        playList.setBounds(533,7,258,535);
        add(playList);
                
        info = new Info();
        info.setBounds(233,7,290,80);
        add(info);       
                
        playerMenu = new PlayerMenu();
        playerMenu.setBounds(10,7,221,80);
        add(playerMenu);
        
        progress = new Progress();
        progress.setBounds(6,92,520,20);
        add(progress);
        
        media = new Media();
        media.setBounds(10,117,515,463);
        add(media);       
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
