package com.twfl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Book {
    private final String title;
    private final HashMap<Integer, ArrayList<Clipping>> locationToClippings;

    public Book(String title){
        this.title = title;
        locationToClippings = new HashMap<>();
    }

    public void addClipping(Scanner scanner){
        String locationLine = scanner.nextLine();
        boolean isNote = !locationLine.startsWith("- Your Highlight");
        int location = getLocation(locationLine);
        scanner.nextLine();
        StringBuilder content = new StringBuilder();
        String newLine = "";
        while(!Objects.equals(newLine, "==========")){
            content.append(newLine);
            newLine = scanner.nextLine();
        }
        if(locationToClippings.containsKey(location)){
            for(Clipping clipping : locationToClippings.get(location)){
                if(clipping.isNote() == isNote && content.toString().contains(clipping.getContent()))
                    clipping.setContent(content.toString());
            }
        }
        else{
            Clipping clipping = new Clipping(isNote, content.toString());
            ArrayList<Clipping> newList = new ArrayList<>();
            newList.add(clipping);
            locationToClippings.put(getLocation(locationLine), newList);
        }
    }

    public HashMap<Integer, ArrayList<Clipping>> getClippings(){
        return this.locationToClippings;
    }

    private int getLocation (String locationLine){
        int i = 0;
        StringBuilder locationString = new StringBuilder();
        if(locationLine.startsWith("- Your Highlight on page") || locationLine.startsWith("- Your Note on page")){
            while(!Character.isDigit(locationLine.charAt(i)))
                i++;
            while(Character.isDigit(locationLine.charAt(i)))
                i++;
        }
        while(!Character.isDigit(locationLine.charAt(i)))
            i++;
        while(Character.isDigit(locationLine.charAt(i))) {
            locationString.append(locationLine.charAt(i));
            i++;
        }

        return Integer.parseInt(locationString.toString());
    }

    public String getTitle(){
        return this.title;
    }
}
