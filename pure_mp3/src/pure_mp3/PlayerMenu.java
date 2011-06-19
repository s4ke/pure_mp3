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
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.miginfocom.swing.MigLayout;

/**
 * PlayerMenu for the Player
 * @author Martin Braun
 */
@Singleton
public class PlayerMenu extends JPanel
{
	private static final long serialVersionUID = 2385007980763532219L;
	private WiringControl wiringControl;
    private final JButton prev;
    private final JButton play;
    private final JButton next;
    private final JSlider volume;
    
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
    
    public void setWiringControl(WiringControl xWiringControl)
    {
    	wiringControl = xWiringControl;
    }
    
    public void playPrev()
    {
    	wiringControl.playerPlayPrev();
    }
    
    public void play()
    {
    	new Thread()
    	{
    		public void run()
    		{
    			wiringControl.playerPlaypause(true);
    		}
    	}.start();        
    }
    
    public void playNext()
    {
    	wiringControl.playerPlayNext();        
    }
    
    public void changeVolume(int xVolume)
    {
    	wiringControl.playerChangeVolume(xVolume);
    }
}
