package pure_mp3;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

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
       // TO-DO: Wenn URL dann false.
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
       return length;
   }
   
   public File getSource()
   {
       return source;
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
}
