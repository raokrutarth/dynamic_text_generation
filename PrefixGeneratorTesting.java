
public class PrefixGeneratorTesting 
{

	public static void main(String[] args) 
	{
		//String[] oldPrefix = {"I" , "am", "not"};
		//String suffix = "theNewOne";
		//String[] newPrefixArray = PrefixGenerator.newPrefixGenerator(oldPrefix, suffix);  //getting the new prefix
		//System.out.println(newPrefixArray[0] + " " + newPrefixArray[1] + " " + newPrefixArray[2]);
		
		StringArrayMap map1 = new StringArrayMap();
		PrefixGenerator.trainPrefixMap(map1, "hamlet.txt");
		//PrefixGenerator.trainPrefixMap(map1, "a.txt");
		//PrefixGenerator.trainPrefixMap(map1, "b.txt");
		//PrefixGenerator.trainPrefixMap(map1, "tgt.txt");		
		map1.printMap();
		
	}

}
