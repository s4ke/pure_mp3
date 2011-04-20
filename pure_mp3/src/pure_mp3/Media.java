 /**
 * @author Martin Braun
 *   
 * This file is part of pure.mp3.
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

import java.awt.Color;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class Media extends JPanel
{
	private static final long serialVersionUID = 2385007980763532219L;
    private MediaBar mediabar;
    private MediaTable mediatable;
    public Media()
    {
        super();
        setLayout(new MigLayout("insets 0 0 0 0","[grow,fill]","[][grow,fill]"));        
        
        mediabar = new MediaBar();
        add(mediabar,"wrap");
        
        mediatable = new MediaTable();
        add(mediatable);
    }
}
