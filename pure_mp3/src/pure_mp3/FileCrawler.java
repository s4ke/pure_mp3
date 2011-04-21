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

/**
 * The FileCrawler which adds Songs to the PlayList and to Media
 * @author Martin Braun
*/
public class FileCrawler
{		
	/**
	 * Adds files or Directories to the PlayList
	 * @param file The files or Directories to add
	 */
	public void addToPlayList(File file)
	{
		addToPlayList2(file);
		Global.playList.revalidateList();
	}
	
	/**
	 * Used for recursively adding Files and Directories to the PlayList
	 * @param file
	 */
	private void addToPlayList2(File file)
	{
		if(file != null)
		{
			//Only a single File?
			if(!file.isDirectory())
			{
				//Is the File a mp3?
				if(file.getPath().endsWith(".mp3")||file.getPath().endsWith(".MP3"))
				{
					//Then add it to the PlayList
					Global.playList.addSong(new Song(file));
				}
			}
			//Or a Directory?
			else
			{
				//Create a Filter for determining wheter to use the files/Directories or not
				//and then let him return the array of files we want
				final File [] files = file.listFiles(new FilenameFilter()
				{
					public boolean accept(File dir, String name)
					{
						File help = new File(dir, name);
				    	return name.endsWith(".mp3")||name.endsWith(".MP3")||help.isDirectory();
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
		    		//or just add normally
		    		else
		    		{
		    			//Lets do that by using the EDT (Event Dispatch Thread) to prevent Crashing
				    	try
				    	{		
				    		final int j = i;
				    		javax.swing.SwingUtilities.invokeLater(new Runnable()
				    		{
				    		    public void run()
				    		    {
				    		    	Global.playList.addSong(new Song(files[j]));
				    		    }
				    		});
				    		
				    	}
				    	catch(Exception e)
				    	{
				    		e.printStackTrace();
				    	}
		    		}
		    	}
		    }			
		}
	}
}
