package com.twfl;

public class Clipping {
    private final boolean isNote;
    private String content;

    public Clipping (boolean isNote, String content){
        this.isNote = isNote;
        this.content = content;
    }

    public boolean isNote(){
        return this.isNote;
    }

    public String getContent(){
        return this.content;
    }

    public void setContent(String newContent){
        this.content = newContent;
    }
}
