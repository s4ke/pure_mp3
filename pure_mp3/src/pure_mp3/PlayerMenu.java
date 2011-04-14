package pure_mp3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.miginfocom.swing.MigLayout;

public class PlayerMenu extends JPanel
{
	private static final long serialVersionUID = 2385007980763532219L;
    private JButton prev;
    private JButton play;
    private JButton next;
    private JSlider volume;
    
    public PlayerMenu()
    {
        super();
        setLayout(new MigLayout("insets 0 0 0 0"));
        
        prev = new JButton("<|");
        prev.addActionListener(new ActionListener()
        {               
            public void actionPerformed(ActionEvent e)
            {
                playPrev();
            }
        });
        add(prev,"sizegroup playerMenu");
        
        play = new JButton(">");
        play.addActionListener(new ActionListener()
        {               
            public void actionPerformed(ActionEvent e)
            {
                play();
            }
        });
        add(play,"sizegroup playerMenu");
        
        //TODO: PAUSE-BUTTON
        
        next = new JButton("|>");
        next.addActionListener(new ActionListener()
        {               
            public void actionPerformed(ActionEvent e)
            {
                playNext();
            }
        });
        add(next,"sizegroup playerMenu");
        
        volume = new JSlider();
        volume.setOrientation(JSlider.VERTICAL);
        volume.setMinimum(0);
        volume.setMaximum(100);
        volume.setValue(100);
        volume.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();
                source.setValueIsAdjusting(false);
                changeVolume((int)source.getValue());
            }
        });
        add(volume);
    }
    
    public void playPrev()
    {
    	Global.player.playPrev();
    }
    
    public void play()
    {
    	new Thread()
    	{
    		public void run()
    		{
    			Global.player.playpause(true);
    		}
    	}.start();        
    }
    
    public void playNext()
    {
    	Global.player.playNext();        
    }
    
    public void changeVolume(int xVolume)
    {
    	Global.player.changeVolume(xVolume);
    }
}
