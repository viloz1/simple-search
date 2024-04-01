package org.lib.word;
public class Word implements Comparable<Word> {
    String word;
    double termFrequency;
    public Word(String word, double termFrequency) {
        this.word = word;
        this.termFrequency = termFrequency;
    }

    public double getTermFrequency() {
        return termFrequency;
    }

    @Override
    public int compareTo(Word other) {
        return Double.compare(this.termFrequency, other.termFrequency);
    }
}
