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

import com.google.inject.Inject;

/**
 * Thread used for updating Progress
 * @author Martin Braun
*/
public class SlideUpdater extends Thread
{
	private MusicPlayer musicPlayer;
	private final Progress progress;
	private final Info info;
	private boolean paused;
	private boolean stop;
	
	@Inject
	public SlideUpdater(Progress xProgress, Info xInfo)
	{
		super();
		progress = xProgress;
		info = xInfo;
		paused = false;
	}
	
	/**
	 * The actual method that does the work for updating Progress
	 */
	public void run()
	{
			long frameLength = 0;
			int durationInSeconds = 0;
			synchronized(this)
			{
				if(musicPlayer != null && !stop)
				{
					frameLength = musicPlayer.getFrameLength();
					durationInSeconds = musicPlayer.getDurationInSeconds();
					//Maybe it was stopped? Or sth went wrong
					if(frameLength == -1)
					{
						stop = true;
					}
				}
			}
			final int durationInSeconds_ = durationInSeconds;
			while(!stop)
			{	
				//Block for pausing/unpausing
				synchronized(this) 
				{		
	                while (paused && (!stop))
	                {
	                	try
	                	{
	                		wait();
	                	}
	                	catch(Exception e)
	                	{
	                	}
	                }
	                notify();
	            }
				//update if everything is ok
				if(musicPlayer != null && frameLength > 0 && !stop)
				{
					final double percentage_ = (double)musicPlayer.getFramePosition()/frameLength*100;
					final int percentage = (int) percentage_;
					progress.setValue2(percentage);
					if(durationInSeconds_ > 0)
					{
						info.updatePlayedTime((int)(durationInSeconds_*percentage_/100));
					}
				}
				else
				{
					break;
				}
				try
				{
					//Don't work too much. That get's the PC crazy
					sleep(500);
				}
				catch(Exception e)
				{
				}
			}
		if(!stop)
		{
			progress.setValue2(0);	
		}
	}
	
	/**
	 * Pauses the SlideUpdater
	 */
	public synchronized void pause()
	{
		paused = !paused;
		notify();
	}
	
	/**
	 * Stops the SlideUpdater
	 */
	public synchronized void stop_()
	{
		stop = true;
		notify();
	}
	
	/**
	 * @return the variable stop
	 */
	public synchronized boolean isStopped()
	{
		return stop;
	}
	
	public void setMusicPlayer(MusicPlayer xMusicPlayer)
	{
		musicPlayer = xMusicPlayer;
	}

}
