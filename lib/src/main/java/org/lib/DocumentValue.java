package org.lib;

import java.util.Comparator;

//Represents the TF-IDF value of a word in a specific document
public class DocumentValue {
    public String getDocument() {
        return document;
    }

    public double getValue() {
        return value;
    }

    String document;
    double value;

    public DocumentValue(String word, double value) {
        this.document = word;
        this.value = value;
    }
}

