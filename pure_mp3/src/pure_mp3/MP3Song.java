package pure_mp3;

import java.io.File;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFormat;

public class MP3Song extends Song
{
	public MP3Song(File xSource)
	{
		super(xSource);
	}
	
	
	
	
//	@Override
//	public int getDurationInSeconds() 
//	{
//		try
//		{
//			File source = super.getSource();
//			if(getSource() != null)
//			{
//				AudioFileFormat audioFileFormat = null;
//				try
//				{
//					audioFileFormat = AudioSystem.getAudioFileFormat(source);
//				}
//				catch(Exception e)
//				{
//					System.out.println("Couldn't get AudioFileFormat!");
//				}
//				// get all properties
//				if(audioFileFormat != null)
//				{
//					Map<String, Object> properties = audioFileFormat.properties();
//					Long duration = (Long) properties.get("duration");
//					double durationInSeconds_ = (duration/1000)/1000;
//					return (int) durationInSeconds_;
//				}
//				else
//				{
//					System.out.println("AudioFileFormat equals null-Reference");
//					return -1;
//				}
//			}
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			System.out.println("Some other Bug in Song.java!");
//			return -1; 
//		}
//		return -1;
//	}
	
}
