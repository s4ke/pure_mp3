/*
 *  This file is part of pure.mp3.
 *
 *  pure.mp3 is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  pure.mp3 is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with pure.mp3.  If not, see <http://www.gnu.org/licenses/>.
 */
package pure_mp3;

import java.awt.Color;
import java.awt.dnd.DropTarget;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * PlayList for the Player. Is not a JList but a JScrollPane
 * @author Martin Braun
*/
@Singleton
public class PlayList extends JScrollPane
{
	private static final long serialVersionUID = 2385007980763532219L;
	private WiringControl wiringControl;
    private final JList list;
    private final MyListModel model;
    
    /**
     * Basic Constructor
     */
    @Inject
    public PlayList(PlayListDropTargetListener playListDropTargetListener, MyListModel xModel)
    {
        super();
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        list = new JList();
        model = xModel;
        
        list.setModel(model);
        list.setDragEnabled(true);
        list.setDropMode(DropMode.INSERT);
        list.setTransferHandler(Global.injector.getInstance(ListMoveTransferHandler.class));
        list.setBackground(Color.WHITE);
        list.setCellRenderer(new PlayListRenderer());
        
        list.addMouseListener(new MouseAdapter() 
        {
            public synchronized void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2)
                {
                	try
                	{
                		playSelected(((JList)e.getSource()).locationToIndex(e.getPoint()));
                	}
                	catch(ArrayIndexOutOfBoundsException ex)
                	{
                	}
                }
             }
        });
        
        list.addKeyListener(new KeyListener() 
        {
        	public void keyPressed(KeyEvent event)
        	{
        		int selectAfter = 0;
        		if(event.getKeyCode() == KeyEvent.VK_DELETE)
        		{
        			int selected[] = list.getSelectedIndices();
        			if(selected.length >= 0)
        			{
        				selectAfter = selected[0];
        				if(selectAfter < 0)
            			{
            				selectAfter = 0;
            			}
        				model.trimToSize();
        			}
        			int killCount = 0;
        			for(int i = 0; i < selected.length; i++)
        			{
	        			if(selected[i] == model.getCurrent() )
	        			{
	        				prev();
	        			}
	        			if(selected[i] != -1)
	        			{
	        				model.remove(selected[i] - killCount);
	        				killCount++;	        				
	        			}	        			
        			}
        			list.setSelectedIndex(selectAfter);
        			if(list.isSelectionEmpty())
        			{
        				list.setSelectedIndex(0);
        			}
        			list.ensureIndexIsVisible(list.getSelectedIndex());
        			if(model.getCurrent() > selected[selected.length-1])
        			{
        				model.setCurrent(model.getCurrent() - selected[selected.length-1]);
        			}
        		}
        	}  
        	
        	public void keyReleased(KeyEvent event)
        	{
        		//Do nothing.
        	}  
        	
        	public void keyTyped(KeyEvent event)
        	{
        		//Do nothing.
        	}
        });
        
        list.setDropTarget(new DropTarget(list, playListDropTargetListener));

        list.setSelectedIndex(model.getCurrent());
        add(list);        
        setViewportView(list);
    }    
    
    //Methods that control the oder of Playback
    /**
     * chooses the next Song
     */
    public void next()
    {
        setCurrentAndDisplay(model.getCurrent()+1);
    }
    
    /**
     * chooses the previous Song
     */
    public void prev()
    {
        setCurrentAndDisplay(model.getCurrent()-1);
    }
    
    /**
     * chooses a random Song
     */
    public void random()
    {
    	Random random = new Random();
    	if(model.getSize() > 0)
    	{
    		setCurrentAndDisplay(random.nextInt(model.getSize())-1);
    	}
    }
    //End of the Playback Control Methods
    
    /**
     * Method used by a Internal Class for Playing the current Song    
     * @param index
     */
    private void playSelected(int index)
    {
        setCurrentAndDisplay(index);
        wiringControl.playerStop();
        wiringControl.playerPlaypause(false);
    }
        
    //Methods that check if the current Song is correct    
    /**
     * checks wheter the index of the current song isn't negative
     */
    public void checkCurrentNegative()
    {
    	if(model.getCurrent() == -1 && model.getSize() > 0)
    	{
    		next();
    	}
    }    
    //End of checking Methods
    
    //Methods for adding or removing Songs
    /**
     * adds a Song at the end. After adding the List has to be validated
     * @param song
     */
    public void addSong(final Song song)
    {
    	SwingUtilities.invokeLater(new Runnable()
		{
		    public void run()
		    {
		    	System.out.println(song.getSource().toString());
		    	model.ensureCapacity(model.getSize()+1);
		    	if(song != null)
		    	{
		    		model.addElement(song);
		    	}
		    }
		});
    }
    
    /**
     * Adds Song at the specified position. After adding, the list has to be validated
     * @param song
     * @param position
     */
    public void addSongAt(final Song song, final int position)
    {
    	SwingUtilities.invokeLater(new Runnable()
		{
		    public void run()
		    {
		    	System.out.println(song.getSource().toString() + " at " + position);
		    	model.ensureCapacity(model.getSize()+1);
		    	if(song != null)
		    	{
		    		model.add(position,song);
		    	}
		    }
		});
    }
    
    /**
     * Clears the PlayList from all Songs
     */
    public void removeAllElements()
    {
    	SwingUtilities.invokeLater(new Runnable()
    	{
    		public void run()
    		{
    			model.removeAllElements();
    	    	model.trimToSize();
    	    	model.setCurrent(-1);
    	    	repaint(getViewport().getViewRect());
    		}
    	});    	
    }   
    //End of the Adding/Removing Methods
    
    //The Setter Methods:
    /**
     * @param xCurrent the index of the Song that the currentSong will be set to
     */
    public void setCurrent(int xCurrent)
    {
    	if(wiringControl.playerIsPlaying())
    	{
    		model.setCurrent(xCurrent);
    	}
    }
    
    /**
     * sets the chosen Song
     * @param xCurrent
     */
    public void setCurrentAndDisplay(final int xCurrent)
    {
    	if(model.getSize() > 0)
    	{
	    	if(xCurrent < 0)
	        {
	           model.setCurrent(model.getSize()-1);
	        }
	        else if(xCurrent+1 <= model.getSize() && xCurrent > 0)
	        {
	            model.setCurrent(xCurrent);
	        }
	        else
	        {
	            model.setCurrent(0);
	        }
	    	SwingUtilities.invokeLater(new Runnable()
	    	{
	    		public void run()
	    		{
	    			if(model.getSize() > 0)
	    			{
				        if(model.get(model.getCurrent())!=null)
				        {
				            list.setSelectedIndex(model.getCurrent());
				            list.ensureIndexIsVisible(model.getCurrent());	
				        }
				        invalidate();
				        validate();
		    			repaint(getViewport().getViewRect());
	    			}
	    		}
	    	});   
    	}
    }
    
    public void setWiringControl(WiringControl xWiringControl)
    {
    	wiringControl = xWiringControl;
    }
    //End of the Setter Methods
    
    //The Getter Methods:
    /**
     * @return the JList in PlayList
     */
    public JList getList()
    {
    	return list;
    }
    
    /**
     * @return the Model of the JList
     */
    public DefaultListModel getModel()
    {
    	return model;
    }
    
    /**
     * @return the current Song
     */
    public Song getCurrentSong()
    {
    	return model.getCurrentSong();
    }
    
    /**
     * returns the size of the PlayList, 
     * not named size() because of possibility of confusion with getSize() 
     * from JComponent
     * @return number of songs in the PlayList
     */
    public int getNumberOfSongs()
    {
    	return model.getSize();
    }
    
    /**
     * returns the index of the chosen Song
     * @return the index of the current Song
     */
    public int getCurrent()
    {
        return model.getCurrent();
    }
    //End of the Getter Methods
}
