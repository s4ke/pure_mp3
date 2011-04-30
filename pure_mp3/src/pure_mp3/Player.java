/*
 *  This file is part of pure.mp3.
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

/**
 * The Player. Manages The SlideUpdater and the MusicPlayer
 * @author Martin Braun
*/
public class Player
{
	private static final long serialVersionUID = 20100125;
	public static final int randomPlayback = 1;
	public static final int normalPlayback = 0;
    private MusicPlayer musicPlayer;
    private SlideUpdater slideUpdater;
    private Progress progress;
    private int playMode;
    private boolean playing;
    private boolean paused;
    
    /**
     * Basic Constructor
     * @param xPlayMode the PlayMode -> static vars
     */
    public Player(int xPlayMode)
    {
    	playMode = xPlayMode;
        musicPlayer = null;
        playing = false;
        paused = false;
    }
    
    /**
     * Plays the previous Song
     */
    public void playPrev()
    {
    	prev();
    	Global.info.update();
        System.out.println("Previous Title: " + Global.playList.getCurrent());
        stop();
        playpause(false);
    }
    
    /**
     * sets the current song in the PlayList to the previous one
     */
    public void prev()
    {
    	switch(playMode)
    	{
	    	case normalPlayback:
	    		//normal playmode:
	    		Global.playList.prev();
	    		
	    		break;    		
	    	case randomPlayback:
	    		//random playmode
	    		int current = Global.playList.getCurrent();
	    		Global.playList.random();
	    		if(current == Global.playList.getCurrent() && (Global.playList.getModel().getSize() > 1))
	    		{
	    			prev();
	    		}
	    		break;
    	}
    	if(Global.playList.getNumberOfSongs() == 0)
		{
			playing = false;
		}
    }
    
    /**
     * plays the next Song
     */
    public void playNext()
    {
    	next();
    	Global.info.update();
        System.out.println("Next Title: " + Global.playList.getCurrent());
        stop();
        playpause(false);    
    }
    
    /**
     * sets the current song in the PlayList to the next one
     */
    public void next()
    {
    	switch(playMode)
    	{
	    	case normalPlayback:
	    		//normal playmode:
	    		Global.playList.next();
	    		break;    		
	    	case randomPlayback:
	    		//random playmode
	    		int current = Global.playList.getCurrent();
	    		Global.playList.random();
	    		if(current == Global.playList.getCurrent() && (Global.playList.getNumberOfSongs() > 1))
	    		{
	    			next();
	    		}
	    		break;
    	}
    	if(Global.playList.getNumberOfSongs() == 0)
		{
			playing = false;
		}
    }
    
    /**
     * Method for controlling playback and pausing
     * @param byUser invoked by User?
     */
	public synchronized void playpause(boolean byUser)
    {	
		if(musicPlayer == null && (Global.playList.getNumberOfSongs() > 0))
		{
			//if player hasn't started playing yet and and the playmode is random and 
			//the user himself clicked on play and not another method invoked playpause(byUser)
			if(playMode == 1 && !playing && byUser)
			{
				paused = false;
				next();
			}
			//same but for playMode == 0
			else if(playMode == 0 && !playing && byUser)
			{
				Global.playList.setCurrentAndDisplay(0);
			}
			//normal playmode; the value for the current song could be negative because of
			//deleting the whole playList. So it has to be checked and fixed.
			//Thats because when everything is deleted the first song has to 
			//be selected and played
			else
			{
				Global.playList.checkCurrentNegative();
			}
			//now start the playback
			playing = true;
			//create and start the musicplayer
			musicPlayer = new StreamMusicPlayer();
			musicPlayer.start();
			//and the SlideUpdater
			slideUpdater = new SlideUpdater(musicPlayer,progress);			
			slideUpdater.start();	
			System.out.println("Play: " + Global.playList.getCurrent());
			//but first update the Info about the song
			Global.info.update();
		}
		else
		{
			//pause everything
			paused = !paused;
			if(musicPlayer != null)
			{
				musicPlayer.pause();
			}
			if(slideUpdater != null)
			{
				slideUpdater.pause();
			}
		}
		notify();
	}
	
	/**
	 * stops the playback
	 */
	public void stop()
	{
		//stop everything
		if(musicPlayer!=null)
		{
			musicPlayer.stop_();
		}
		if(slideUpdater != null)
		{
			slideUpdater.stop_();
		}
		
		//destroy the objects. let the garbage collector his work
		musicPlayer = null;		
		slideUpdater = null;
	}
	
	/**
	 * @param percentage the position in % where to skip to
	 */
	public void seek(int percentage)
	{
		if(musicPlayer!=null)
		{
			musicPlayer.pause();
			slideUpdater.pause();
			stop();
			musicPlayer = new StreamMusicPlayer();
			musicPlayer.seek(percentage);
			musicPlayer.start();
			slideUpdater = new SlideUpdater(musicPlayer,progress);
			slideUpdater.start();		
		}
	}
    
	/**
	 * @param xVolume percentage of loudness 0-100
	 */
    public void changeVolume(int xVolume)
    {
    	//if there is a player set his volume and the global vars
        if(musicPlayer != null)
        {
        	musicPlayer.setVolume(xVolume);
        }
        //if there is none just write it into the global vars
        else
        {
        	Global.setVolume(xVolume);
        }
    }
    
    /**    
     * Sets the Progressbar
     * @param xProgress the progressbar
     */
    public void setProgress(Progress xProgress)
    {
    	progress = xProgress;
    }
    
    /**
     * @param xPlayMode variable that can be 1 or 0. -> static vars
     */
    public void setPlayMode(int xPlayMode)
    {
    	playMode = xPlayMode;
    }
    
    /**
     * @return the playmode in integers
     */
    public int getPlayMode()
    {
    	return playMode;
    }
    
    /**
     * @return variable that says if the Player is playing
     */
    public boolean isPlaying()
    {
    	return playing;
    }
    
    /**
     * @return variable that says if the Player is paused
     */
    public boolean isPaused()
    {
    	return paused;
    }
    
    /**
     * @return reference to the current Song
     */
    public Song getCurrentSong()
    {
    	if(musicPlayer != null)
    	{
    		return musicPlayer.getCurrentSong();
    	}
    	return null;
    }

}
