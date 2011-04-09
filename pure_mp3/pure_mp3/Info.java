package pure_mp3;

/**
 * Write a description of class Info here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
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
       setLayout(null);       
       Global.setInfo(this);
       
       artist_l = new JLabel("Artist:",JLabel.LEFT);
       artist_l.setBounds(5,0,55,20);
       add(artist_l);
       
       artist_r = new JLabel("",JLabel.LEFT);
       artist_r.setBounds(60,0,225,20);
       add(artist_r);
       
       title_l = new JLabel("Title:",JLabel.LEFT);
       title_l.setBounds(5,20,55,20);
       add(title_l);
       
       title_r = new JLabel("",JLabel.LEFT);
       title_r.setBounds(60,20,225,20);
       add(title_r);
       
       album_l = new JLabel("Album:",JLabel.LEFT);
       album_l.setBounds(5,40,55,20);
       add(album_l);
       
       album_r = new JLabel("",JLabel.LEFT);
       album_r.setBounds(60,40,225,20);
       add(album_r);
       
       length_l = new JLabel("Length:",JLabel.LEFT);
       length_l.setBounds(5,60,55,20);
       add(length_l);
       
       length_r = new JLabel("",JLabel.LEFT);
       length_r.setBounds(60,60,225,20);
       add(length_r);      
   }
   
   public void update()
   {
       artist_r.setText(Global.playList.getArtist());
       title_r.setText(Global.playList.getTitle());
       album_r.setText(Global.playList.getAlbum());
       length_r.setText(Global.playList.getLength());
   }
   
}
