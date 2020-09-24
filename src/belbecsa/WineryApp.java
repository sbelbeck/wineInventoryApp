/*
    Name:  Sam Belbeck
    Assignment:  Assignment 6
    Program: WineryApp.java
    Date:  April 11, 2020
    
    Description:
    App to load wine list and add, edit or delete records
*/
package belbecsa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author H.D
 */
public class WineryApp extends Application{
    
    public static void main(String[] args) {
        launch(args);        
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));
        stage.setTitle("Winery Application");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
