package com.twfl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Library {
    private final int maxTitleSize = 128;

    private final HashMap<String, Book> titleToBook;

    public Library(){
        this.titleToBook = new HashMap<>();
    }

    public void addNewClippings(Scanner scanner){
        String title;

        while(scanner.hasNextLine()){
            title = scanner.nextLine();
            if(title.length() > maxTitleSize){
                title = title.substring(0, maxTitleSize - 1);
            }
            if(!titleToBook.containsKey(title)){
                titleToBook.put(title, new Book(title));
            }

            titleToBook.get(title).addClipping(scanner);
        }
    }

    public List<Book> getBooks(){
        return new ArrayList<>(titleToBook.values());
    }
}
