package pure_mp3;

/**
 * Write a description of class GUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */


import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;

public class GUI extends JFrame
{
	private static final long serialVersionUID = 2385007980763532219L;
    private MainPanel mainPanel;     
    public GUI()
    {
        super("pure.mp3");
        Global.setGUI(this);
        init();
    }
    
    private void init()
    {
        SwingUtilities.invokeLater(new Runnable(){
             public void run() 
             {
                    setLayout(null);
                    setSize(800,600); 
                    mainPanel = new MainPanel();
                    mainPanel.setBounds(0,0,800,600);
                    add(mainPanel);
                    setJMenuBar(new Menu());
                    System.setProperty("apple.laf.useScreenMenuBar", "true");
                    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "pure.mp3");
//                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    setDefaultCloseOperation(EXIT_ON_CLOSE);   
                    setLocationRelativeTo(null);
                    setResizable(false);
                    setVisible(true);
                    
            }
        });
    }
    
    public static void main (String args[])
    {
    	boolean support = false;
		System.out.println("Available Mixers:");
		Mixer.Info[]	aInfos = AudioSystem.getMixerInfo();
		for (int i = 0; i < aInfos.length; i++)			
		{
			Mixer mixer = AudioSystem.getMixer(aInfos[i]);
			Line.Info lineInfo = new Line.Info(SourceDataLine.class);
			if (mixer.isLineSupported(lineInfo))
			{
				System.out.println(aInfos[i].getName());
				support = true;
			}
			else
			{
				System.out.println("Not supported: " + aInfos[i].getName());
			}
		}
		if (aInfos.length == 0)
		{
			System.out.println("[No mixers available]");
			System.exit(ERROR);
		}
		if(support)
		{
			new GUI();
		}
		else
		{
			System.out.println("Sound playback not supported properly!");
			System.exit(ERROR);
		}
    
	}
}
