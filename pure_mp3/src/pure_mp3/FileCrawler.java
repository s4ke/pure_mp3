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
import java.io.FilenameFilter;
//import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The FileCrawler which adds Songs to the PlayList and to Media
 * @author Martin Braun
*/
@Singleton
public class FileCrawler
{		
	private WiringControl wiringControl;
	
	@Inject
	public FileCrawler()
	{
	}
	
	 public void setWiringControl(WiringControl xWiringControl)
	 {
		 wiringControl = xWiringControl;
	 }
	
	/**
	 * Adds files or Directories to the PlayList
	 * @param file The files or Directories to add
	 */
	public void addToPlayList(File file)
	{
		addToPlayList2(file);
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				wiringControl.playListGetList().invalidate();
				wiringControl.playListGetList().validate();
			}
			
		});
	}
	
	/**
	 * Used for recursively adding Files and Directories to the PlayList
	 * @param file
	 */
	private void addToPlayList2(File file)
	{
		if(file != null)
		{
			if(!file.isDirectory())
			{
				if(file.getPath().endsWith(".mp3")||file.getPath().endsWith(".MP3")||file.getPath().endsWith(".wav")||file.getPath().endsWith(".WAV"))
				{
					wiringControl.playListAddSong(new Song(file));
				}
			}
			else
			{
				final File [] files = file.listFiles(new FilenameFilter()
				{
					public boolean accept(File dir, String name)
					{
						File help = new File(dir, name);
				    	return name.endsWith(".mp3")||name.endsWith(".MP3")||name.endsWith(".wav")||name.endsWith(".WAV")||help.isDirectory();
				    }
				});
				//iterate through the array and add everything
		    	for(int i = 0; i  < files.length; i++)
		    	{
		    		//and recursively add again if a directory was provided
		    		if(files[i].isDirectory())
		    		{
		    			addToPlayList2(files[i]);
		    		}
		    		else
		    		{
				    	wiringControl.playListAddSong(new Song(files[i]));
		    		}
		    	}
		    }			
		}
	}
	
	public void addToDatabase(File file)
	{
		
	}
	
//	public Song[] addToDatabase2(File file)
//	{
//		if(file != null)
//		{
//			if(!file.isDirectory())
//			{
//				if(file.getPath().endsWith(".mp3")||file.getPath().endsWith(".MP3"))
//				{
//					return new Song[] {new Song(file)};
//				}
//			}
//			else
//			{
//				final File [] files = file.listFiles(new FilenameFilter()
//				{
//					public boolean accept(File dir, String name)
//					{
//						File help = new File(dir, name);
//				    	return name.endsWith(".mp3")||name.endsWith(".MP3")||help.isDirectory();
//				    }
//				});
//				//iterate through the array and add everything
//				Song [] songs = new Song[files.length];
//		    	for(int i = 0; i  < files.length; i++)
//		    	{
//		    		//and recursively add again if a directory was provided
//		    		if(files[i].isDirectory())
//		    		{
//		    			songs + addToDatabase2(files[i]);
//		    		}
//		    		else
//		    		{
//				    	Global.database.addSong(new Song(files[i]));
//		    		}
//		    	}
//		    }			
//		}
//		return null;
//	}
}
