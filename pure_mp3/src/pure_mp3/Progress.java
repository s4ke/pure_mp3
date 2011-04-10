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
