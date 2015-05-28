import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class InformationRetrieval_Hw3 {

	public InformationRetrieval_Hw3() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		if(args.length < 3)
		{
			System.out.println("You need to provide the path as first argument");
			System.exit(1);
		}
		String cranfield = args[0];
		String stopWordsPath = args[1];
		String queryPath = args[2];
		Set<String> stopWords = parseStopWords(stopWordsPath);
		System.out.println(cranfield);
		//Function to read Cranfield directory into string
		String cranfieldContent = new String();
		
		File cranfieldFiles = new File(cranfield);
		File queryFile = new File(queryPath);
		//Programm time starts
		//long startTime = System.currentTimeMillis();
		
		//parseFile(cranfieldFiles);
		List<String> queries = FileParser.readFile(queryFile);
		IndexBuilder indexBuilder = new IndexBuilder();
		boolean stemming = false;
		Long startTime = System.currentTimeMillis();
		Map<String, IndexStorage> indexunCompressed = IndexBuilder.parseFile(cranfieldFiles, stopWords, true);
		
		int avgDocLength = IndexBuilder.getAvgDocLength();
		//Create a query Handler
		QueryHandler queryHandler = new QueryHandler(indexunCompressed, IndexBuilder.documentDetails, stopWords, avgDocLength);
		for(int i = 0; i < queries.size(); i++){
			System.out.println("\nQuery"+(i+1)+ " : " + queries.get(i) );
			queryHandler.process(queries.get(i));
		}

	}
	public static Set<String> parseStopWords(String filename) throws FileNotFoundException {
		Set<String> stopWords = new HashSet<>(); 
		Scanner scanner = new Scanner(new File(filename));
		while(scanner.hasNext()){
			stopWords.add(scanner.next());
		}
		scanner.close();
		return stopWords;
	}
	public static class IndexStorage {
		String word;
		int docFrequency;
		int termFrequency;
		List<Documents> docList;
	
		public IndexStorage(String word, int docFrequency, int termFrequency, List<Documents> docList) {
			this.word = word;
			this.docFrequency = docFrequency;
			this.termFrequency = termFrequency;
			this.docList = docList;
		}

		@Override
		public String toString() {
			return "IndexStorage [word=" + word + ", docFrequency="
					+ docFrequency + ", termFrequency=" + termFrequency
					+ ", docList=" + docList + "]";
		}

		/*
		@Override
		public String toString() {
			StringBuilder stringBuilder =   new StringBuilder("");
			stringBuilder.append("\n" + term + " " + docFrequency + "/" + termFrequency +"->"); 
			for(PostingEntry postingEntry : postingList){
				stringBuilder.append(postingEntry); 
			}
			stringBuilder.length();
			return stringBuilder.toString();
		}
		*/
	}
	
	static class Documents{
		int docID;
		int frequency;
		
		public Documents(int docID, int frequency) {
			this.docID = docID;
			this.frequency = frequency;
		}

		@Override
		public String toString() {
			return "Documents [docID=" + docID + ", frequency=" + frequency
					+ "]";
		}

		/*
		@Override
		
		public String toString() {
			return docID + "/" + frequency + ",";
		}
		*/
	}
	
	

}
