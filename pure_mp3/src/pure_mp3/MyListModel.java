package pure_mp3;

import javax.swing.DefaultListModel;

public class MyListModel extends DefaultListModel implements Cloneable{
	
	private static final long serialVersionUID = 2385007980763532219L;
	private PlayList playList;
	
	public MyListModel(PlayList xPlayList)
	{
		playList = xPlayList;
	}
	
	public void checkCurrent(int xCurrent)
	{
		playList.checkCurrent(xCurrent);
	}
	
	public MyListModel clone()
	{
		try 
		{
			return (MyListModel) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
