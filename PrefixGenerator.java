import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/**
 * CS 180 - Dynamic Generation Project
 * 
 * This class provides methods to train the Dynamic Text Generator program. This
 * class only provides static methods and should never be instantiated.
 * 
 * @author (Krutarth Rao) <raok@purdue.edu>
 * @author (Rahul Sathi) <rsathi@purdue.edu>
 * @lab (LM3)
 * 
 * @version (3/12/2015)
 *
 */


public class PrefixGenerator 
{
	public PrefixGenerator() {  }

	public static void trainPrefixMap(StringArrayMap map, String filename) 
	{		
		// Open scanner on the file
		Scanner text;
		try 
		{
			text = new Scanner(new File(filename));
		} 
		catch (IOException io) 
		{
			System.out.printf("File '%s' failed to open\n", filename);
			return;
		}
		// Assumes that the file has at least 1 word in it
		if (!text.hasNext()) 
		{
			System.out.println("File is empty");
			text.close();
			return;
		}
		if (map == null)
		{
			map = new StringArrayMap();
		}
		Prefix.initializeSentenceStartArray();
		String[] prefixStringArray = Prefix.getStartOfSentencePrefixes();

		while ( text.hasNext() )
		{
			String suffixString  = text.next().toLowerCase();			
			
			Prefix newPrefixObject = map.getPrefix(prefixStringArray);
			if ( newPrefixObject == null)
			{
				newPrefixObject = new Prefix(prefixStringArray);
			}
			
			newPrefixObject.addSuffix(suffixString);
			map.putPrefix(prefixStringArray, newPrefixObject);   
			prefixStringArray = newPrefixGenerator(prefixStringArray, suffixString);
			if (TextGenerationEngine.shouldTerminate(suffixString))
			{
				Prefix.initializeSentenceStartArray();
				prefixStringArray = Prefix.getStartOfSentencePrefixes();				
			}
		}
	}
	public static String[] newPrefixGenerator(String [] oldPrefix, String suffix)
	{
		String[] newPrefixString = new String[Prefix.prefixLength]; 

		for (int i = 0; i < Prefix.prefixLength - 1; i++)
		{
			newPrefixString[i] = oldPrefix[ i + 1 ];    
		}
		newPrefixString[Prefix.prefixLength - 1] = suffix; 
		return newPrefixString;  
	}
}
