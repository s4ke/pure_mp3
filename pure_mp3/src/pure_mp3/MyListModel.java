package pure_mp3;

import javax.swing.DefaultListModel;

import com.google.inject.Inject;


public class MyListModel extends DefaultListModel
{
	private static final long serialVersionUID = 1L;
	private int current;
	
	@Inject
	public MyListModel()
	{
		current = -1;
	}
	
	public void setCurrent(int xCurrent)
	{
		current = xCurrent;
	}
	
	public Song getCurrentSong()
	{
		if(getSize() > 0 && getCurrent() >= 0)
    	{
    		return (Song)get(getCurrent());
    	}
		return null;
	}
	
	public int getCurrent()
	{
		return current;
	}

}
