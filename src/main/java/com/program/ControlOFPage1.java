

package com.program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ControlOFPage1 {

    @FXML
    private TextField mainFilePath;
    @FXML
    private Label msgError;

    static ArrayList<LogRecord> listOfLogRecord = new ArrayList<>(); //Default (Show in same package just)

    @FXML
    public void clickOKButton(ActionEvent event) throws Exception {

        File mainFile = new File(mainFilePath.getText());

        if (!mainFile.exists()){
            msgError.setVisible(true);
            System.out.println("There is wrong in path of main file!");
            return;
        }
        msgError.setVisible(false);

        Scanner readOfMainFile = new Scanner(mainFile);

        while (readOfMainFile.hasNext()){
            File tempFile = new File(readOfMainFile.nextLine());
            if (!tempFile.exists()){
                msgError.setVisible(true);
                System.out.println("There are errors in one of the paths in the file!");
                return;
            }
            msgError.setVisible(false);
            Scanner readOfTempFile = new Scanner(tempFile);

            while (readOfTempFile.hasNext()) {

                String logInformation = readOfTempFile.nextLine();
                String[] arrLogInformation = logInformation.split("-");

                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");

                try {

                    if (arrLogInformation.length == 7) {
                        listOfLogRecord.add(new LogRecord(LocalDate.parse(arrLogInformation[0], format),
                                LocalTime.parse(arrLogInformation[1]), Long.parseLong(arrLogInformation[2]),
                                arrLogInformation[3], arrLogInformation[4], arrLogInformation[5], arrLogInformation[6]));
                    } else if (arrLogInformation.length == 8) {
                        listOfLogRecord.add(new LogRecord(LocalDate.parse(arrLogInformation[0], format), LocalTime.parse(arrLogInformation[1]),
                                Long.parseLong(arrLogInformation[2]), arrLogInformation[3], arrLogInformation[4],
                                arrLogInformation[5], arrLogInformation[6], arrLogInformation[7]));
                    }
                } catch (Exception e){
                    System.out.println("There are errors in the one of data of records in the files!");
                    return;
                }
            }
        }

        Collections.sort(listOfLogRecord);

        MainApplication.showSecondPage();
    }
    
}
