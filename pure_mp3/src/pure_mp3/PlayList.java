 /**
 * @author Martin Braun
 *   
 * This file is part of pure.mp3.
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

public class PlayList extends JScrollPane
{
	private static final long serialVersionUID = 2385007980763532219L;
    private JList list;
    private DropTarget dropTarget;
    private DefaultListModel model;
    private int current;
    
    public PlayList()
    {
        super();
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        Global.setPlayList(this);
        current = 0;        
        list = new JList();
//        list.setLocation(0,0);
        model = new MyListModel(this);
        
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
        
        list.setDropTarget(new DropTarget(Global.playList.getList(),new PlayListDropTargetListener()));

        list.setSelectedIndex(current);
        add(list);        
        setViewportView(list);
    }    
    
    public void next()
    {
        setCurrent(current+1);
    }
    
    public void prev()
    {
        setCurrent(current-1);
    }
    
    public void random()
    {
    	Random random = new Random();
    	if(model.getSize() > 0)
    	{
    		setCurrent(random.nextInt(model.getSize())-1);
    	}
    }
    
    public void playSelected(int index)
    {
        setCurrent(index);
        Global.player.stop();
        Global.player.playpause(false);
    }
    
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
    
    public void setDropTargetActive(boolean isActive)
    {
    	dropTarget.setActive(isActive);
    }
    
    
    public int getCurrent()
    {
        return current;
    }
    
    public int getNumberOfSongs()
    {
    	return model.getSize();
    }
    
    public void checkCurrent(int xCurrent)
    {
    	System.out.println("checking Current");
    	Song currentSong = Global.player.getCurrentSong();
    	if(this.getCurrentSong().getSource()!=currentSong.getSource() && Global.player.isPlaying())
    	{
    		setCurrent(xCurrent);
    	}
    }
    
    public void checkCurrentNegative()
    {
    	if(current == -1 && model.getSize() > 0)
    	{
    		next();
    	}
    }
    
    public Song getCurrentSong()
    {
    	if(model.getSize() > 0)
    	{
    		return (Song)model.get(current);
    	}
    	return null;
    }
    
    public String getArtist()
    {
    	if(model.getSize() > 0)
    	{
	        if(model.get(current)!=null)
	        {
	            return ((Song)model.get(current)).getArtist();
	        }
	        else
	        {
	            return "";
	        }
    	}
    	return "";
    }
    
    public String getTitle()
    {
    	if(model.getSize() > 0)
    	{
	        if(model.get(current)!=null)
	        {
	            return ((Song)model.get(current)).getTitle();
	        }
	        else
	        {
	            return "";
	        }
    	}
    	return "";
    }
    
    public String getAlbum()
    {
    	if(model.getSize() > 0)
    	{
	        if(model.get(current)!=null)
	        {
	            return ((Song)model.get(current)).getAlbum();
	        }
	        else
	        {
	            return "";
	        }
    	}
    	return "";
    }
    
    public String getLength()
    {
    	if(model.getSize() > 0)
    	{
	        if(model.get(current)!=null)
	        {
	            return ((Song)model.get(current)).getLength();
	        }
	        else
	        {
	            return "";
	        }
    	}
    	return "";
    }
    
    public void addSong(Song song)
    {
    	System.out.println(song.getSource().toString());
    	model.ensureCapacity(model.getSize()+1);
    	if(song != null)
    	{
    		model.addElement(song);
    	}
    }
    
    public void addSongAt(Song song, int position)
    {
    	System.out.println(song.getSource().toString() + " at " + position);
    	model.ensureCapacity(model.getSize()+1);
    	if(song != null)
    	{
    		model.add(position,song);
    	}
    }
    
    public void revalidateList()
    {
    	list.revalidate();
    }
    
    public void setValueIsAdjusting(boolean isAdjusting)
    {
    	list.setValueIsAdjusting(isAdjusting);
    }
    
    public void removeAllElements()
    {
    	model.removeAllElements();
    	model.trimToSize();
    	current = -1;
    }
    
    public DefaultListModel getModel()
    {
    	return model;
    }
    
    public JList getList()
    {
    	return list;
    }
    
    public int getModelSize()
    {
    	return model.getSize();
    }
    
}
