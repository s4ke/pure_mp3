package pure_mp3;

import javax.sound.sampled.AudioFormat;

public interface MusicPlayer {
	public void start();
	public void pause();
	public void stop_();
	public void seek(int percentage);
	public long getMicrosecondPosition();
	public boolean isStopped();
	public void setVolume(float xVolume);
	public AudioFormat getAudioFormat();
	public int getFramePosition();
	public int getFrameLength();
	public int getDurationInSeconds();
//	public long getPosition();
	public Song getCurrentSong();
}
