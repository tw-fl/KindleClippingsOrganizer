package com.twfl;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) {
        File myClippingsFile = new File("My Clippings.txt");
        Scanner myClippingsScanner;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            myClippingsScanner = new Scanner(myClippingsFile);
        } catch(FileNotFoundException e){
            System.err.println("'My Clippings.txt' not found");
            return;
        }

        File jsonFile = new File("Clippings.json");
        Library library;
        try{
            if(jsonFile.createNewFile()){
                System.out.println("JSON file created successfully.");
                library = new Library();
            }
            else{
                try(FileReader reader = new FileReader(jsonFile)){
                    System.out.println("Reading JSON file.");
                    library = gson.fromJson(reader, Library.class);
                    if(library == null)
                        library = new Library();
                }catch(IOException e){
                    System.err.println("Error reading JSON file");
                    return;
                }
            }
        }catch(IOException e){
            System.err.println("Error creating the JSON file.");
            return;
        }

        library.addNewClippings(myClippingsScanner);
        myClippingsScanner.close();

        try(FileWriter writer = new FileWriter("Clippings.json")){
            gson.toJson(library, writer);
        }catch(IOException e){
            System.err.println("Error writing to JSON.");
        }

        TreeMap<Integer, ArrayList<Clipping>> test = new TreeMap<>(library.getBooks().get(1).getClippings());
        for(Map.Entry<Integer, ArrayList<Clipping>> entry : test.entrySet()){
            System.out.println(entry.getKey() + ": ");
            entry.getValue().forEach(x -> {
                if(x.isNote())
                    System.out.println("Note: ");
                System.out.println(x.getContent());
            });
        }

        System.out.println("Done!");
    }
}