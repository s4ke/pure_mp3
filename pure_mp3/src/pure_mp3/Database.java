package pure_mp3;

import java.io.File;
import java.io.IOException;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class Database 
{
	private File dbFile;
	
	public Database()
	{
		 dbFile = new File("songs.db");
		 if(!dbFile.exists())
		 {
			 try
				{
					SqlJetDb db = SqlJetDb.open(dbFile, true);
					db.getOptions().setAutovacuum(true);
					db.beginTransaction(SqlJetTransactionMode.WRITE);
					try 
					{
					    db.getOptions().setUserVersion(1);
					    db.createTable("CREATE TABLE songs (path TEXT NOT NULL PRIMARY KEY , artist TEXT, title TEXT, album TEXT, length TEXT)");
					} 
					finally 
					{
					    db.commit();
					    db.close();
					}
				}
				catch(SqlJetException sqle)
				{
					sqle.printStackTrace();
				}
		 }
	}
	
	public void addSongs(Song[] songs)
	{
		try
		{
			SqlJetDb db = SqlJetDb.open(dbFile, true);
			db.beginTransaction(SqlJetTransactionMode.WRITE);
			try 
			{
				ISqlJetTable table = db.getTable("songs");
				for(int i = 0; i < songs.length; i++)
				{
					table.insert(songs[i].getSource().getPath(),songs[i].getArtist(),songs[i].getTitle(),songs[i].getAlbum(),songs[i].getLength());
				} 
			}
			catch(SqlJetException sqle)
			{
			}
			finally 
			{
			    db.commit();
			    db.close();
			}
		}
		catch(SqlJetException sqle)
		{
			sqle.printStackTrace();
		}
	}
	
	public void addSong(Song song)
	{
		try
		{
			SqlJetDb db = SqlJetDb.open(dbFile, true);
			db.beginTransaction(SqlJetTransactionMode.WRITE);
			try 
			{
				ISqlJetTable table = db.getTable("songs");
				table.insert(song.getSource().getPath(),song.getArtist(),song.getTitle(),song.getAlbum(),song.getLength());
			}
			catch(SqlJetException sqle)
			{
			}
			finally 
			{
			    db.commit();
			    db.close();
			}
		}
		catch(SqlJetException sqle)
		{
			sqle.printStackTrace();
		}
	}
	
	public void removeSong(Song song)
	{
		
	}
	
	public static void main(String args[])
	{
		new Database().addSong(new Song(new File("/multimedia/groovewalrusmusik/test.mp3")));
	}
}
