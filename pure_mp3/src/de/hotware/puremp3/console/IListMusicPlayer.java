package de.hotware.puremp3.console;

import java.util.List;

import de.hotware.hotsound.audio.player.IMusicPlayer;
import de.hotware.hotsound.audio.player.ISong;
import de.hotware.hotsound.audio.player.IMusicPlayer.SongInsertionException;

public interface IListMusicPlayer extends IMusicPlayer {

	public void setPlaylist(List<ISong> pPlaylist) throws SongInsertionException;

	public void next() throws SongInsertionException;

	public void previous() throws SongInsertionException;

	public void play(int pX) throws SongInsertionException;

	public void insertAt(int pX) throws SongInsertionException;

	public void removeAt(int pX);

	public int getCurrent();

	public int size();

}
