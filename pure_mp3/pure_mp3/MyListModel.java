package pure_mp3;

import javax.swing.DefaultListModel;

public class MyListModel extends DefaultListModel{
	
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
}
