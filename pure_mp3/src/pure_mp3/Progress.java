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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The Slider which displays the current position in the song
 * @author Martin Braun
*/
public class Progress extends JSlider
{
	private static final long serialVersionUID = 2385007980763532219L;
	private boolean userChanged;
	private int lastValue;
	
	/**
	 * Basic Constructor
	 */
	public Progress()
	{
		super();
		userChanged = true;
		setPaintTrack(true);
		setMinimum(0);
        setMaximum(100);
        setValue(0);
        Global.player.setProgress(this);                
        addChangeListener(new ChangeListener()
        {
        	public void stateChanged(ChangeEvent e)
        	{
        		if(getUserChanged())
        		{
        			Progress source = (Progress) e.getSource();
        			if (!source.getValueIsAdjusting())
        			{
        				seek(source.getValue());
        			}
        		}
        	}
        	
        });
        addMouseListener(new MouseListener()
        {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}       	
        });
	}	
	
	/**
	 * Method that sets the value without making a Listener use the Chang
	 * @param x new Value
	 */
	public synchronized void setValue2(final int x)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				if(!getValueIsAdjusting())
				{
					userChanged = false;
					setValue(x);
					repaint();
					lastValue = x;
					userChanged = true;	
				}
			}
		});
		notify();
	}
	
	/**
	 * Returns if setValue was invoked by the user
	 * @return
	 */
	private synchronized boolean getUserChanged()
	{
		return userChanged;
	}
	
	/**
	 * Controls the seek mechanism in Player
	 * @param value
	 */
	public synchronized void seek(int value)
	{
		Global.player.seek(value);
	}
}
