 package org.lib;

import java.util.HashMap;
import java.util.ArrayList;

import java.io.FileNotFoundException;
import java.util.Map;

 public class SearchEngine {

     //This map represents the documents and all their words. The key is the document name, and the value
     //is a map with all the words in that document. The key in this map is the word string, and the value
     //is the words term frequency in the given document
    HashMap<String, HashMap<String, Double>> documentToWords;

    //This map represents the words and their document list. The key is a word string, and the value is a list
    //of all the documents it occurs in sorted on TF-IDF
    public HashMap<String, ArrayList<String>> wordOccurrencesTFIDF;

    public SearchEngine(String[] newDocuments) throws FileNotFoundException {
         this.wordOccurrencesTFIDF = new HashMap<>();
         this.documentToWords = new HashMap<>();
         for (String document : newDocuments) {
             Document newDocument = new Document(document);
             addDocument(newDocument);
         }
         sortWords();
     }

    public void addDocument(Document document) {
        ArrayList<String> contents = document.getContents();
        String fileName = document.getFileName();
        HashMap<String, Integer> occurrences = new HashMap<>();

        int totalOccurrences = 0;

        //Calculate the number of occurrences of each word in the given document
        for (String word : contents) {
            Integer occurrence = occurrences.getOrDefault(word, 0);
            occurrence++;
            totalOccurrences++;
            occurrences.put(word, occurrence);

            ArrayList<String> currentDocuments = this.wordOccurrencesTFIDF.getOrDefault(word, new ArrayList<String>());
            if (!currentDocuments.contains(fileName)) {
                currentDocuments.add(fileName);
                this.wordOccurrencesTFIDF.put(word, currentDocuments);
            }
        }

        //Calculate term-frequency for each word in the document
        HashMap<String, Double> words = new HashMap<>();
        for(Map.Entry<String, Integer> word : occurrences.entrySet()) {
            double tf = (double) word.getValue() / totalOccurrences;

            words.put(word.getKey(), tf);
        }

        this.documentToWords.put(fileName, words);
    }

    void sortWords() {
        HashMap<String, ArrayList<String>> newwordOccurrencesTFIDF = new HashMap<>();

        //For each word, sort their document list based on TF-IDF
        for (Map.Entry<String, ArrayList<String>> wordInDocuments : this.wordOccurrencesTFIDF.entrySet()) {
            String wordString = wordInDocuments.getKey();

            ArrayList<String> newDocumentList = sortDocumentListBasedOnTFIDF(wordString, this.wordOccurrencesTFIDF.get(wordString));
            newwordOccurrencesTFIDF.put(wordString, newDocumentList);

        }
        this.wordOccurrencesTFIDF = newwordOccurrencesTFIDF;
    }

     ArrayList<String> sortDocumentListBasedOnTFIDF(String word, ArrayList<String> documents) {
        ArrayList<DocumentValue> documentValues = new ArrayList<>();
        for (String document : documents) {
            double tf = this.documentToWords.get(document).get(word);
            double idf =  Math.log((double) this.documentToWords.size() / documents.size());
            double tfidf = tf * idf;

            DocumentValue documentValue = new DocumentValue(document, tfidf);
            documentValues.add(documentValue);
        }
        documentValues.sort(new DocumentValuesComparatorReversed());
        ArrayList<String> newDocuments = new ArrayList<>();
        for (DocumentValue entry : documentValues) {
            newDocuments.add(entry.getDocument());
        }
        return newDocuments;
    }

    public ArrayList<String> searchWord(String word) {
         return this.wordOccurrencesTFIDF.getOrDefault(word, new ArrayList<String>());
     }
}
