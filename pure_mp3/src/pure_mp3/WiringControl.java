package pure_mp3;

import javax.swing.JList;
import javax.swing.JSlider;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class WiringControl 
{
	private final MainPanel mainPanel;
	private final Player player;
    private final PlayerMenu playerMenu;
    private final Info info;
    private final PlayList playList;
    private final JSlider progress;
    private final Media media;
    
	@Inject
	public WiringControl(MainPanel xMainPanel)
	{
		mainPanel = xMainPanel;
        player = Global.injector.getInstance(Player.class);
        player.setWiringControl(this);
        
        playerMenu = Global.injector.getInstance(PlayerMenu.class);
        playerMenu.setWiringControl(this);
        mainPanel.add(playerMenu, "pos 5 5 n info.y2, id playerMenu");
        
        info = Global.injector.getInstance(Info.class);
        info.setWiringControl(this);
        mainPanel.add(info,"x (playerMenu.x2 + 5), x2 (playList.x - 5), y 5, id info"); 
        
        playList = Global.injector.getInstance(PlayList.class);
        playList.setWiringControl(this);
        mainPanel.add(playList, "pos (66% - 5) 5 (100% - 5) (100% - 5), id playList");
        
        progress = Global.injector.getInstance(Progress.class);
        mainPanel.add(progress,"pos 0 (info.y2 + 5) (playList.x) n, id progress");
        
        media = Global.injector.getInstance(Media.class);
        mainPanel.add(media,"pos 5 (progress.y2 + 5) (playList.x - 5) (100% - 5), id media");
        
        Global.injector.getInstance(FileCrawler.class).setWiringControl(this);
	}

	public int getPlayListSize() 
	{
		return playList.getModel().getSize();
	}

	public int playListGetCurrent() 
	{
		return playList.getCurrent();
	}

	public void playListRandom() 
	{
		playList.random();	
	}

	public void playListPrev() 
	{
		playList.prev();
	}

	public int playListGetNumberOfSongs() 
	{
		return playList.getNumberOfSongs();
	}

	public void infoUpdate() 
	{
		info.update();		
	}

	public void playListNext() 
	{
		playList.next();		
	}

	public void playListSetCurrentAndDisplay(int i) 
	{
		playList.setCurrentAndDisplay(i);
	}

	public void playListCheckCurrentNegative() 
	{
		playList.checkCurrentNegative();
	}

	public Song playListGetCurrentSong() 
	{
		return playList.getCurrentSong();
	}

	public void playerPlayNext() 
	{
		player.playNext();		
	}
	
	public void playerPlayPrev() 
	{
		player.playPrev();		
	}

	public void playerPlaypause(boolean b) 
	{
		player.playpause(b);
	}
	
	public void playerChangeVolume(int xVolume) 
	{
		player.changeVolume(xVolume);		
	}

	public boolean playerIsPlaying() 
	{
		return player.isPlaying();
	}

	public void playerStop() 
	{
		player.stop();
	}

	public void playListAddSong(Song song)
	{
		playList.addSong(song);
	}

	public JList playListGetList() 
	{
		return playList.getList();
	}

}
