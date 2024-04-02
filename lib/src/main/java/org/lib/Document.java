package org.lib;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

//Representation of a Document. Read its content, and then store the content and filename in the object
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
