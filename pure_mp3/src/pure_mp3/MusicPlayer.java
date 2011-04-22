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

import javax.sound.sampled.AudioFormat;

/**
 * Interface for a Player.
 * @author Martin Braun
*/
public interface MusicPlayer 
{
	public void start();
	public void pause();
	public void stop_();
	public void seek(int percentage);
	public long getMicrosecondPosition();
	public boolean isStopped();
	public void setVolume(float xVolume);
	public AudioFormat getAudioFormat();
	public int getFramePosition();
	public int getFrameLength();
	public int getDurationInSeconds();
	public Song getCurrentSong();
}
