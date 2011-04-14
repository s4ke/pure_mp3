package pure_mp3;

/**
 * Write a description of class Info here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.Color;
import java.awt.dnd.DropTarget;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
public class Info extends JPanel
{
   private static final long serialVersionUID = 2385007980763532219L;
   private JLabel artist_l;
   private JLabel artist_r;
   private JLabel title_l;
   private JLabel title_r;
   private JLabel album_l;
   private JLabel album_r;
   private JLabel length_l;
   private JLabel length_r;
   public Info()
   {
       super();
       setBackground(Color.WHITE);
       setLayout(new MigLayout("","[fill][grow]",""));       
       Global.setInfo(this);
       
       artist_l = new JLabel("Artist:",JLabel.LEFT);
       add(artist_l);
       
       artist_r = new JLabel("",JLabel.LEFT);
       add(artist_r,"wrap");
       
       title_l = new JLabel("Title:",JLabel.LEFT);
       add(title_l);
       
       title_r = new JLabel("",JLabel.LEFT);
       add(title_r,"wrap");
       
       album_l = new JLabel("Album:",JLabel.LEFT);
       add(album_l);
       
       album_r = new JLabel("",JLabel.LEFT);
       add(album_r,"wrap");
       
       length_l = new JLabel("Length:",JLabel.LEFT);
       add(length_l);
       
       length_r = new JLabel("",JLabel.LEFT);
       add(length_r,"wrap");
       
       setDropTarget(new DropTarget(this,new PlayListDropTargetListener()));
   }
   
   public void update()
   {
       artist_r.setText(Global.playList.getArtist());
       title_r.setText(Global.playList.getTitle());
       album_r.setText(Global.playList.getAlbum());
       length_r.setText(Global.playList.getLength());
   }
   
}
