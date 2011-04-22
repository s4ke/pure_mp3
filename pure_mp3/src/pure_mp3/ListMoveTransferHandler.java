package pure_mp3;
/* Copyright 2009 Sebastian Haufe
 * Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
 * Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */



import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;

public class ListMoveTransferHandler extends TransferHandler {
 
    /** Serial version UID */
    private static final long serialVersionUID = 6703461043403098490L;
 
    @Override
    public int getSourceActions(JComponent c) {
      final JList list = (JList) c;
      
      return list.getModel() instanceof DefaultListModel ? MOVE : NONE;
    }
 
    @Override
    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
      if (!(comp instanceof JList)
            || !(((JList) comp).getModel() instanceof DefaultListModel)) {
        return false;
      }
 
      final DefaultListModel model =
            (DefaultListModel) ((JList) comp).getModel();
      for (DataFlavor f : transferFlavors) {
        if (ListMoveDataFlavor.match(model, f)) {
          return true;
        }
      }
      return false;
    }
 
    @Override
    protected Transferable createTransferable(JComponent c) {
      final JList list = (JList) c;
      //Martin Braun
      Global.playList.getList().setDropTarget(null);
      Global.playList.getList().setTransferHandler(new ListMoveTransferHandler());
      //...
      final int[] selectedIndices = list.getSelectedIndices();
      return new ListMoveTransferable(new ListMoveTransferData(
            (DefaultListModel) list.getModel(), selectedIndices));
    }
 
    @Override
    public boolean importData(TransferHandler.TransferSupport info) {
      final Component comp = info.getComponent();
      if (!info.isDrop() || !(comp instanceof JList)) {
        return false;
      }
      final JList list = (JList) comp;
      final ListModel lm = list.getModel();
      if (!(lm instanceof DefaultListModel)) {
        return false;
      }
 
      final DefaultListModel listModel = (DefaultListModel) lm;
      final DataFlavor flavor = new ListMoveDataFlavor(listModel);
      if (!info.isDataFlavorSupported(flavor)) {
        return false;
      }
 
      final Transferable transferable = info.getTransferable();
      try {
    	//Martin Braun
    	Song currentSong = Global.playList.getCurrentSong();  
    	//...
    	  
        final ListMoveTransferData data =
              (ListMoveTransferData) transferable.getTransferData(flavor);
 
        // get the initial insertion index
        final JList.DropLocation dropLocation = list.getDropLocation();
        int insertAt = dropLocation.getIndex();
 
        // get the indices sorted (we use them in reverse order, below)
        final int[] indices = data.getIndices();
        Arrays.sort(indices);
 
        // remove old elements from model, store them on stack
        final Stack<Object> elements = new Stack<Object>();
        int shift = 0;
        for (int i = indices.length - 1; i >= 0; i--) {
          final int index = indices[i];
          if (index < insertAt) {
            shift--;
          }
          elements.push(listModel.remove(index));
        }
        insertAt += shift;
 
        // insert stored elements from stack to model
        final ListSelectionModel sm = list.getSelectionModel();
        try {
          sm.setValueIsAdjusting(true);
          sm.clearSelection();
          final int anchor = insertAt;
          Object object = null;
          while (!elements.isEmpty()) {
        	System.out.println(elements.peek());
        	//Changes by Martin Braun
        	object = elements.pop();
        	if(object == currentSong)
        	{
        		Global.playList.checkCurrent(insertAt);
        	}
        	//End of Changes by Martin Braun
            listModel.insertElementAt(object, insertAt);
            sm.addSelectionInterval(insertAt, insertAt++);
          }
          final int lead = insertAt - 1;
          if (!sm.isSelectionEmpty()) {
            sm.setAnchorSelectionIndex(anchor);
            sm.setLeadSelectionIndex(lead);
          }
        } finally {
          sm.setValueIsAdjusting(false);
          //by Martin Braun
          Global.playList.setDropTarget(new DropTarget(Global.playList.getList(),new PlayListDropTargetListener()));
          //...
        }
        return true;
      } catch (UnsupportedFlavorException ex) {
        return false;
      } catch (IOException ex) {
        // FIXME: Logging
        return false;
      }
    }
  }