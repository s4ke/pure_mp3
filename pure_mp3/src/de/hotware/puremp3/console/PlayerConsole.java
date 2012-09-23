package de.hotware.puremp3.console;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import de.hotware.hotmisc.audio.player.BaseSong;
import de.hotware.hotmisc.audio.player.IMusicPlayer;
import de.hotware.hotmisc.audio.player.IMusicPlayer.SongInsertionException;
import de.hotware.hotmisc.audio.player.StreamMusicPlayer;
import de.hotware.puremp3.console.ICommand.ExecutionException;
import de.hotware.puremp3.console.ICommand.UsageException;


public class PlayerConsole implements Runnable {
	
	private IMusicPlayer mMusicPlayer;
	private PrintStream mPrintStream;
	private Map<String, ICommand> mCommands;
	private Scanner mScanner;
	private Pattern mSplitPattern;
	
	public PlayerConsole(PrintStream pPrintStream, InputStream pInputStream) {
		this.mPrintStream = pPrintStream;
		this.mCommands = new HashMap<String, ICommand>();
		this.mScanner = new Scanner(pInputStream);
		for(BasicCommand cmd : BasicCommand.values()) {
			for(String key : cmd.mKeys) {
				if(this.mCommands.containsKey(key)) {
					throw new IllegalStateException("command key was being used twice");
				}
				this.mCommands.put(key, cmd);
			}
			cmd.setConsole(this);
		}
		this.mSplitPattern = Pattern.compile("\\s");
	}
	
	@Override
	public void run() {
		while(true) {
			String input = this.mScanner.nextLine();
			String[] args = this.mSplitPattern.split(input);
			ICommand cmd = this.mCommands.get(args[0]);
			try {
				if(cmd != null) {
					cmd.execute(args);
				} else {
					BasicCommand.HELP.execute(new String[]{});
				}
			} catch(UsageException e) {
				this.mPrintStream.println(e.getCommand().usage());
			} catch(ExecutionException | RuntimeException e) {
				e.printStackTrace(this.mPrintStream);
			}
		}
	}
	
	/**
	 * TODO: usage
	 */
	public enum BasicCommand implements ICommand {
		PLAY("play") {
			
			@Override
			public void execute(String... pArgs) throws UsageException, ExecutionException {
				if(pArgs.length < 1) {
					throw new UsageException("play was used in a wrong way", this);
				}
				if(this.mConsole.mMusicPlayer == null) {
					this.mConsole.mMusicPlayer = new StreamMusicPlayer();
				}
				if(pArgs.length == 1) {
					this.mConsole.mMusicPlayer.unpausePlayback();
				} else {
					String insertionString = "";
					if(pArgs[1].equals("-url")) {
						if(pArgs.length < 3) {
							throw new UsageException("play was used in a wrong way", this);
						}
						insertionString = pArgs[2];
					} else {
						insertionString = "file:" + pArgs[1];
					}
					try {
						this.mConsole.mMusicPlayer.insert(new BaseSong(new URL(insertionString)));
					} catch(MalformedURLException | SongInsertionException e) {
						throw new ExecutionException(e, this);
					}
					this.mConsole.mMusicPlayer.startPlayback();
				}
			}
			
		},
		PAUSE("pause") {
			
			@Override
			public void execute(String... pArgs) {
				this.mConsole.mMusicPlayer.pausePlayback();
			}
			
		},
		STOP("stop") {
			
			@Override
			public void execute(String... pArgs) {
				this.mConsole.mMusicPlayer.stopPlayback();
			}
			
		},
		EXIT("exit") {
			
			@Override
			public void execute(String... pArgs) {
				System.exit(1);
			}
			
		},
		EMPTY("") {
			
			@Override
			public void execute(String... pArgs) {
				
			}
			
		},
		HELP("help") {
			
			@Override
			public void execute(String... pArgs) {
				
			}
			
		};
		
		protected String[] mKeys;
		protected PlayerConsole mConsole;
		
		private BasicCommand(String... pKeys) {
			this.mKeys = pKeys;
		}
		
		public void setConsole(PlayerConsole pConsole) {
			this.mConsole = pConsole;
		}
		
		@Override
		public String usage() {
			return "lol";
		}
		
	}
	
	public static void main(String args[]) {
		PlayerConsole console = new PlayerConsole(System.out, System.in);
		console.run();
	}

}
