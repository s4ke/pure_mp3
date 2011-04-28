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

//import java.awt.event.KeyEvent;
//import java.io.File;
//import java.io.IOException;

//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;

//import jxgrabkey.HotkeyConflictException;
//import jxgrabkey.HotkeyListener;
//import jxgrabkey.JXGrabKey;
import net.miginfocom.swing.MigLayout;

/**
 * The MainPanel for the Player
 * @author Martin Braun
*/
public class MainPanel extends JPanel
{
	private static final long serialVersionUID = 2385007980763532219L;
    private PlayerMenu playerMenu;
    private Info info;
    private PlayList playList;
    private JSlider progress;
    private Media media;
//    private static final int MY_HOTKEY_INDEX = 1;
    
    public MainPanel()
    {
        setLayout(new MigLayout("insets 5 5 5 5, nogrid, nocache"));
        
        playerMenu = new PlayerMenu();
        add(playerMenu, "pos 5 5 n info.y2, id playerMenu");
        
        info = new Info();
        add(info,"x (playerMenu.x2 + 5), x2 (playList.x - 5), y 5, id info"); 
        
        playList = new PlayList();
        add(playList, "pos (66% - 5) 5 (100% - 5) (100% -5), id playList");
        
        progress = new Progress();
        add(progress,"pos 0 (info.y2 + 5) (playList.x) n, id progress");
        
        media = new Media();
        add(media,"pos 5 (progress.y2 + 5) (playList.x - 5) (100% - 5), id media");
        repaint();
    }
}
