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
  

  import java.awt.datatransfer.DataFlavor;
  import java.awt.datatransfer.Transferable;
  import java.awt.datatransfer.UnsupportedFlavorException;
  import java.io.IOException;
  
  public class ListMoveTransferable implements Transferable {
 
    private final ListMoveTransferData data;
 
    public ListMoveTransferable(ListMoveTransferData data) {
    	this.data = data;
    }
 
    public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[] { new ListMoveDataFlavor(data.getModel()) };
    }
 
    public boolean isDataFlavorSupported(DataFlavor flavor) {
      return ListMoveDataFlavor.match(data.getModel(), flavor);
    }
 
    public Object getTransferData(DataFlavor flavor)
          throws UnsupportedFlavorException, IOException {
      if (!isDataFlavorSupported(flavor)) {
        throw new UnsupportedFlavorException(flavor);
      }
      return data;
    }
  }
 

 

