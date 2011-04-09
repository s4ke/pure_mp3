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

import javax.swing.DefaultListModel;

public class ListMoveDataFlavor extends DataFlavor {
 
    private final DefaultListModel model;
 
    public ListMoveDataFlavor(DefaultListModel model) {
      super(ListMoveTransferData.class, "List Data");
      this.model = model;
    }
 
    public DefaultListModel getModel() {
      return model;
    }
 
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((model == null) ? 0 : model.hashCode());
      return result;
    }
 
    @Override
    public boolean equals(DataFlavor that) {
      if (this == that) {
        return true;
      }
      if (!super.equals(that) || getClass() != that.getClass()) {
        return false;
      }
      return match(model, that);
    }
 
    /**
     * Tests whether the given data flavor is a {@link ListMoveDataFlavor} and
     * matches the given model.
     * 
     * @param model the model
     * @param flavor the flavor
     * @return {@code true} if matches
     */
    public static boolean match(DefaultListModel model, DataFlavor flavor) {
      return flavor instanceof ListMoveDataFlavor
            && ((ListMoveDataFlavor) flavor).getModel() == model;
    }
  }