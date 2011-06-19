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

import com.google.inject.Inject;

/**
 * The GUI for the Player
 * @author Martin Braun
*/
public class GUI extends JFrame
{
	private static final long serialVersionUID = 2385007980763532219L;
    private final MainPanel mainPanel;
    private final WiringControl wiringControl;
//    private final TrayIcon trayIcon;
    
    @Inject
    public GUI(MainPanel xMainPanel, WiringControl xWiringControl)
    {
        super("pure.mp3");
        mainPanel = xMainPanel;
        wiringControl = xWiringControl;
        init();
    }
    
    private void init()
    {
        SwingUtilities.invokeLater(new Runnable(){
             public void run() 
             {
            	 	setMinimumSize(new Dimension(420,300));
                    setContentPane(mainPanel);
                    setJMenuBar(Global.injector.getInstance(Menu.class));
                    System.setProperty("apple.laf.useScreenMenuBar", "true");
                    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "pure.mp3");
                    setDefaultCloseOperation(EXIT_ON_CLOSE);   
                    setResizable(true);
                    pack();
                    setSize(800,600); //Has to be done after the Window has been layouted to prevent a bug under Windows
                    setLocationRelativeTo(null);
                    setVisible(true); 
//                    if(SystemTray.isSupported())
//                    {
//                    	SystemTray systemTray = SystemTray.getSystemTray();
//                    	try
//                    	{
//                    		systemTray.add(new TrayIcon(ImageIO.read(new File("logo.png"))));      
//                    	}
//                    	catch(IOException e)
//                    	{
//                    	} 
//                    	catch (AWTException e) 
//                    	{
//						}
//                    }
            }
        });
    }
}
