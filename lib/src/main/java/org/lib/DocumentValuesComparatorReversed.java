package org.lib;

import java.util.Comparator;

// creates the comparator for comparing stock value
public class DocumentValuesComparatorReversed implements Comparator<DocumentValue> {
    @Override
    public int compare(DocumentValue o1, DocumentValue o2) {
        //Normally, this is the other way around. But since we want to
        //sort our list with the greatest values first we need to
        //reverse the comparison
        return Double.compare(o2.value, o1.value);
    }
}
