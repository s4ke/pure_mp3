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

import java.awt.Dimension;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

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
            	 	try {
//            	 		MetalLookAndFeel.setCurrentTheme(new TestTheme());
            	        UIManager.setLookAndFeel(new MetalLookAndFeel());
            	    } 
            	    catch (UnsupportedLookAndFeelException e) {
            	       System.out.println("Something is wrong with your MetalLookAndFeel!!!");
            	    }
            	    setSize(800,600);
            	    setMinimumSize(new Dimension(410,300));
                    mainPanel = new MainPanel();
                    setContentPane(mainPanel);
                    setJMenuBar(new Menu());
                    System.setProperty("apple.laf.useScreenMenuBar", "true");
                    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "pure.mp3");
                    setDefaultCloseOperation(EXIT_ON_CLOSE);   
                    setLocationRelativeTo(null);
                    setResizable(true);
                    setVisible(true);
                    
                    
            }
        });
    }
    
    public static void main (String args[])
    {
    	boolean support = false;
		System.out.println("Available Mixers:");
		Mixer.Info[]	aInfos = AudioSystem.getMixerInfo();
		for (int i = 0; i < aInfos.length; i++)			
		{
			Mixer mixer = AudioSystem.getMixer(aInfos[i]);
			Line.Info lineInfo = new Line.Info(SourceDataLine.class);
			if (mixer.isLineSupported(lineInfo))
			{
				System.out.println(aInfos[i].getName());
				support = true;
			}
			else
			{
				System.out.println("Not supported: " + aInfos[i].getName());
			}
		}
		if (aInfos.length == 0)
		{
			System.out.println("[No mixers available]");
			System.exit(ERROR);
		}
		if(support)
		{
			new GUI();
		}
		else
		{
			System.out.println("Sound playback not supported properly!");
			System.exit(ERROR);
		}
    
	}
}
