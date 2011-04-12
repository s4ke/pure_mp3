package pure_mp3;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.SwingWorker;

//import javax.swing.DefaultListModel;
//import javax.swing.ListModel;

public class FileCrawler extends SwingWorker<Void,Void> implements PropertyChangeListener
{
	
	public FileCrawler()
	{	
	}
	
	public void add(File file)
	{
		if(file != null)
		{
			File [] files = file.listFiles(
					new FilenameFilter()
			{
				public boolean accept(File dir, String name)
			    {
					File help = new File(dir, name);
			    	return name.endsWith(".mp3")||name.endsWith(".MP3")||help.isDirectory();
			    }
			}
					);
	    	for(int i = 0; i  < files.length; i++)
	    	{
	    		if(files[i].isDirectory())
	    		{
	    			add(files[i]);
	    		}
	    		else
	    		{
			    	try
			    	{			    			
			    		Global.playList.addSong(new Song(files[i]));
			    	}
			    	catch(Exception e)
			    	{
			    			e.printStackTrace();
			    	}
	    		}
	    	}
	    }			
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
//	//ToDo: Extra Class for adding Directory
//    public void addDirectory(File file)
//    {
//    	System.out.println(file.getPath());
////    	String fileSeparator_ = System.getProperty("file.separator");
////    	if ("\\".equals(fileSeparator_)) 
////    	{
////    	      fileSeparator_ = "\\\\";
////    	}
//    	if(file != null)
//    	{
//	    	if(file.isDirectory() && file.exists())
//	    	{
//		    	String[] filenames = file.list(new FilenameFilter()
//		    	{
//		    		public boolean accept(File dir, String name)
//		    	    {
//		    			File help = new File(dir,name);
//		    			return name.endsWith(".mp3")||name.endsWith(".MP3")||help.isDirectory();
//		    	    }
//		        });
//		//    	try
//		    	{
//		    		File fileInsert = null;
//			    	for(int i = 0; i  < filenames.length; i++)
//			    	{
//			    		fileInsert = new File(file,filenames[i]);
//			    		if(fileInsert.isDirectory())
//			    		{
//			    			addDirectory(fileInsert);
//			    		}
//			    		else
//			    		{
//				    		try
//				    		{			    			
//				    			Global.playList.addSong(new Song(new File(file, filenames[i])));
//				    		}
//				    		catch(Exception e)
//				    		{
//				    			e.printStackTrace();
//				    		}
//			    		}
//			        }
//		    	}
//		//    	catch(Exception e)
//		    	{
//		    	}
//	    	}
//	    	else
//	    	{
//	    		addFile(file);
//	    	}
//    	}
////    	return Global.playList.getModel().getSize();
//    }
//    
//    //ToDo: Extra Class for adding File
//    public void addFile(File file)
//    {
//    	if(file != null)
//    	{
//	    	if(!file.isDirectory())
//	    	{
//	    		try
//	    		{
//	    	    	if(file.toString().endsWith(".mp3")||file.toString().endsWith(".MP3"))
//	    	    	{
//	    	    		Global.playList.addSong(new Song(file));
//	    	    	}
//	    		}
//	    		catch(Exception e)
//	    		{
//	    			e.printStackTrace();
//	    		}
//	    	}
//	    	else
//	    	{
//	    		addDirectory(file);
//	    	}
//    	}	
////    	return Global.playList.getModel().getSize();
//    }

	
    

}
