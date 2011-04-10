package pure_mp3;

import java.awt.dnd.*;
import java.awt.datatransfer.*;
//import java.util.List;
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.io.StringWriter;
//import java.io.Writer;
//import java.net.URL;
//import java.util.*;
import java.io.File;
import java.net.URL;
import java.util.List;

//import javax.swing.JList;

public class PlayListDropTargetListener  implements DropTargetListener {


  public PlayListDropTargetListener() 
  {
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
	    		  List list = (List<File>) transferable.getTransferData(flavors[i]);
	    		  System.out.println(((File)list.get(0)).getPath());
	    		  for(int j = 0; j < list.size(); j++)
    			  {
    				  try
    				  {
    					  Global.fileCrawler.addFile((File)list.get(j));
    				  }
    				  catch(Exception e)
    				  {
    				  }
    			  }   			  
    			  dropTargetDropEvent.dropComplete(true);
    			  return;
	    	  }
	//    	   Check for file lists specifically
	    	  if (flavors[i].equals(java.awt.datatransfer.DataFlavor.stringFlavor))
	    	  {
	    		  dropTargetDropEvent.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
	    		  Object transferData = transferable.getTransferData(flavors[i]);
	    		  if(transferData instanceof String)
	    		  {
	    			  String string = (String) transferData;
	    			  String strings[] = string.split("\n");
	    			  if(strings[strings.length-1].endsWith("\n"));
	    			  {
	    				  strings[strings.length-1] = strings[strings.length-1].substring(0,strings[strings.length-1].length()-1);
	    			  }
//	    			  int position = Global.playList.getList().locationToIndex(dropTargetDropEvent.getLocation());
	    			  for(int j = 0; j < strings.length; j++)
	    			  {
	    				  try
	    				  {	    					  
//	    					  if(position >= 0)
//	    					  {
//	    						  position = Global.fileCrawler.addFileAt(new File(new URL(strings[j]).toURI().getPath()),position);
//	    					  }
//	    					  else
	    					  {
	    						  Global.fileCrawler.addFile(new File(new URL(strings[j]).toURI().getPath()));
	    					  }
	    				  }
	    				  catch(Exception e)
	    				  {
	    				  }
	    			  }   			  
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