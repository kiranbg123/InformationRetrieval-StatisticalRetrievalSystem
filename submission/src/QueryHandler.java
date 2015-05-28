import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;


public class QueryHandler {

	Map<String, InformationRetrieval_Hw3.IndexStorage> indexMap;
    Set<String> stopWords;
    //DocumentTokenizer documentTokenizer;
    //Map<Integer, IndexBuilder.DocumentInfo> docListDetails;
    Map<Integer,IndexBuilder.DocumentInfo> docListDetails;
    double avgDoclength;
    
	
	public QueryHandler() {
		// TODO Auto-generated constructor stub
	}
	
	//Parameterized Constructor
	 public QueryHandler(Map<String, InformationRetrieval_Hw3.IndexStorage> index,Map<Integer, 
	    		IndexBuilder.DocumentInfo> documents, Set<String> stopWords, int avgDoclength) throws Exception {
			this.indexMap = index;
			this.stopWords = stopWords;
			this.docListDetails = documents;
			//this.documentTokenizer = new DocumentTokenizer();
			this.avgDoclength = avgDoclength;
		}
	 
	

		public void process(String query) throws Exception{
	    	 Map<String, Integer> processedQueryTable = FileParser.processQuery(query);
	    	 removeStopWords(processedQueryTable); 
	    	 Map<Integer, Double> W1_table = new HashMap<>();
	    	 Map<Integer, Double> W2_table = new HashMap<>();
	    	 int queryLenght = calculateQueryLenght(processedQueryTable);
	    	 int docListSize = docListDetails.size();
	    	 
	    	 //Process the each query Term
	    	 for(String queryTerm : processedQueryTable.keySet()){
	    		 InformationRetrieval_Hw3.IndexStorage indexMapEntry = indexMap.get(queryTerm);
	    		 if(indexMapEntry == null){
	    			// System.out.println("query term " + queryTerm + "not available");
	    			 continue;
	    		 }
	    		 
	    		 int docFreq = indexMapEntry.docFrequency;
	    		 for(InformationRetrieval_Hw3.Documents document : indexMapEntry.docList){
	    			 int termFreq = document.frequency;
	    			 int maxTermFreq = (int)docListDetails.get(document.docID).maxFrequency; 
	    			 int docLenght = docListDetails.get(document.docID).docLength.intValue();	
	    			 
	    			 double w1 = calculateW1(termFreq,maxTermFreq,docFreq,docListSize);
	    			 double w2 = calculateW2(termFreq,docLenght,avgDoclength,docFreq,docListSize); 
	    			 
	    			 addWeight(W1_table, document.docID, w1);
	    			 addWeight(W2_table, document.docID, w2);
	    		 }
	    	 }
	    	 
	    	 System.out.print("Stemmed Query: ");
	    	 for(String queryTerm : processedQueryTable.keySet()){
	    		 System.out.print(queryTerm + " ");
	    	 }
	    	 System.out.println();
	    	 System.out.println("\nTop Five document by W1");
	    	printTopDocuments(W1_table, 5);
	       	 System.out.println("\nTop Five document by W2");
	       	printTopDocuments(W2_table, 5);
	    }
		
		private void addWeight(Map<Integer, Double> wMap, int docID, double w) {
			if(wMap.get(docID) == null){
				 wMap.put(docID, w);
				// return;
			 }
		  wMap.put(docID, w + wMap.get(docID) ); 
		}
		
		//To print the top documents
		public  void printTopDocuments(Map<Integer, Double> wMap,int max) {
			TreeSet<Entry<Integer, Double>> sortedSet = new TreeSet<Entry<Integer, Double>>(new myComparator());
			sortedSet.addAll(wMap.entrySet());
			System.out.println("Rank : " + "\t Weight   " + "    : " + " DocId" + "\t Headline");
			Iterator<Entry<Integer, Double>> iterator = sortedSet.iterator();
			//Print the docs for max number mentioned
			for(int i = 0 ; i < max && iterator.hasNext(); i++){
				Entry<Integer, Double> entry = iterator.next();
				IndexBuilder.DocumentInfo documentInfo = docListDetails.get(entry.getKey());
				System.out.println((i+1) + " : " + entry.getValue() + " : " + documentInfo.docID + "\t : " + documentInfo.docName);
			}	
			
		}
		//This is to compaere the value which impliments comparator
		 class myComparator implements Comparator<Entry<Integer, Double>> {
				@Override
				public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
					if(o1.getValue() < o2.getValue()){
						return 1;
					}	
				   return -1;
				}
			}

		public double calculateW1(int termFreq, int maxTermFreq, int docFreq, int collectionSize){
			double temp = 0;
			try {
				//Formula as mentioned in the HW description
				temp = ( 0.4 + 0.6 * Math.log (termFreq + 0.5) / Math.log (maxTermFreq + 1.0) ) *  (Math.log(collectionSize / docFreq) / Math.log(collectionSize)) ;
			} catch (Exception e) {
				temp = 0;
			}
			return temp;
		}

		public double  calculateW2(int termFreq, int doclength, double avgDoclength, int docFreq, int collectionSize){
			double temp = 0;
			try {
				//Formula as mentioned in the HW description
				temp = (0.4 + 0.6 * (termFreq / (termFreq + 0.5 + 1.5 * (doclength / avgDoclength))) * Math.log (collectionSize / docFreq) / Math.log(collectionSize) );
			} catch (Exception e) {
				temp = 0;
			}
			return temp;
		}
		
		public void removeStopWords(Map<String, Integer> processedQueryTable) {
			Iterator<String> iterator = processedQueryTable.keySet().iterator();
			while(iterator.hasNext()){
				if(stopWords.contains(iterator.next())){
					iterator.remove();
				}
			}
		}
		
		public  int calculateQueryLenght(Map<String, Integer> termFreqTable) {
			int length = 0;
			for(String queryTerm : termFreqTable.keySet()){
				length += termFreqTable.get(queryTerm);
			}
			return length;
		}

}
