package com.twfl;

import java.util.*;

public class Book {
    private final String title;
    private final Map<Integer, Clipping> locationToClipping;

    public Book(String title){
        this.title = title;
        locationToClipping = new HashMap<>();
    }

    public void addClipping(Scanner scanner){
        String locationLine = scanner.nextLine();
        boolean isNote = locationLine.startsWith("- Your Note");
        int locationNumber = getLocation(locationLine);
        scanner.nextLine();
        StringBuilder content = new StringBuilder();
        String newLine = "";
        while(!newLine.equals("==========")){
            content.append(newLine);
            newLine = scanner.nextLine();
        }
        if(locationToClipping.containsKey(locationNumber)){
            Clipping oldClipping = locationToClipping.get(locationNumber);
            if(content.toString().contains(oldClipping.getContent())){
                oldClipping.setContent(content.toString());
            }
        }
        else{
            Clipping clipping = new Clipping(isNote, content.toString());
            locationToClipping.put(getLocation(locationLine), clipping);
        }
    }

    public Map<Integer, Clipping> getClippings(){
        return this.locationToClipping;
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

    @Override
    public String toString(){
        return this.title;
    }
}
