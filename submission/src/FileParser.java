import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeMap;
import java.util.regex.Pattern;


public class FileParser {

	public FileParser() {
		// TODO Auto-generated constructor stub
	}
	
	public static StanfordLemmatizer lemmatizer = new StanfordLemmatizer();

	public static TreeMap<String, Integer> readFile(File file, Boolean shouldStem ) throws IOException{
		
		 BufferedReader buffer;
		 buffer = new BufferedReader(new FileReader(file));
		 String line;
		 String[] words;
		 
		 List<String> lemmatizedString = new LinkedList<String>();
		 TreeMap<String, Integer> wordList = new TreeMap<String, Integer>();
		 TreeMap<String, Integer> stemmedWordList = new TreeMap<String, Integer>();
		 while((line = buffer.readLine()) != null)
	        {
				/*
				 * If the line contains a '<', it is considered a tag and tag_count is incremented.
				 */
			 //if(line.contains("<"))
	        		
	        	String s1 = line.replaceAll("[<*>/]", "");
	        	
	        	String s2 = s1.replaceAll("[^a-zA-Z.]+"," "); //Replace everything that is not an alphabet with a blank space.
	            String s3 = s2.replaceAll("[.]", "");//Replace words with . (eg U.S) as 1 word
	            String s4 = "";
	        	//words = s3.split(" ");
	        	//System.out.println("Text" + s3);
	        	
	        	//System.out.println("Lemmatized document" + lemmatizedString.toString());
	        	//Stem the words into StemmedWord List based on Boolean shouldStem value
	        	if (shouldStem == true)
	        	{
	        		words = s3.split(" ");
	        	for(String word : words)
	            {
	            	//Handle the words properly
	            	if(!word.equals(""))
	            	{
	            		word = word.toLowerCase(); // Converts all words to lower case.
	            		String stemmedWord = null;
	            		String temp = word;
	      			  Stemmer myStemmer = new Stemmer();
	      			  //add the word to the stemmer
	      			  myStemmer.add(temp.toCharArray(), temp.length());
	      			  myStemmer.stem();
	      			  stemmedWord = myStemmer.toString();
	            		
	            		//add word if it isn't added already
	            		if(!stemmedWordList.containsKey(stemmedWord))
	            		{                             
	            			//first occurance of this word
	            			stemmedWordList.put(stemmedWord, 1); 
	            		}
	            		else
	            		{
	            			//Increament the count of that word
	            			stemmedWordList.put(word, stemmedWordList.get(stemmedWord) + 1);
	            			
	            		}
	            	}
	            }
	        	
	        	}
	        	else 
	        	{
	        		lemmatizedString = lemmatizer.lemmatize(s3);
		        	ListIterator<String> listIterator = lemmatizedString.listIterator();
		        	//Add each word as a space seperated string
		        	while (listIterator.hasNext()) {
			            s4 = s4 + listIterator.next() + " ";
			        }
		        	words = s4.split(" ");
	            	for(String word : words)
		            {
	            		//System.out.println("Lemmatized string" + word);
		            	//Handle the words properly
		            	if(!word.equals(""))
		            	{
		            		word = word.toLowerCase(); // Converts all words to lower case.
		            		
		            		//add word if it isn't added already
		            		if(!wordList.containsKey(word))
		            		{                             
		            			//first occurance of this word
		            			wordList.put(word, 1); 
		            		}
		            		else
		            		{
		            			//Increament the count of that word
		            			wordList.put(word, wordList.get(word) + 1);
		            			
		            		}
		            	}
	        	
		            }
	            	
	        	}
	        	
	}
		 buffer.close();
		 if(shouldStem == true)
		 {
	   		 	return stemmedWordList;
		 }
		 else
		 {
			 
       		 return wordList;
		 }
	
	
}
	
	public static List<String> readFile(File file) throws Exception {
		String data = new String(Files.readAllBytes(file.toPath() ));
		String[] parts= Pattern.compile("[Q0-9:]+").split(data);
		List<String> queries = new ArrayList<>();
		for(String part : parts ){
			String query = part.trim().replaceAll("\\r\\n", " ");
			if(query.length() > 0){
				queries.add(query);
			}
		}
		return queries;
	}
	public static TreeMap<String,Integer> processQuery(String query)
	{
		TreeMap<String, Integer> stemmedQueryWordList = new TreeMap<String,Integer>();
		String[] words;
		//return processedQuery;
		
		String s1 = query.replaceAll("[<*>/]", "");
    	
    	String s2 = s1.replaceAll("[^a-zA-Z.]+"," "); //Replace everything that is not an alphabet with a blank space.
        String s3 = s2.replaceAll("[.]", "");//Replace words with . (eg U.S) as 1 word
        String s4 = "";

		words = s3.split(" ");
		//Process the query in the same manner as stemmed WordList
	for(String word : words)
    {
    	//Handle the words properly
    	if(!word.equals(""))
    	{
    		word = word.toLowerCase(); // Converts all words to lower case.
    		String stemmedWord = null;
    		String temp = word;
			  Stemmer myStemmer = new Stemmer();
			  //add the word to the stemmer
			  myStemmer.add(temp.toCharArray(), temp.length());
			  myStemmer.stem();
			  stemmedWord = myStemmer.toString();
    		
    		//add word if it isn't added already
    		if(!stemmedQueryWordList.containsKey(stemmedWord))
    		{                             
    			//first occurance of this word
    			stemmedQueryWordList.put(stemmedWord, 1); 
    		}
    		else
    		{
    			//Increament the count of that word
    			stemmedQueryWordList.put(word, stemmedQueryWordList.get(stemmedWord) + 1);
    			
    		}
    	}
    }
		//Returns the processed Query
	return stemmedQueryWordList;
		
	}
	
	
	}


