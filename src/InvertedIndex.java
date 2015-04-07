import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * The basic idea to index the words is to create a Map that has each word
 * as key and a Set of phrases as value. So when we have to build the Indexes
 * we can map a word to a set of phrases. Also we can add or associate more
 * phrases to that word.
 * Then the queries would be easy, because we can look for the word in the map
 * and then retrieve all the phrases in the set returned.
 * @author svanegas
 *
 */
public class InvertedIndex {
  // Instance for singleton
  private static InvertedIndex instance = null;
  
  // Contains a map from String (word) to a Set of Strings (Phrases) 
  private Map <String, Set <String>> indexedData;
  
  private InvertedIndex() {
    indexedData = new TreeMap <String, Set <String>>();
  }
  
  public static InvertedIndex getInstance() {
    if (instance == null) instance = new InvertedIndex();
    return instance;
  }
  
  /**
   * Creates an Inverted Index in RAM from the given array of String.
   * @param data Array of Strings which contains the phrases to be indexed.
   */
  public void buildFromData(String [] data) {
    // Iterate through phrases.
    for (int i = 0; i < data.length; ++i) {
      // 'text' contains a single phrase to be indexed.
      String text = data[i];
      // We are gonna split the whole phrase, with a regex that only allows
      // letters.
      String [] word = text.split("[^a-zA-Z]+");
      // Iterate through words in the phrase.
      for (int j = 0; j < word.length; ++j) {
        // This set will contain all the phrases that includes word[j].
        Set <String> phrases;
        // We retrieve the current set of the words, if it doesn't exists it
        // will return null.
        phrases = indexedData.get(word[j]);
        // If the set didn't exists (The word wasn't previously indexed) we
        // create a new instance of the set.
        // The 'TreeSet' can be replaced for 'HashSet' for a better performance.
        if (phrases == null) phrases = new TreeSet<String>();
        // We add the phrase that is containing the word.
        phrases.add(data[i]);
        // Associate the resulting set with the given word.
        indexedData.put(word[j], phrases);
      }
    }
  }
  
  /**
   * Retrieves all the phrases that contains the word given.
   * @param word String with word to be found.
   * @return Array of Strings with the phrases that contains the word given.
   */
  public String [] get(String word) {
    // Get the set associated to the given word.
    Set <String> phrases = indexedData.get(word);
    String [] result;
    // If the set doesn't exist (The word is not associated with any word) we
    // create a new empty array.
    if (phrases == null) result = new String[0];
    else {
      // We have to parse the Set of Strings to Array of Strings.
      Object [] phrasesArray = phrases.toArray();
      result = Arrays.copyOf(phrasesArray, phrasesArray.length, String[].class);
    }
    return result;
  }
  
  public static void main(String [] args) {
    String[] data = new String[] { 
        "A brilliant, festive study of JS Bach uses literature and painting to "
        + "illuminate his 'dance-impregnated' music, writes Peter Conrad", 
        "Fatima Bhutto on Malala Yousafzai's fearless and still-controversial "
        + "memoir", 
        "Grisham's sequel to A Time to Kill is a solid courtroom drama about "
        + "racial prejudice marred by a flawless white hero, writes John "
        + "O'Connell", 
        "This strange repackaging of bits and pieces does the Man Booker "
        + "winner no favours, says Sam Leith", 
        "Another book with music related content" 
    };
    
    InvertedIndex invertedIndex = InvertedIndex.getInstance();
    invertedIndex.buildFromData(data);
    
    System.out.println("-- Querying the word 'music' --");
    String[] results = invertedIndex.get("music");
    System.out.println(results.length);
    System.out.println(Arrays.toString(results));
    System.out.println("\n");
    
    System.out.println("-- Querying the word 'to' --");
    results = invertedIndex.get("to");
    System.out.println(results.length);
    System.out.println(Arrays.toString(results));
    System.out.println("\n");
    
    System.out.println("-- Querying the word 'hola' --");
    results = invertedIndex.get("hola");
    System.out.println(results.length);
    System.out.println(Arrays.toString(results));
    System.out.println("\n");
  }
}
