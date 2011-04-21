package pure_mp3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class Launch 
{
	private static GUI gui;
	
	//Let's throw any Exceptions that happen, normally no Exception will ever be thrown.
	public static void main (String args[]) throws Exception
    {
    	boolean support = false;
		System.out.println("Available Mixers:");
		//Let's look, if there are Mixers available
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
				//Oh a Mixer that doesn't like SourceDataLines. Tell that the cli
				System.out.println("Not supported: " + aInfos[i].getName());
			}
		}
		if (aInfos.length == 0)
		{
			System.out.println("[No mixers available]");
			System.exit(-1);
		}
		//Not supported?
		if(!support)
		{
			JOptionPane.showMessageDialog(null,"Sound playback not supported properly!");
		}
		// if everything has worked so far, start executing
		else
		{
			//but first choose the LaF
			File config = new File("puremp3","config.txt");
			//The predefined Choices for the LaF
			Object choices[] = {"Metal","Nimbus"};			
	 		System.out.println(System.getProperty("os.name"));
	 		if(config.exists())
	 		{
	 			boolean success = false;
	 			//read in the config file and see what it says
	 			BufferedReader buffread = new BufferedReader(new FileReader(config));
	 			//Read in the first line
	 			String laf = buffread.readLine();
	 			//if Metal is he LaF
	 			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
    		    {
    		        if (laf.equals(info.getName())) 
    		        {
    		        	//set the LaF
    		            UIManager.setLookAndFeel(info.getClassName());
    		            //Everything worked fine
    		            success = true;
    		            break;
    		        }
    		    }
	        	if(!success)
	            {
	        		JOptionPane.showMessageDialog(null,"The Default LaF has been chosen, because your System doesn't support your choice.\nPlease update your config.txt");
	        	}
	 		}
	 		else
	 		{
	 			//first we need a PrintWriter variable.
	 			PrintWriter writer = null;
	 			//Let the user decide what he wants
	 			int answer = JOptionPane.showOptionDialog(
	 					null,
	 					"Do you want to use Metal or Nimbus?", 
	 					"Which Look and Feel do you want?" , 
	 					JOptionPane.DEFAULT_OPTION, 
	 					JOptionPane.QUESTION_MESSAGE, 
	 					null, 
	 					choices,
	 					choices[0]);
	 			try
	 			{
	 				//Create the directory for the config if it doesn't exist, yet
	 				new File("puremp3").mkdir();
	 				//initializing the PrintWriter
	 				writer = new PrintWriter(new FileWriter(config));
	 			}
	 			catch(Exception ex)
	 			{
	 				System.out.println("Failed Creating the PrintWriter");
	 			}
	        	if(answer == 0 || answer == 1)
	        	{
	        		//Initialize the variable laf;
	        		String laf = "";
	        		if(answer == 0)
		        	{
		        		laf = "Metal";
		        	}
		        	else
		        	{
		        		laf = "Nimbus";
		        	}
		        	for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
	    		    {
	    		        if (laf.equals(info.getName())) 
	    		        {
	    		        	//Set the LaF according to the User
	    		            UIManager.setLookAndFeel(info.getClassName());
	    		            //And write it into the config
	    		            writer.println("Metal");
		    	        	writer.flush();
		    	        	writer.close();
	    		            break;
	    		        }
	    		    }
	        	}
	        	else
	        	{
	        		JOptionPane.showMessageDialog(null,"The Default LaF has been chosen, because your System doesn't support your choice.\nPlease update your config.txt");
	        	}
    	    } 
	 		gui = new GUI();
		}
	}

}
