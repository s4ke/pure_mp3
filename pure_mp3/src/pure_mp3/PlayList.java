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

/**
 * PlayList for the Player. Is not a JList but a JScrollPane
 * @author Martin Braun
*/
public class PlayList extends JScrollPane
{
	private static final long serialVersionUID = 2385007980763532219L;
    private JList list;
    private DropTarget dropTarget;
    private DefaultListModel model;
    private int current;
    
    /**
     * BAsic Constructor
     */
    public PlayList()
    {
        super();
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        Global.setPlayList(this);
        current = 0;        
        list = new JList();
//        list.setLocation(0,0);
        model = new DefaultListModel();
        
        list.setModel(model);
        list.setDragEnabled(true);
        list.setDropMode(DropMode.INSERT);
        list.setTransferHandler(new ListMoveTransferHandler());
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
	        			if(selected[i] == current && Global.player.isPlaying())
	        			{
	        				prev();
	//        				boolean paused = Global.player.isPaused();
	//        				Global.player.stop();
	//        				next();
	//        				Global.player.playpause();
	//        				if(paused)
	//        				{
	//        					Global.player.playpause();
	//        				}
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
        			if(current > selected[selected.length-1])
        			{
        				current = current - selected[selected.length-1];
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
        
        list.setDropTarget(new DropTarget(list,new PlayListDropTargetListener()));

        list.setSelectedIndex(current);
        add(list);        
        setViewportView(list);
    }    
    
    /**
     * chooses the next Song
     */
    public void next()
    {
        setCurrent(current+1);
    }
    
    /**
     * chooses the previous Song
     */
    public void prev()
    {
        setCurrent(current-1);
    }
    
    /**
     * chooses a random Song
     */
    public void random()
    {
    	Random random = new Random();
    	if(model.getSize() > 0)
    	{
    		setCurrent(random.nextInt(model.getSize())-1);
    	}
    }
    
    /**
     * Method used by a Internal Class for Playing the current Song    
     * @param index
     */
    private void playSelected(int index)
    {
        setCurrent(index);
        Global.player.stop();
        Global.player.playpause(false);
    }
    
    /**
     * sets the chosen Song
     * @param xCurrent
     */
    public void setCurrent(int xCurrent)
    {
    	if(model.getSize() > 0)
    	{
	        if(xCurrent < 0)
	        {
	            current = model.getSize()-1;
	        }
	        else if(xCurrent+1 <= model.getSize() && xCurrent > 0)
	        {
	            current = xCurrent;
	        }
	        else
	        {
	            current = 0;
	        }
	        if(model.get(current)!=null)
	        {
	            list.setSelectedIndex(current);
	            list.ensureIndexIsVisible(current);	
	        }
    	}
    	list.repaint(getViewport().getViewRect());
    }
    
    /**
     * Sets wheter a DropTarget has to listen
     * @param isActive
     */
    public void setDropTargetActive(boolean isActive)
    {
    	dropTarget.setActive(isActive);
    }
    
    /**
     * returns the index of the chosen Song
     * @return the index of the current Song
     */
    public int getCurrent()
    {
        return current;
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
     * Checks wheter the chosen Song is correct
     * @param xCurrent the index of the Song that should be the current Song
     */
    public void checkCurrent(int xCurrent)
    {
    	System.out.println("checking Current");
    	Song currentSong = Global.player.getCurrentSong();
    	if(this.getCurrentSong().getSource()!=currentSong.getSource() && Global.player.isPlaying())
    	{
    		setCurrent(xCurrent);
    	}
    }
    
    /**
     * checks wheter the index of the current song isn't negative
     */
    public void checkCurrentNegative()
    {
    	if(current == -1 && model.getSize() > 0)
    	{
    		next();
    	}
    }
    
    /**
     * @return the current Song
     */
    public Song getCurrentSong()
    {
    	if(model.getSize() > 0)
    	{
    		return (Song)model.get(current);
    	}
    	return null;
    }
    
    /**
     * adds a Song at the end
     * @param song
     */
    public void addSong(Song song)
    {
    	System.out.println(song.getSource().toString());
    	model.ensureCapacity(model.getSize()+1);
    	if(song != null)
    	{
    		model.addElement(song);
    	}
    }
    
    /**
     * Adds Song at the specified position
     * @param song
     * @param position
     */
    public void addSongAt(Song song, int position)
    {
    	System.out.println(song.getSource().toString() + " at " + position);
    	model.ensureCapacity(model.getSize()+1);
    	if(song != null)
    	{
    		model.add(position,song);
    	}
    }
    
    /**
     * 
     */
    public void revalidateList()
    {
    	list.revalidate();
    }
    
    /**
     * Clears the PlayList from all Songs
     */
    public void removeAllElements()
    {
    	model.removeAllElements();
    	model.trimToSize();
    	current = -1;
    }
    
    /**
     * returns the Model of the JList in the PlayList
     * @return the Model of the JList
     */
    public DefaultListModel getModel()
    {
    	return model;
    }
    
    /**
     * 
     * @return the JList in PlayList
     */
    public JList getList()
    {
    	return list;
    }
}
