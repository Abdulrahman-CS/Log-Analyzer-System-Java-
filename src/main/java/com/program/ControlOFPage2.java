
package com.program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ControlOFPage2 {
    @FXML
    private ListView<String> listViewOfRecord = new ListView<>();
    @FXML
    private ChoiceBox<String> choicesOfSort = new ChoiceBox<>();
    @FXML
    private TextField commandText;
    @FXML
    private TextField filePathSave;
    @FXML
    private Label msgErrorOfSecond;
    @FXML
    private Label msgErrorSave;
    @FXML
    private TextField filePathLoad;
    @FXML
    private Label msgErrorLoad;
    private ArrayList<ArrayList<LogRecord>> listOfListRecord = new ArrayList<>();
    private static Stage saveStage;
    private static Stage loadStage;


    public void initialize() throws IOException {
        listOfListRecord.add(ControlOFPage1.listOfLogRecord);

        for (LogRecord logRecord : ControlOFPage1.listOfLogRecord) {
            listViewOfRecord.getItems().add(logRecord.toString());
        }

        choicesOfSort.setValue("Timestamp");
        choicesOfSort.getItems().addAll("Date", "Time", "Timestamp", "IPAddress", "Username", "Role", "URL", "Description");
        choicesOfSort.setOnAction(this::getChoicesOfSort);
    }

    public void filter(ActionEvent event) throws IOException {
        ArrayList<LogRecord> currentList = listOfListRecord.get(listOfListRecord.size() - 1);
        ArrayList<LogRecord> tempListRecord = new ArrayList<>();
        msgErrorOfSecond.setVisible(false);

        String command = commandText.getText();
        String attribute;
        try {
            attribute = command.split("[.(=<>]")[0].trim();
        }catch (Exception e){
            System.out.println("Wrong in input of command, try again!");
            return;
        }

        String text = "";
        boolean comm = false;
        String textOfTimestamp;
        try {
            text = command.split("[”“\"']")[1].trim();
            comm = command.split("[.(]")[1].trim().equalsIgnoreCase("contains");
        } catch (Exception ignored){}

        if (attribute.equalsIgnoreCase("URL") && comm){
            for (LogRecord logRecord : currentList) {
                if (logRecord.getURL().equalsIgnoreCase(text))
                    tempListRecord.add(logRecord);
            }
            updateList(tempListRecord);
        } else if (attribute.equalsIgnoreCase("IPAddress") && comm) {
            for (LogRecord logRecord : currentList) {
                if (logRecord.getIPAddress().equalsIgnoreCase(text))
                    tempListRecord.add(logRecord);
            }
            updateList(tempListRecord);
        } else if (attribute.equalsIgnoreCase("Description") && comm) {
            for (LogRecord logRecord : currentList) {
                if (logRecord.getDescription().equalsIgnoreCase(text))
                    tempListRecord.add(logRecord);
            }
            updateList(tempListRecord);
        } else if (attribute.equalsIgnoreCase("Role") && comm) {
            for (LogRecord logRecord : currentList) {
                if (logRecord.getRole().equalsIgnoreCase(text))
                    tempListRecord.add(logRecord);
            }
            updateList(tempListRecord);
        } else if (attribute.equalsIgnoreCase("Username") && comm) {
            for (LogRecord logRecord : currentList) {
                if (logRecord.getUsername().equalsIgnoreCase(text))
                    tempListRecord.add(logRecord);
            }
            updateList(tempListRecord);
        } else if (attribute.equalsIgnoreCase("Date")) {
            char operator = command.split("Date")[1].trim().charAt(0);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate tempDate = LocalDate.parse(text, format);

            if (operator == '='){
                for (LogRecord logRecord : currentList) {
                    if (logRecord.getDate().isEqual(tempDate))
                        tempListRecord.add(logRecord);
                }
                updateList(tempListRecord);
            } else if (operator == '>'){
                for (LogRecord logRecord : currentList) {
                    if (logRecord.getDate().isAfter(tempDate))
                        tempListRecord.add(logRecord);
                }
                updateList(tempListRecord);
            } else if (operator == '<'){
                for (LogRecord logRecord : currentList) {
                    if (logRecord.getDate().isBefore(tempDate))
                        tempListRecord.add(logRecord);
                }
                updateList(tempListRecord);
            } else {
                msgErrorOfSecond.setVisible(true);
                System.out.println("Wrong in input of command, try again!");
            }
        } else if (attribute.equalsIgnoreCase("Timestamp")) {
            char operator = command.split("Timestamp")[1].trim().charAt(0);
            textOfTimestamp = command.split("[=<>]")[1].trim();

            if (operator == '='){
                for (LogRecord logRecord : currentList) {
                    if (logRecord.getTimestamp() == Long.parseLong(textOfTimestamp))
                        tempListRecord.add(logRecord);
                }
                updateList(tempListRecord);
            } else if (operator == '>'){
                for (LogRecord logRecord : currentList) {
                    if (logRecord.getTimestamp() > Long.parseLong(textOfTimestamp))
                        tempListRecord.add(logRecord);
                }
                updateList(tempListRecord);
            } else if (operator == '<'){
                for (LogRecord logRecord : currentList) {
                    if (logRecord.getTimestamp() < Long.parseLong(textOfTimestamp))
                        tempListRecord.add(logRecord);
                }
                updateList(tempListRecord);
            } else {
                msgErrorOfSecond.setVisible(true);
                System.out.println("Wrong in input of command, try again!");
            }
        } else {
            msgErrorOfSecond.setVisible(true);
            System.out.println("Wrong in input of command, try again!");
        }
    }

    @FXML
    public void save(ActionEvent event) throws IOException {
        msgErrorOfSecond.setVisible(false);
        serLists(); //Save the latest update to the list of lists
        saveStage = new Stage();
        Parent parentOfSave;
        try {
            parentOfSave = FXMLLoader.load(getClass().getResource("viewCodeOfSave.fxml"));
        }catch (Exception e){
            System.out.println("File 'fxml' of save page unavailable!");
            return;
        }
        Scene sceneOfSave = new Scene(parentOfSave);
        saveStage.setTitle("Save records");
        saveStage.setScene(sceneOfSave);
        saveStage.showAndWait();
        deSerLists();
    }

    @FXML
    public void load(ActionEvent event) throws IOException {
        msgErrorOfSecond.setVisible(false);
        serLists(); //Save the latest update to the list of lists

        loadStage = new Stage();
        Parent parentOfLoad;
        try {
            parentOfLoad = FXMLLoader.load(getClass().getResource("viewCodeOfLoad.fxml"));
        }catch (Exception e){
            System.out.println("File 'fxml' of load page unavailable!");
            return;
        }

        Scene sceneOfLoad = new Scene(parentOfLoad);
        loadStage.setTitle("Load records");
        loadStage.setScene(sceneOfLoad);
        loadStage.showAndWait();

        deSerLists(); //Fetch the latest update to the list of lists
        listViewOfRecord.getItems().clear();
        for (LogRecord logRecord : listOfListRecord.get(listOfListRecord.size() - 1)) {
            listViewOfRecord.getItems().add(logRecord.toString());
        }
        listViewOfRecord.refresh();
        deSerLists();
    }

    @FXML
    public void reset(ActionEvent event) throws IOException {
        msgErrorOfSecond.setVisible(false);
        listOfListRecord.clear();
        listOfListRecord.add(ControlOFPage1.listOfLogRecord);
        listViewOfRecord.getItems().clear();

        for (LogRecord logRecord : ControlOFPage1.listOfLogRecord) {
            listViewOfRecord.getItems().add(logRecord.toString());
        }
    }

    @FXML
    public void back(ActionEvent event) throws IOException {
        msgErrorOfSecond.setVisible(false);
        if (listOfListRecord.size() > 1) {
            listOfListRecord.remove(listOfListRecord.size() - 1);

            listViewOfRecord.getItems().clear();
            for (LogRecord logRecord : listOfListRecord.get(listOfListRecord.size() - 1)) {
                listViewOfRecord.getItems().add(logRecord.toString());
            }
        }
    }

    public void getChoicesOfSort(ActionEvent event){
        msgErrorOfSecond.setVisible(false);
        String choice = choicesOfSort.getValue();
        ArrayList<LogRecord> currentList = listOfListRecord.get(listOfListRecord.size() - 1);

        Collections.sort(currentList, new Comparator<LogRecord>() {
            @Override
            public int compare(LogRecord tempLogRecord1, LogRecord tempLogRecord2) {
                return switch (choice) {
                    case "Date" -> tempLogRecord1.getDate().compareTo(tempLogRecord2.getDate());
                    case "Time" -> tempLogRecord1.getTime().compareTo(tempLogRecord2.getTime());
                    case "Timestamp" -> Long.compare(tempLogRecord1.getTimestamp(), tempLogRecord2.getTimestamp());
                    case "IPAddress" -> tempLogRecord1.getIPAddress().compareTo(tempLogRecord2.getIPAddress());
                    case "Username" -> tempLogRecord1.getUsername().compareTo(tempLogRecord2.getUsername());
                    case "Role" -> tempLogRecord1.getRole().compareTo(tempLogRecord2.getRole());
                    case "URL" -> tempLogRecord1.getURL().compareTo(tempLogRecord2.getURL());
                    case "Description" -> tempLogRecord1.getDescription().compareTo(tempLogRecord2.getDescription());
                    default -> 0;
                };
            }
        });

        listViewOfRecord.getItems().clear();
        for (LogRecord logRecord : currentList) {
            listViewOfRecord.getItems().add(logRecord.toString());
        }

    }

    public void updateList(ArrayList<LogRecord> list) throws IOException {
        listOfListRecord.add(list);

        listViewOfRecord.getItems().clear();
        for (LogRecord logRecord : listOfListRecord.get(listOfListRecord.size() - 1)) {
            listViewOfRecord.getItems().add(logRecord.toString());
        }
    }

    @FXML
    public void clickSaveButton(ActionEvent event) throws Exception {
        FileOutputStream fileOFObjects;
        ObjectOutputStream writer;

        try {
            fileOFObjects = new FileOutputStream(filePathSave.getText());
            writer = new ObjectOutputStream(fileOFObjects);
        } catch (Exception e){
            msgErrorSave.setVisible(true);
            System.out.println("There is wrong in path of load file!");
            return;
        }

        msgErrorSave.setVisible(false);

        deSerLists(); //Fetch the latest update to the list of lists
        for (LogRecord i : listOfListRecord.get(listOfListRecord.size() - 1)) {
            writer.writeObject(i);
        }

        writer.close();
        saveStage.close();
    }

    @FXML
    public void clickLoadButton(ActionEvent event) throws Exception {

        File file = new File(filePathLoad.getText());
        if (!file.exists()){
            msgErrorLoad.setVisible(true);
            System.out.println("There is wrong in path of load file!");
            return;
        }
        msgErrorLoad.setVisible(false);

        FileInputStream fileOFObjects = new FileInputStream(file);
        ObjectInputStream read = new ObjectInputStream(fileOFObjects);
        ArrayList<LogRecord> tempListRecord = new ArrayList<>();

        try {
            LogRecord logRecord = (LogRecord) read.readObject();
            while (logRecord != null) {
                tempListRecord.add(logRecord);
                logRecord = (LogRecord) read.readObject();
            }
        } catch (Exception ignored){}

        deSerLists(); //Fetch the latest update to the list of lists
        listOfListRecord.add(tempListRecord); //Add the downloaded list to the lists
        serLists(); //Saving lists

        loadStage.close();
    }


    //serialization for list of list (update/Fetch latest update)
    public void serLists() throws IOException {
        FileOutputStream fileOFObjects;
        ObjectOutputStream writer;

        try {
            fileOFObjects = new FileOutputStream("objectOfLists.txt");
            writer = new ObjectOutputStream(fileOFObjects);
        } catch (Exception e){
            return;
        }

        for (ArrayList<LogRecord> i : listOfListRecord) {
            writer.writeObject(i);
        }
        writer.close();
    }

    //de-serialization for list of list (save)
    public void deSerLists() throws IOException {
        File file = new File("objectOfLists.txt");
        if (!file.exists()){
            return;
        }
        FileInputStream fileOFObjects = new FileInputStream(file);
        ObjectInputStream read = new ObjectInputStream(fileOFObjects);

        listOfListRecord.clear();
        try {
            ArrayList<LogRecord> list = (ArrayList<LogRecord>) read.readObject();
            while (list != null) {
                listOfListRecord.add(list);
                list = (ArrayList<LogRecord>) read.readObject();
            }
        }catch (Exception ignored){}

        read.close();
    }

}
