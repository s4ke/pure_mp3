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

import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.net.URL;
import java.util.List;

import com.google.inject.Inject;

/**
 * Listens for Drops of Music Files
 * @author Martin Braun
*/
public class PlayListDropTargetListener  implements DropTargetListener {

  private final FileCrawler fileCrawler;
  
  @Inject
  public PlayListDropTargetListener(FileCrawler xFileCrawler) 
  {
	  fileCrawler = xFileCrawler;
  }

  public void dragEnter(DropTargetDragEvent dtde) {
    System.out.println("Drag Enter");
  }

  public void dragExit(DropTargetEvent dte) {
    System.out.println("Drag Exit");
  }

  public void dragOver(DropTargetDragEvent dtde) {
    System.out.println("Drag Over");
  }

  public void dropActionChanged(DropTargetDragEvent dtde) {
    System.out.println("Drop Action Changed");
  }

  	@SuppressWarnings("unchecked")
	public void drop(DropTargetDropEvent dropTargetDropEvent) {
	    try {
	      // Ok, get the dropped object and try to figure out what it is
	      Transferable transferable = dropTargetDropEvent.getTransferable();
	      DataFlavor[] flavors = transferable.getTransferDataFlavors();
	      for (int i = 0; i < flavors.length; i++) 
	      {
	    	  System.out.println("Possible flavor: " + flavors[i].getMimeType());
	    	  if(flavors[i].equals(java.awt.datatransfer.DataFlavor.javaFileListFlavor))
	    	  {
	    		  dropTargetDropEvent.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
	    		  final List list = (List<File>) transferable.getTransferData(flavors[i]);
	    		  System.out.println(((File)list.get(0)).getPath());
	    		  new Thread()
				  {
					  public void run()
					  {
						  for(int j = 0; j < list.size(); j++)
		    			  {
		    				  try
		    				  {
		    					  fileCrawler.addToPlayList((File)list.get(j));
		    				  }
		    				  catch(Exception e)
		    				  {
		    				  }
		    			  }   		
					  }
				  }.start();
	    		  	  
    			  dropTargetDropEvent.dropComplete(true);
    			  return;
	    	  }
	    	  if (flavors[i].equals(java.awt.datatransfer.DataFlavor.stringFlavor))
	    	  {
	    		  dropTargetDropEvent.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
	    		  Object transferData = transferable.getTransferData(flavors[i]);
	    		  if(transferData instanceof String)
	    		  {
	    			  String string = (String) transferData;
	    			  final String strings[] = string.split("\n");
	    			  if(strings[strings.length-1].endsWith("\n"));
	    			  {
	    				  strings[strings.length-1] = strings[strings.length-1].substring(0,strings[strings.length-1].length()-1);
	    			  }
					  new Thread()
					  {
						  public void run()
						  {
			    			  for(int j = 0; j < strings.length; j++)
			    			  {
			    				  try
			    				  {	    					  
			    					  {
			    						  fileCrawler.addToPlayList(new File(new URL(strings[j]).toURI().getPath()));
			    					  }
			    				  }
			    				  catch(Exception e)
			    				  {
			    				  }
			    			  }   	
						  }
					  }.start();			  		  
	    			  dropTargetDropEvent.dropComplete(true);
	    			  return;
	    		  }
	    		  
	    	  }
	      }
	      // Hmm, the user must not have dropped a file list
	     System.out.println("Drop failed: " + dropTargetDropEvent);
	     dropTargetDropEvent.rejectDrop();
	    } 
	    catch (Exception e) 
	    {
	      e.printStackTrace();
	      dropTargetDropEvent.rejectDrop();
	    }
	  }
  
} 