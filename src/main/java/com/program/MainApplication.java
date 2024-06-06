

package com.program;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    private static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws IOException {
        this.mainStage = mainStage;
        Parent parentMain;
        try {
            parentMain = FXMLLoader.load(getClass().getResource("viewCodeOfMain.fxml"));
        }catch (Exception e){
            System.out.println("File 'fxml' of main page unavailable!");
            return;
        }
        Scene sceneMain = new Scene(parentMain);
        mainStage.setTitle("Logs Analysis");
        mainStage.setScene(sceneMain);
        mainStage.show();
    }

    public static void showSecondPage() throws Exception {
        Parent parentOfSecond;
        try {
            parentOfSecond = FXMLLoader.load(MainApplication.class.getResource("viewCodeOfSecond.fxml"));
        }catch (Exception e){
            System.out.println("File 'fxml' of second page unavailable!");
            return;
        }
        Stage secondStage = new Stage();
        secondStage.setTitle("Logs Analysis");
        Scene sceneOfSecond = new Scene(parentOfSecond);
        secondStage.setScene(sceneOfSecond);
        secondStage.show();
        mainStage.hide();
    }

}