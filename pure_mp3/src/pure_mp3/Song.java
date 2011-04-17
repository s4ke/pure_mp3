package pure_mp3;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

/**
 * Write a description of class Song here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Song implements Serializable, Transferable
{
   private static final long serialVersionUID = 20100125;
   private String artist;
   private String title;
   private String album;
   private String length;
   private File source;
   
   public Song(File xSource)
   {
         artist = "";
         try
         {
        	 String [] help;
       		 help = xSource.getPath().split(System.getProperty("file.separator"));
        	 title = help[help.length-1];
         }
         catch(Exception e)
         {
        	 e.printStackTrace();
         }
         if(title == null)
         {
        	 title = xSource.getPath();
         }
         album = "";
         length = "";
         source = xSource;
   }
   
   public String getData()
   {
       return artist + " - " + title + " - " + length;
   }
   
   public String getArtist()
   {
       return artist;
   }
   
   public String getTitle()
   {
       return title;
   }
   
   public String getAlbum()
   {
       return album;
   }
   
   public String getLength()
   {
	   int seconds = getDurationInSeconds();
	   int minutes = seconds / 60;
	   String seconds_ = "" + seconds % 60;
	   if(seconds_.length() < 2)
	   {
		   seconds_ = "0" + seconds_;
	   }
	   return minutes + ":" + seconds_;
   }
   
   public File getSource()
   {
       return source;
   }
   
   public void setArtist(String xArtist) 
   {
	   artist = xArtist;
   }

   public void setTitle(String xTitle) 
   {
	   title = xTitle;
   }

   public void setAlbum(String xAlbum) 
   {
	   album = xAlbum;
   }

   public void setLength(String xLength) 
   {
	   length = xLength;
   }

	@Override
	public Object getTransferData(DataFlavor arg0)
		throws UnsupportedFlavorException, IOException 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor arg0) 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public int getDurationInSeconds()
	{
		try
		{
			if(source != null)
			{
				AudioFileFormat audioFileFormat = null;
				try
				{
					audioFileFormat = AudioSystem.getAudioFileFormat(source);
				}
				catch(Exception e)
				{
					System.out.println("Couldn't get AudioFileFormat!");
				}
				// get all properties
				if(audioFileFormat != null)
				{
					Map<String, Object> properties = audioFileFormat.properties();
					Long duration = (Long) properties.get("duration");
					double durationInSeconds_ = (duration/1000)/1000;
					return (int) durationInSeconds_;
				}
				else
				{
					System.out.println("AudioFileFormat equals null-Reference");
					return -1;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Some other Bug!");
			return -1; 
		}
		return -1;
	}
}
