package de.hotware.puremp3.console;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import de.hotware.hotsound.audio.player.ISong;
import de.hotware.hotsound.audio.player.StreamMusicPlayer;
import de.hotware.hotsound.audio.player.StreamPlayerRunnable.IPlayerRunnableListener;

public class ListStreamMusicPlayer extends StreamMusicPlayer implements
		IListMusicPlayer {

	protected List<ISong> mSongs;
	protected int mCurrent;
	private ReentrantLock mLock;

	public ListStreamMusicPlayer(IPlayerRunnableListener pPlayerThreadListener) {
		super(pPlayerThreadListener);
		this.mSongs = new ArrayList<ISong>();
		this.mCurrent = 0;
		this.mLock = new ReentrantLock();
	}

	@Override
	public void setPlaylist(List<ISong> pPlaylist) throws SongInsertionException {
		this.mLock.lock();
		try {
			this.mSongs = pPlaylist;
			this.mCurrent = 0;
			if(this.mSongs.size() != 0) {
				super.insert(this.mSongs.get(this.mCurrent));
			}
		} finally {
			this.mLock.unlock();
		}
	}

	public List<ISong> getPlayList(List<ISong> pPlaylist) {
		this.mLock.lock();
		try {
			return this.mSongs;
		} finally {
			this.mLock.unlock();
		}
	}

	@Override
	public void insert(ISong pSong) throws SongInsertionException {
		this.mLock.lock();
		try {
			if(this.mPlayerRunnable == null || this.mPlayerRunnable.isStopped()) {
				super.insert(pSong);
			}
			this.mSongs.add(pSong);
		} finally {
			this.mLock.unlock();
		}
	}

	@Override
	public void stopPlayback() {
		this.mLock.lock();
		try {
			super.stopPlayback();
			this.mSongs = new ArrayList<ISong>();
		} finally {
			this.mLock.unlock();
		}
	}

	@Override
	public void next() throws SongInsertionException {
		this.mLock.lock();
		try {
			if(this.mCurrent == this.mSongs.size() - 1) {
				this.mCurrent = 0;
			} else {
				this.mCurrent++;
			}
			super.insert(this.mSongs.get(this.mCurrent));
		} finally {
			this.mLock.unlock();
		}
	}

	@Override
	public void previous() throws SongInsertionException {
		this.mLock.lock();
		try {
			if(this.mCurrent == 0) {
				this.mCurrent = this.mSongs.size() - 1;
			} else {
				this.mCurrent--;
			}
			super.insert(this.mSongs.get(this.mCurrent));
		} finally {
			this.mLock.unlock();
		}
	}

	@Override
	public void play(int pX) throws SongInsertionException {
		this.mLock.lock();
		try {
			int size = this.mSongs.size();
			if(pX < 0 || pX >= size || size == 0) {
				throw new IllegalArgumentException("Song-Index not available");
			}
			this.mCurrent = pX;
			super.insert(this.mSongs.get(pX));
			super.startPlayback();
		} finally {
			this.mLock.unlock();
		}
	}

	@Override
	public void insertAt(int pX) throws SongInsertionException {
		throw new UnsupportedOperationException("not implemented, yet");
	}

	@Override
	public void removeAt(int pX) {
		this.mLock.lock();
		try {
			if(pX < 0 || pX >= this.mSongs.size()) {
				throw new IllegalArgumentException("Song-Index not available");
			}
			this.mSongs.remove(pX);
			if(pX <= this.mCurrent && this.mCurrent != 0) {
				this.mCurrent--;
			}
		} finally {
			this.mLock.unlock();
		}
	}

	@Override
	public int getCurrent() {
		this.mLock.lock();
		try {
			return this.mCurrent;
		} finally {
			this.mLock.unlock();
		}
	}

	@Override
	public int size() {
		this.mLock.lock();
		try {
			return this.mSongs.size();
		} finally {
			this.mLock.unlock();
		}
	}

}
