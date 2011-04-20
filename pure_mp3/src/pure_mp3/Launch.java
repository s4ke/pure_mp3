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
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class Launch 
{
	private static GUI gui;
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
			System.exit(-1);
		}
		if(support)
		{
			try 
    	 	{
				Object choices[] = {"Metal","Nimbus"};
    	 		System.out.println(System.getProperty("os.name"));
    	 		try
    	 		{
    	 			BufferedReader buffread = new BufferedReader(new FileReader(new File("puremp3","config.txt")));
    	 			String laf = buffread.readLine();
    	 			if(laf.equals("Metal"))
    	 			{
    	 				UIManager.setLookAndFeel(new MetalLookAndFeel());
    	 			}
    	 			else if(laf.equals("Nimbus"))
    	 			{
    	 				try 
		        		{
		        		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
		        		    {
		        		        if ("Nimbus".equals(info.getName())) 
		        		        {
		        		            UIManager.setLookAndFeel(info.getClassName());
		        		            break;
		        		        }
		        		    }
		        		}
		        		catch(Exception ex)
		        		{
		        			JOptionPane.showMessageDialog(null,"Metal has been chosen, because your System doesn't support Nimbus.\nPlease update your config.txt");
		        			UIManager.setLookAndFeel(new MetalLookAndFeel());
		        		}
    	 			}
    	 			else
    	 			{
    	 				throw new Exception();
    	 			}
    	 		}
    	 		catch(Exception e)
    	 		{
    	 			PrintWriter writer = null;
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
    	 				new File("puremp3").mkdir();
    	 				writer = new PrintWriter(new FileWriter(new File("puremp3","config.txt")));
    	 			}
    	 			catch(Exception ex)
    	 			{
    	 				System.out.println("Failed saving LookAndFeel!");
    	 			}
		        	if(answer == 0)
		        	{
		    	        UIManager.setLookAndFeel(new MetalLookAndFeel());
		    	        if(writer != null)
		    	        {
		    	        	writer.println("Metal");
		    	        	writer.flush();
		    	        	writer.close();
		    	        }
		        	}
		        	else if(answer == 1)
		        	{
		        		try 
		        		{
		        		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
		        		    {
		        		        if ("Nimbus".equals(info.getName())) 
		        		        {
		        		            UIManager.setLookAndFeel(info.getClassName());
		        		            break;
		        		        }
		        		    }
		        		}
		        		catch(Exception ex)
		        		{
		        			JOptionPane.showMessageDialog(null,"Swing has been chosen, because your System doesn't support Nimbus.");
		        			UIManager.setLookAndFeel(new MetalLookAndFeel());
			    	        if(writer != null)
			    	        {
			    	        	writer.println("Metal");
			    	        	writer.flush();
			    	        	writer.close();
			    	        }
		        		}
		        		if(writer != null)
		    	        {
		    	        	writer.println("Nimbus");
		    	        	writer.flush();
		    	        	writer.close();
		    	        }
		        	}
		        	else
		        	{
		        		System.exit(-1);
		        	}
    	 		}    	 		
    	    } 
    	    catch (UnsupportedLookAndFeelException e)
    	    {
    	       System.out.println("Something is wrong with your Look and Feel!!!");
    	    }
			gui = new GUI();
		}
		else
		{
			System.out.println("Sound playback not supported properly!");
			System.exit(-1);
		}
	}

}
