Name: Kiran Bhat Gopalakrishna
NetId: kxb140230

*************************************************************************************************
To Compile:
Go to src folder containing all java files

javac -classpath bin:/usr/local/corenlp341/stanford-corenlp-3.4.1.jar:/usr/local/corenlp341/stanford-corenlp-3.4.1-models.jar *.java

*************************************************************************************************

To Run:

java -classpath bin:/usr/local/corenlp341/stanford-corenlp-3.4.1.jar:/usr/local/corenlp341/stanford-corenlp-3.4.1-models.jar InformationRetrieval_Hw3 <path to cranfield directory> <path to file containing stopwords> <path to queries>

Note: Stopwords file and queries files are provided along with the source code(src Folder). I have used jdk 1.8.0 for testing.

*************************************************************************************************

Programm Description:

I/p: Path to cranfield collection ,stopwords and queries
o/p: Top 5 files which are the query results as per 2 Weights described in Programm Requirement.

Description:


***************************************************************************************************************

1. Program handling:
Programm replaces all the NON alpha numeric characters with " "(space) and seperates words based on the " "(space). However if it encounters . it replaces the word with ""(nothing).
A. The program handles upper and lower case letters to be the same. Ex: People, PEOPLE, People, PEoplE are all same. 
B.  Words with "-" are seperated into two words based on hyphen. Ex:middle-class is treated as 2 words 'middle' and 'class'.
C. Possessives divide the word into two halves. Ex: university's is treated as two words 'university' and 's'
D. The acronyms are treated as 1 word. Ex: U.S is treated as 'us'

2. Datastructures  & Algorithms used: 

A) Hash Map is used to store all the tokens and stemmed tokens.
B) An ArraList is used for computing distinct words in each file of cranfield directory. 
C) Stemmer algorithm to stem the words for 2nd vesion.
d) Stopword List is used to remove stopword (path to file containing stopwords shouls be provided by user)

****************************************************************************************************