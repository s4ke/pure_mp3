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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

/**
 * The MenuBar for the Player
 * @author Martin Braun
*/
public class Menu extends JMenuBar
{
	private static final long serialVersionUID = 2385007980763532219L;
	private JMenu file;
    private JMenuItem fileAddFilePlayList;
    private JMenuItem fileAddFolderPlayList;
    private JMenuItem fileRemoveAllFilesPlayList;
    private JMenuItem exit;
    private JMenu edit;
    private JRadioButtonMenuItem editSetRandomPlayMode;
    private JMenu help;
    private JMenuItem helpAbout;
    
    public Menu()
    {
        super();
        
        //File Menu
        file = new JMenu("File");
        fileAddFilePlayList = new JMenuItem("Add File to Playlist...");
        fileAddFilePlayList.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent event)
        	{
        		showAddFileDialog();
        	}
        });
        
        file.add(fileAddFilePlayList);
        fileAddFolderPlayList = new JMenuItem("Add Folder to Playlist...");
        fileAddFolderPlayList.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent event)
        	{
        		showAddFolderDialog();
        	}
        });
        
        file.add(fileAddFolderPlayList);
        fileRemoveAllFilesPlayList = new JMenuItem("Remove all Files from Playlist");
        fileRemoveAllFilesPlayList.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent event)
        	{
        		Global.playList.removeAllElements();
        	}
        });
        
        file.add(fileRemoveAllFilesPlayList);
        file.add(new JSeparator());
        exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent event)
        	{
        		System.exit(0);
        	}
        });
        
        file.add(exit);
        add(file);
        
        //Edit Menu
        edit = new JMenu("Edit");       
        editSetRandomPlayMode = new JRadioButtonMenuItem("Random Playback");
        editSetRandomPlayMode.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent event)
        	{
        		switchPlayMode();
        	}
        });
        edit.add(editSetRandomPlayMode);        
        add(edit);
        
        //Help Menu
        help = new JMenu("Help");
        helpAbout = new JMenuItem("About");
        helpAbout.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		showAbout();
        	}
        });
        help.add(helpAbout);
        add(help);
    }
    
    private void showAddFileDialog()
    {
    	final Object answer = JOptionPane.showInputDialog(null, "Input path:", "Add File",
                JOptionPane.INFORMATION_MESSAGE); 
		if(answer instanceof String)
		{
			try
			{
				new Thread()
				{
					public void run()
					{
						Global.fileCrawler.addToPlayList(new File((String)answer));
					}
				}.start();
			}
			catch(Exception e)
			{
			}
		}
    }
    
    private void showAddFolderDialog()
    {
    	final Object answer = JOptionPane.showInputDialog(null, "Input path:", "Add Folder",
                JOptionPane.INFORMATION_MESSAGE); 
		if(answer instanceof String)
		{
			try
			{
				new Thread()
				{
					public void run()
					{
						Global.fileCrawler.addToPlayList(new File((String)answer));
					}
				}.start();        				
			}
			catch(Exception e)
			{
			}
		}
    }
    
    private void switchPlayMode()
    {
    	if(Global.player.getPlayMode()==Player.normalPlayback)
		{
			Global.player.setPlayMode(Player.randomPlayback);
		}
		else
		{
			Global.player.setPlayMode(Player.normalPlayback);
		}
    }
    
    private void showAbout()
    {
    	JOptionPane.showMessageDialog(null, 
				"Author: Martin Braun \n" +
				"Website: www.puremp3.co.de \n" +
				"Released under GPLv3 License \n" +
				"Some Parts are under different Licences \n" +
				"See readme.txt for further information");
    }
}
