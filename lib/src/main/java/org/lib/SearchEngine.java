 package org.lib.searchengine;

import java.util.HashMap;
import java.util.ArrayList;

import org.lib.DocumentValue;
import org.lib.DocumentValuesComparatorReversed;
import org.lib.document.Document;
import org.lib.word.Word;
import java.io.FileNotFoundException;
import java.util.Map;

 public class SearchEngine {

    HashMap<String, HashMap<String, Word>> documentToWords;
    public HashMap<String, ArrayList<String>> mapping;

    public SearchEngine(String[] newDocuments) throws FileNotFoundException {
         this.mapping = new HashMap<>();
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

        //Calculate the number of occurrences of each word in the given document
        for (String word : contents) {
            Integer occurrence = occurrences.getOrDefault(word, 0);
            occurrence++;
            occurrences.put(word, occurrence);

            ArrayList<String> currentDocuments = this.mapping.getOrDefault(word, new ArrayList<String>());
            if (!currentDocuments.contains(fileName)) {
                currentDocuments.add(fileName);
                this.mapping.put(word, currentDocuments);
            }
        }

        //Calculate term-frequency for each word in the document
        HashMap<String, Word> words = new HashMap<>();
        for(Map.Entry<String, Integer> word : occurrences.entrySet()) {
            double tf = (double) word.getValue() / occurrences.size();

            Word wordObject = new Word(word.getKey(), tf);
            words.put(word.getKey(), wordObject);
        }

        this.documentToWords.put(fileName, words);
    }

    void sortWords() {
        HashMap<String, ArrayList<String>> newMapping = new HashMap<>();

        for (Map.Entry<String, ArrayList<String>> wordInDocuments : this.mapping.entrySet()) {
            String wordString = wordInDocuments.getKey();

            ArrayList<String> newDocumentList = sortDocumentListBasedOnTFIDF(wordString, this.mapping.get(wordString));
            newMapping.put(wordString, newDocumentList);

        }
        this.mapping = newMapping;
    }

     ArrayList<String> sortDocumentListBasedOnTFIDF(String word, ArrayList<String> documents) {
        ArrayList<DocumentValue> documentValues = new ArrayList<>();
        for (String document : documents) {
            double tf = this.documentToWords.get(document).get(word).getTermFrequency();
            double idf =  documents.size() / Math.log(this.documentToWords.size());
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
         return this.mapping.getOrDefault(word, new ArrayList<String>());
     }


}
