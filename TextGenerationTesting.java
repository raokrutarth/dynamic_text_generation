
public class TextGenerationTesting 
{

	public static void main(String[] args) 
	{
		System.out.println(TextGenerationEngine.shouldTerminate("palas.!dgdf."));
		//System.out.println(TextGenerationEngine.capitalize("hat"));		
		
		StringArrayMap map1 = new StringArrayMap();
		PrefixGenerator.trainPrefixMap(map1, "hamlet.txt");
		String result = TextGenerationEngine.generateSentence(map1);
		//System.out.printf("%s\n\n", result);
	}
}
