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

//import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
public class MediaBar extends JPanel
{
	private static final long serialVersionUID = 2385007980763532219L;
    private JTextField input;
    private JButton search;
    public MediaBar()
    {
        super();
        setLayout(new MigLayout("insets 0 0 0 0","[fill][grow]",""));
        
        input = new JTextField("Search a Song...");
        add(input,"sizegroupy mediabar, w 150!");
        
        search = new JButton("Go!");
        add(search,"sizegroupy mediabar");
    }  
}
