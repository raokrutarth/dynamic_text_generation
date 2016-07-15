import java.io.File;
import java.util.Scanner;

/**
 * CS 180 - Dynamic Generation Project
 * 
 * This class prompts the user for an action and can dynamically generate
 * sentences if the program has already been trained.
 * 
 * @author (Krutarth Rao) <raok@purdue.edu>
 * @author (Rahul Sathi) <rsathi@purdue.edu>
 * @lab (LM3)
 * 
 * @version (3/12/2015)
 *
 */
public class TextGenerationEngine 
{
    private static String[] terminators = { ".", "!", "?" };
    private static String[] trainedTexts = new String[8];
    private static int numTextsTrained = 0;
    private static StringArrayMap map = new StringArrayMap();
    private static int numSentences = 1;
    
    
    public static void addTrainedTexts(String filename) 
    {
        trainedTexts[numTextsTrained++] = filename;
        if (numTextsTrained >= trainedTexts.length) 
        {
            String[] temp = new String[2 * trainedTexts.length];
            for (int i = 0; i < trainedTexts.length; ++i)
            {
                temp[i] = trainedTexts[i];
            }
            trainedTexts = temp;
        }
    }
    
    public static boolean haveTrainedText(String filename) 
    {
        for (int i = 0; i < numTextsTrained; ++i)
        {
            if (filename.equals(trainedTexts[i]))
            {
                return true;
            }
        }
        
        
        return false;
    }
    
    
    public static void promptUser() {
        System.out.println("0 - Terminate Program");
        System.out.println("1 - Generate Sentence");
        System.out.println("2 - Train Program on File");
        System.out.println("3 - Change Number of Words in Prefix");
        System.out.println("4 - Number of Sentences to Generate");
        System.out.print("Action: ");
    }
    
    
    public static void main(String[] args) {
        // scanner opened here, closed later when the program is selected to end
        Scanner in = new Scanner(System.in);
        
        while (true) {
            int decision = -1;
            
            // Prompt user input/action
            do {
                promptUser();
                try {
                    decision = in.nextInt();
                } catch (Exception e) {
                    decision = -1;
                }
                in.nextLine();
            } while (decision < 0);
            
            switch (decision) {
                case 0:
                    System.out.printf("\nProgram Ending\n");
                    in.close();
                    return;
                    
                case 1:
                    if (numTextsTrained < 1) {
                        System.out.printf("Program has not been trained yet\n\n");
                        break;
                    }
                    
                    System.out.printf("\nDynamically Generated Text\n\n");
                    for (int i = 0; i < numSentences; ++i) {
                        String sentence = generateSentence(map);   //////////////////////////
                        System.out.printf("%s\n\n", sentence);
                    }
                    break;
                    
                case 2:
                {
                    String filename = null;
                    do {
                        System.out.print("Enter file name ('0' for menu): ");
                        filename = in.next();
                        in.nextLine();
                        
                        // Allow user to return to main menu
                        if (filename.equals("0"))
                            break;
                        
                        // Check if program has already been trained on this text
                        if (haveTrainedText(filename)) {
                            System.out.printf("Program has already been trained on this text\n\n");
                            filename = null;
                            continue;
                        }
                        // Check that the file is valid
                        File check = new File(filename);
                        if (!check.isFile()) 
                        {
                            System.out.printf("Invalid file name\n\n");
                            filename = null;
                            continue;
                        }
                        PrefixGenerator.trainPrefixMap(map, filename);
                        
                    } 
                    while (filename == null);
                    
                    addTrainedTexts(filename);
                    System.out.println();
                    break;
                }
                
                case 3:
                    int length = -1;
                    do {
                        System.out.print("Number of Words in Prefix: ");
                        length = in.nextInt();
                        if (length <= 0)
                            System.out.println("Invalid input");
                        in.nextLine();
                    } while (length <= 0);
                    
                    map = retrain(length);
                    
                    System.out.println("All texts re-trained\n");
                    break;
                    
                case 4:
                    int num = -1;
                    do {
                        System.out.print("Num. of Sentences: ");
                        num = in.nextInt();
                        if (num < 0)
                            System.out.println("Invalid input");
                    } while (num < 0);
                    numSentences = num;
                    System.out.println();
                    break;
                    
                default:
                    System.out.printf("Invalid program action\n\n");
            }
        }
    }
    public static boolean isPunctuation(char c) 
    {
        return c == '.' || c == ',' || c == '?' || c == '!' || c == ';'
            || c == ':' || c == '"' || c == '(' || c == ')' || c == '\'';
    }
    public static boolean isTerminator(char c)
    {
        return c == '.' || c == '!' || c == '?' ;
    }
    public static boolean shouldTerminate(String suffix)
    {
        for (int i = 0; i < suffix.length(); i++)
        {            
            if ( isTerminator(suffix.charAt(i)) && (i == suffix.length() - 1 
                                                        || loopChecking( suffix.charAt(i), i, suffix ) ) )
            {
                return true;   
            }
        }
        return false;
        
    }
    public static boolean loopChecking(char c, int startPos, String check)
    {
        for (int i = startPos; i < check.length(); i++)
        {
            if ( !isPunctuation(check.charAt(i)) )
            {
                return false;
            }
        }
        return true;
    }
    
    public static String generateSentence(StringArrayMap map) 
    {
        if (map == null)
        {
            map = new StringArrayMap();
            //but have to train if map is null and filename is not passed as param
        }
        String result = "";
        String suffix = "";
        Prefix.initializeSentenceStartArray();
        String[] prefixStringArray = Prefix.getStartOfSentencePrefixes();
        Prefix newPrefixObject = map.getPrefix(prefixStringArray);
        do
        {   
            newPrefixObject = map.getPrefix(prefixStringArray);
            if (newPrefixObject != null)
            {
                suffix = newPrefixObject.getRandomSuffix(); 
                
                if (shouldTerminate(suffix))
                {
                    result = result + suffix;     
                }
                else if (Prefix.compareArrays(prefixStringArray, Prefix.getStartOfSentencePrefixes()))
                {
                    result = result + capitalize(suffix) + " ";     
                }
                else if (suffix.charAt(suffix.length() - 1) == ':' || suffix.charAt(suffix.length() - 1) == ',' 
                             || suffix.charAt(suffix.length() - 1) == ';')
                {     
                    result = result + suffix + "\n";     
                }
                else
                {
                    if (suffix.equals("i"))
                    {      
                        String tempSuffix;
                        tempSuffix = capitalize(suffix);
                        result = result + tempSuffix + " ";            
                    }
                    else
                    {
                        result = result + suffix + " ";      
                    }     
                }    
                prefixStringArray = PrefixGenerator.newPrefixGenerator(prefixStringArray, suffix);
                
            }   
        }
        while(!shouldTerminate(suffix));
        return result;
    }
    public static String capitalize(String originalS)
    {
        if (originalS.length() > 1)
        {
            return  originalS.substring(0 , 1).toUpperCase() + originalS.substring(1).toLowerCase();   
        }
        else
        {
            return originalS.toUpperCase();
        }
        
    }
    public static StringArrayMap retrain(int prefixLength) 
    {
        StringArrayMap newMap = new StringArrayMap();
        
        Prefix.prefixLength = prefixLength;
        Prefix.initializeSentenceStartArray();
        for (int i = 0 ; i < numTextsTrained ; i++)
        {
            PrefixGenerator.trainPrefixMap(newMap, trainedTexts[i]);  
        }  
        return newMap; 
    }
    
}
