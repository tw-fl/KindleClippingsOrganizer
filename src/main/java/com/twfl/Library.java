package com.twfl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Library {
    private final HashMap<String, Book> titleToBook;

    public Library(){
        this.titleToBook = new HashMap<>();
    }

    public void addNewClippings(Scanner scanner){
        String title;

        while(scanner.hasNextLine()){
            title = scanner.nextLine();
            if(title.length() > 64)
                title = title.substring(0,63);
            if(!titleToBook.containsKey(title))
                titleToBook.put(title, new Book(title));

            titleToBook.get(title).addClipping(scanner);
        }
    }

    public ArrayList<Book> getBooks(){
        return new ArrayList<>(titleToBook.values());
    }
}
