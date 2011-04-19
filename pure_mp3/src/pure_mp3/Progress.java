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

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Progress extends JSlider
{
	private static final long serialVersionUID = 2385007980763532219L;
	private boolean userChanged;
	private int lastValue;
	
	public Progress()
	{
		userChanged = true;
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
        				seek(source.getValue()-lastValue);
        			}
        		}
        	}
        	
        });
	}	
	
	public synchronized void setValue2(int x)
	{
		if(!getValueIsAdjusting())
		{
			userChanged = false;
			setValue(x);
			lastValue = x;
			userChanged = true;	
		}
//		repaint();
		notify();
	}
	
	public void setValue(int x)
	{
		super.setValue(x);
	}

	private synchronized boolean getUserChanged()
	{
		return userChanged;
	}
	
	public synchronized void seek(int value)
	{
		Global.player.seek(value);
	}
}
