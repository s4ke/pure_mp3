package pure_mp3;

public class Global {
	public static float VOLUME = 100;
	public static final double LINEARSCALAR = Math.log(10.0)/20;
	public static FileCrawler fileCrawler = new FileCrawler();
	public static final Player player = new Player(0);
	public static PlayList playList = null;
	public static Info info = null;
	
	public static void setVolume(float xVolume)
	{
		VOLUME = xVolume;
	}
	
	public static void setPlayList(PlayList xPlayList)
	{
		playList = xPlayList;
	}
	
	public static void setInfo(Info xInfo)
	{
		info = xInfo;
	}

}
