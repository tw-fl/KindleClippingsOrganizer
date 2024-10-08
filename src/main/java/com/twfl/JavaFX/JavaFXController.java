package com.twfl.JavaFX;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twfl.Book;
import com.twfl.Library;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;
import java.util.TreeMap;

public class JavaFXController {
    public Button organizeClippingsButton;
    public Button chooseFileButton;
    public Button chooseDestinationButton;
    public ListView<Book> booksList;
    public TextArea bookTextArea;
    public Button backButton;
    public VBox rightColumn;
    private Stage stage;
    private final ObservableList<Book> observableBooksList = FXCollections.observableArrayList();

    private File selectedFile = null;
    private File selectedDirectory = null;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    public void initialize(){
        booksList.setItems(observableBooksList);
    }

    @FXML
    public void handleChooseFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File (.txt)", "*.txt"));

        selectedFile = fileChooser.showOpenDialog(stage);

        organizeClippingsButton.setDisable(selectedDirectory == null);
    }

    @FXML
    public void handleChooseDestination(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        selectedDirectory = directoryChooser.showDialog(stage);

        organizeClippingsButton.setDisable(selectedDirectory == null);
    }

    @FXML
    public void handleOrganizeClippings(){
        if(selectedDirectory != null){
            Library library = generateLibrary();
            selectedDirectory = null;
            selectedFile = null;

            if(library != null){
                observableBooksList.addAll(library.getBooks());
            }
            rightColumnVisibility(true);
        }
    }

    @FXML
    public void handleListDoubleClicked(javafx.scene.input.MouseEvent event) {
        if(event.getClickCount() == 2){
            rightColumnVisibility(false);

            Book selectedItem = booksList.getSelectionModel().getSelectedItem();
            StringBuilder bookText = new StringBuilder();

            new TreeMap<>(selectedItem.getClippings()).forEach((location, clipping) -> {
                bookText.append("Location ").append(location).append(":\n");
                if(clipping.isNote()){
                    bookText.append("NOTE: ");
                }
                bookText.append(clipping.getContent()).append("\n\n");
            });

            bookTextArea.setText(bookText.toString());
        }
    }

    @FXML
    public void handleBackButton() {
        bookTextArea.setText("");
        rightColumnVisibility(true);
    }

    private Library generateLibrary(){
        Scanner myClippingsScanner;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File jsonFile = new File(selectedDirectory.getAbsolutePath() + "/Clippings.json");
        Library library;
        try{
            if(jsonFile.createNewFile()){
                library = new Library();
            }
            else{
                FileReader reader = new FileReader(jsonFile);
                library = gson.fromJson(reader, Library.class);
                if(library == null){
                    library = new Library();
                }
                reader.close();
            }
        }catch(IOException e){
            System.err.println("Error reading or creating the JSON file.");
            return null;
        }

        if(selectedFile != null){
            try {
                myClippingsScanner = new Scanner(selectedFile);
            } catch(FileNotFoundException e){
                System.err.println("'My Clippings.txt' not found");
                return null;
            }

            library.addNewClippings(myClippingsScanner);
            myClippingsScanner.close();

            try(FileWriter writer = new FileWriter(selectedDirectory.getAbsolutePath() + "/Clippings.json")){
                gson.toJson(library, writer);
            }catch(IOException e){
                System.err.println("Error writing to JSON.");
            }
        }

        return library;
    }

    private void rightColumnVisibility(boolean showList){
        booksList.setDisable(!showList);
        booksList.setVisible(showList);
        rightColumn.setDisable(showList);
        rightColumn.setVisible(!showList);
    }

}