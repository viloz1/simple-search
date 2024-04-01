package org.lib.document;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Document {
    String fileName;
    ArrayList<String> contents;


    public Document(String document) throws FileNotFoundException {
        this.fileName = document;
        this.contents = new ArrayList<>();
        File file = new File(document);
        
        Scanner fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            String data = fileReader.nextLine();
            String[] newData = data.split(" ");
            this.contents.addAll(Arrays.asList(newData));
        }
        fileReader.close();
    }

    public ArrayList<String> getContents() {
        return this.contents;
    }

    public String getFileName() {
        return this.fileName;
    }
}
