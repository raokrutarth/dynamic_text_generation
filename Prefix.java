import java.util.Arrays;
import java.util.Random;

/**
 * CS 180 - Dynamic Generation Project
 * 
 * Prefix class that represents prefixes used in a Dynamic Text Generator. A
 * prefix can have a fixed but arbitrary number of context words.
 *
 * @author (Krutarth Rao) <raok@purdue.edu>
 * @author (Rahul Sathi) <rsathi@purdue.edu>
 * 
 * @lab (LM3)
 * 
 * @version (3/12/2015)
 *
 */
public class Prefix 
{
	private String[] prefixStrings;
	private String[] suffixStrings;	
	public static int prefixLength = 3;
	private static String[] startPrefix;

	public Prefix(String[] prefixStrings) 
	{
		this.prefixStrings = prefixStrings;
		this.suffixStrings = suffixStrings;		
	}

	public static void initializeSentenceStartArray() 
	{
		String[] emptyS = new String[prefixLength];
		Arrays.fill(emptyS, "");
		startPrefix = emptyS;
	}



	public static String[] getStartOfSentencePrefixes() 
	{	
		return Arrays.copyOf(startPrefix, startPrefix.length);
	}


	public int getNumSuffixes() 
	{
		if (suffixStrings != null)
		{
			return suffixStrings.length;		
		}
		return 0;
	}

	public int getNumPrefixes() 
	{
		if (prefixStrings != null)
		{
			return prefixStrings.length;		
		}
		return 0;

	}

	public String getPrefixString(int index) 
	{
		return prefixStrings[index];
	}


	public String getSuffixString(int index) 
	{
		return suffixStrings[index];
	}


	public String getRandomSuffix() 
	{
		if (suffixStrings != null && suffixStrings.length != 0)
		{
			Random r = new Random();
			int randomIndex = r.nextInt(suffixStrings.length);
			return suffixStrings[randomIndex];
		}
		return "";
	}


	public void addSuffix(String str) 
	{
		suffixStrings = addElement(suffixStrings, str);			
	}

	private String[] addElement(String[] oldArray , String newElement)

	{
		String[] newArray = new String[1];
		if (oldArray != null)
		{
			newArray = new String[oldArray.length + 1];

			for (int i = 0; i < oldArray.length; i++)
			{
				newArray[i] = oldArray[i];
			}
			newArray[oldArray.length] = newElement;
		}
		else
		{
			newArray[0] = newElement;		
		}
		return newArray;		
	}

	/**
	 * Determines equality among Prefix objects. Two Prefix objects are
	 * considered equal if they both have the exact same string prefixes in the
	 * same order.
	 * 
	 * @param obj
	 *            Object to determine equality against
	 */
	public boolean equals(Object newObject) 
	{		
		boolean isPrefixes = false;
		if (newObject instanceof Prefix)
		{
			Prefix pre = (Prefix) newObject;			
			isPrefixes = compareArrays(pre.prefixStrings, this.prefixStrings);			
		}
		if (isPrefixes )
		{
			return true;
		}
		return false;
	}
	public static boolean compareArrays(String [] array1, String [] array2)
	{
		if (array1.length == array2.length)
		{
			for (int i = 0; i < array1.length; i++)
			{
				if (array1[i].equals(array2[i]))
				{
					continue;
				}
				else
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}
		return true;
	}


	/**
	 * The string form of a prefix object is its list of prefixes converted to a
	 * whitespace delimited string
	 */
	public String toString() 
	{
		return arrayToString(prefixStrings);

	}
	private String arrayToString(String[] arrayS)
	{
		Prefix.initializeSentenceStartArray();
		String result = "";
		if (!compareArrays(arrayS, Prefix.getStartOfSentencePrefixes() ))
		{
			for (int i = 0 ; i < arrayS.length ; i++)
			{	
				//i == arrayS.length-1			
				result = result + arrayS[i] + " ";

			}			
		}

		return result;
	}
}
