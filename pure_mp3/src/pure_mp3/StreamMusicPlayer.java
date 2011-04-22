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

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.FloatControl;

/**
 * MusicPlayer inspired by Matthias Pfisterer's examples on JavaSound
 *  (jsresources.org). Because of the fact, that this Software is meant 
 *  to be Open-Source and I don't want to get anybody angry about me 
 *  using parts of his intelligence without mentioning it, I hereby 
 *  mention him as inspiration, because his code helped me to write this class.
 *  @author Martin Braun
 */
public class StreamMusicPlayer extends Thread implements MusicPlayer
{
	private static final int	EXTERNAL_BUFFER_SIZE = 128000;
	private AudioInputStream audioInputStream;
	private SourceDataLine line;
	private AudioFormat audioFormat;
	private DataLine.Info info;
	private Song song;
	private boolean playing;
	private long skippedDurationInMicroSeconds;
	private int skippedFrames;
	private boolean pause = false;
	private boolean stop = false;
	
	public StreamMusicPlayer()
	{
		super();
		song = Global.playList.getCurrentSong();
		try
		{
			insert(song);
		}
		catch(Exception e)
		{
			System.out.println("Error while inserting the file");
		}
		
	}

	public void insert(Song song)
	{		
		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(song.getSource());
		}
		catch (Exception e)
		{
			System.out.println("Error while parsing URL to File/Stream");
		}		
		if (audioInputStream != null)
		{
			AudioFormat	format = audioInputStream.getFormat();
			if ( format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED ) 
			{
		         AudioFormat newFormat = new AudioFormat(
		            AudioFormat.Encoding.PCM_SIGNED, 
		            format.getSampleRate(),
		            16,
		            format.getChannels(),
		            format.getChannels() * 2,
		            format.getSampleRate(),
		            false );
		         AudioInputStream newStream = AudioSystem.getAudioInputStream( newFormat, audioInputStream );
		         format = newFormat;
		         audioInputStream = newStream;
		   }
		}
		audioFormat = audioInputStream.getFormat();
		info = new DataLine.Info(SourceDataLine.class,audioFormat);
	}

	public void run()
	{
//		running = true;
		try
		{
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(audioFormat);
			line.start();
		}
		catch(LineUnavailableException e)
		{
			System.out.println(e.getMessage());
			System.out.println("Line unavailable");
		}
		catch(Exception e)
		{
			System.out.println("Error while starting playback");
		}
		int nBytesRead = 0;
		int bufferSize = EXTERNAL_BUFFER_SIZE;
		if(audioFormat != null)
		{
			bufferSize = (int) audioFormat.getSampleRate() * audioFormat.getFrameSize();
			System.out.println(audioFormat);
		}
		byte[]	abData = new byte[bufferSize];
		System.out.println("Buffer Size: " + bufferSize);
		if(line != null)
		{
			setVolume(Global.volume);
		}
		while (nBytesRead != -1 && !stop && line != null)
		{
			synchronized(this) 
			{
                while (pause && !stop)
                {
                	try
                	{
                		System.out.println("paused");
                		wait();
                	}
                	catch(Exception e)
                	{
                	}                	
                }
                notify();
            }
			if(!stop)
			{
				try
				{	
					nBytesRead = audioInputStream.read(abData, 0, abData.length);
//					position = position + nBytesRead;
				}
				catch (IOException e)
				{
					nBytesRead = -1;
				}
				if(nBytesRead != -1);
				{
					try
					{
						line.write(abData, 0, nBytesRead);
					}
					catch(IllegalArgumentException e)
					{
						//Has to be caught, because if the stream ends, there may occur an error, 
						//that the amount of bytes read is not valid (occurred under OpenJDK).
					}
				}
			}
		}
		if(line != null)
		{
			line.drain();
			line.close();
		}
//		running = false;
		if(!stop && !pause)
		{
			Global.player.playNext();
		}
	}
	
	@Override
	public synchronized void pause()
	{
		pause = !pause;
		if(!pause)
		System.out.println("Weiter Geht's!");
		notify();
	}
	
	@Override
	public synchronized void stop_()
	{
		stop = true;
		notify();
	}
	
	@Override
	public synchronized boolean isStopped()
	{
		return stop;
	}
	
	public synchronized boolean isPlaying()
	{
		return playing;
	}
	
	@Override
	public void setVolume(float xVolume)
	{
		Global.setVolume(xVolume);
		if(line.isControlSupported(FloatControl.Type.MASTER_GAIN))
		{
			xVolume = xVolume/100; 
            FloatControl volume = (FloatControl) line.getControl( FloatControl.Type.MASTER_GAIN);
            double minGainDB = volume.getMinimum();
            double ampGainDB = ((10.0f/20.0f)*volume.getMaximum())-volume.getMinimum(); 
            double valueDB = minGainDB + (1/Global.linearscalar)*Math.log(1+(Math.exp(Global.linearscalar*ampGainDB)-1)*xVolume);
            volume.setValue((float) valueDB); 			
		}
		else if( line.isControlSupported(FloatControl.Type.VOLUME)) 
		{
            FloatControl volume = (FloatControl) line.getControl( FloatControl.Type.VOLUME);
            float onePercent = volume.getMaximum()/100;
            volume.setValue(xVolume*onePercent);
        }
	}
	
	@Override
	public long getMicrosecondPosition()
    {
        return line.getMicrosecondPosition() + (skippedDurationInMicroSeconds/1000);
    }
	
	public int getFramePosition()
	{
		if(line != null)
		{
			return line.getFramePosition()+skippedFrames;
		}
		else
		{
			return 0;
		}
	}
	
	public AudioFormat getAudioFormat() 
	{
		return audioFormat;
	}
	
//	public long getPosition()
//	{
//		return position;
//	}
	
	public AudioInputStream getAudioInputStream()
	{
		return audioInputStream;
	}
	
	public Song getCurrentSong()
	{
		return song;
	}
	
	public void seek(int percentage)
	{	
			// just pause the playback
			pause();
			try 
			{
				File file = null;
				try
				{
					file = new File(song.getSource().toURI());
				}
				catch(Exception e)
				{
					file = new File(song.getSource().getPath());
				}
				if(file != null)
				{
					long durationInSeconds = getDurationInSeconds();
					double skippedPercentage = (double)percentage/100;
					long skippedDurationInSeconds = (long) (durationInSeconds * skippedPercentage);
					long bytesToSkip = (long)((skippedDurationInSeconds)*(double)(audioFormat.getSampleRate()*audioFormat.getFrameSize()));
					System.out.println("Length in Bytes: " + (durationInSeconds)*((long)audioFormat.getSampleRate())*audioFormat.getFrameSize());
					System.out.println("Bytes to Skip: " + bytesToSkip);
					long bytesSkipped = audioInputStream.skip(bytesToSkip);
					System.out.println("Bytes Skipped: " + bytesSkipped);
					skippedFrames = skippedFrames + (int) bytesSkipped/audioFormat.getFrameSize();
					System.out.println("Frame Length: " + getFrameLength());
					System.out.println("Frames Skipped: " + skippedFrames);
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}	
			catch(Exception e)
			{
				e.printStackTrace();
			}
			// start playing again
			pause();
	}
	
	public int getFrameLength()
	{
//		if(stop)
//		{
//			return -1;
//		}
		try
		{
			int durationInSeconds = getDurationInSeconds();
			if(durationInSeconds > 0)
			{
				return (int)(durationInSeconds)*((int)audioFormat.getSampleRate());
			}
			return -1;
		}
		catch(Exception e)
		{
			System.out.println("Some other bug!");
			return -1;
		}
	}
	
	public int getDurationInSeconds()
	{
		return song.getDurationInSeconds();
	}
	
}



