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

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * The GUI for the Player
 * @author Martin Braun
*/
public class GUI extends JFrame
{
	private static final long serialVersionUID = 2385007980763532219L;
    private MainPanel mainPanel;
    
    public GUI()
    {
        super("pure.mp3");
        Global.setGUI(this);
        init();
    }
    
    private void init()
    {
        SwingUtilities.invokeLater(new Runnable(){
             public void run() 
             {
            	 	setMinimumSize(new Dimension(420,300));
                    mainPanel = new MainPanel();
                    setContentPane(mainPanel);
                    setJMenuBar(new Menu());
                    System.setProperty("apple.laf.useScreenMenuBar", "true");
                    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "pure.mp3");
                    setDefaultCloseOperation(EXIT_ON_CLOSE);   
                    setResizable(true);
                    pack();
                    setSize(800,600); //Has to be done after the Window has been layouted to prevent a bug under Windows
                    setLocationRelativeTo(null);
                    setVisible(true); 
            }
        });
    }
}
