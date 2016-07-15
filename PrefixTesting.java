
public class PrefixTesting 
{

	public static void main(String[] args) 
	{
		Prefix.initializeSentenceStartArray();
		String[] startPrefixArray = Prefix.getStartOfSentencePrefixes();
		String[] PrefixArray1 = {"art","tho", "way"};
		
		Prefix prefix1 = new Prefix(startPrefixArray);
		
		Prefix prefix2 = new Prefix( PrefixArray1);
		
		prefix1.addSuffix("One");
		prefix1.addSuffix("The");
		prefix1.addSuffix("You");
		prefix1.addSuffix("I");
		
		prefix2.addSuffix("my");
		prefix2.addSuffix("your");
		prefix2.addSuffix("nay");
		prefix2.addSuffix(null);
		
		//System.out.println(prefix1.toString());
		//System.out.println(prefix2.toString());
		System.out.println(prefix2.getNumSuffixes());
		
		//System.out.println(prefix2.getSuffixString(3));		
		

	}

}
