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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.miginfocom.swing.MigLayout;

public class PlayerMenu extends JPanel
{
	private static final long serialVersionUID = 2385007980763532219L;
    private JButton prev;
    private JButton play;
    private JButton next;
    private JSlider volume;
    
    public PlayerMenu()
    {
        super();
        setLayout(new MigLayout("insets 0 0 0 0"));
        
        prev = new JButton("<|");
        prev.addActionListener(new ActionListener()
        {               
            public void actionPerformed(ActionEvent e)
            {
                playPrev();
            }
        });
        add(prev,"hmax 30, sizegroup playerMenu, dock center");
        
        play = new JButton(">");
        play.addActionListener(new ActionListener()
        {               
            public void actionPerformed(ActionEvent e)
            {
                play();
            }
        });
        add(play,"hmax 30, sizegroup playerMenu, dock center");
        
        //TODO: PAUSE-BUTTON
        
        next = new JButton("|>");
        next.addActionListener(new ActionListener()
        {               
            public void actionPerformed(ActionEvent e)
            {
                playNext();
            }
        });
        add(next,"hmax 30, sizegroup playerMenu, dock center");
        
        volume = new JSlider();
        volume.setOrientation(JSlider.VERTICAL);
        volume.setMinimum(0);
        volume.setMaximum(100);
        volume.setValue(100);
        volume.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();
                source.setValueIsAdjusting(false);
                changeVolume((int)source.getValue());
            }
        });
        add(volume,"h 75!, dock center");
    }
    
    public void playPrev()
    {
    	Global.player.playPrev();
    }
    
    public void play()
    {
    	new Thread()
    	{
    		public void run()
    		{
    			Global.player.playpause(true);
    		}
    	}.start();        
    }
    
    public void playNext()
    {
    	Global.player.playNext();        
    }
    
    public void changeVolume(int xVolume)
    {
    	Global.player.changeVolume(xVolume);
    }
}
