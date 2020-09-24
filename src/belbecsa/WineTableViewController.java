/*
    MainController.java
    Author: Haxhi Dvorani
    Modifier: Sam Belbeck
    Date: April 13, 2020

    Description
    Controller for accessing wine list objects and loading the wines on to the 
    table view
 */

package belbecsa;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Wine;

public class WineTableViewController implements Initializable {

    @FXML private TableView<Wine> tblWines;
    @FXML private TableColumn<Wine, Integer> idCol;
    @FXML private TableColumn<Wine, String> estCol;
    @FXML private TableColumn<Wine, String> grapeCol;
    @FXML private TableColumn<Wine, Integer> yearCol;
    @FXML private TableColumn<Wine, Integer> qtyCol;
    @FXML private TableColumn<Wine, Double> priceCol;
    
    private ObservableList<Wine> olWines;
    
    // TODO Part: 1 - to be repeated in MainController as well
    // Declare as constants the length of the String fields in characters and the 
    // length of the record in bytes
    private final int ESTATE_SIZE = 15;
    private final int GRAPE_SIZE = 15;
    private final int RECORD_SIZE = 80;
    
    //END of Part 1
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("wineID"));
        estCol.setCellValueFactory(new PropertyValueFactory<>("estate"));
        grapeCol.setCellValueFactory(new PropertyValueFactory<>("grape"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        //load values from file here
        loadWines();
    }

    /**
     * Method for loading the content of the file on to the TableView
     */
    private void loadWines() {
        olWines = FXCollections.observableArrayList();
        /*
         * TODO: Part 3 - Load the records on to the TableView
         * 1. Create a RandomAccessFile object in read only mode. It will
         * create/open a file located at "src/res/wines.dat" */
        try {
            RandomAccessFile raf = new RandomAccessFile
                (new File("src/res/wines.dat"), "r");
            long numOfRecords = raf.length() / RECORD_SIZE;
            
            // loop through file records
            for (int i = 0; i < numOfRecords; i++) {                
                
                /* 2. Position the pointer at the beginning of the file and read each
                 * value in each record considering the order and type of data in the file
                 * (int, String(15 characters), String(15 characters), int, int, double.
                 */
                raf.seek(RECORD_SIZE * i);
                int id = raf.readInt();
                
                /* 3. Use readString() method (Part 2) to read the strings of the required
                 * length. */
                String estate = readString(raf, ESTATE_SIZE).trim();
                String grape = readString(raf, GRAPE_SIZE).trim();
                int year = raf.readInt();
                int quantity = raf.readInt();
                double price = raf.readDouble();
         
                /* 4. Create a wine object using the multi-parameter constructor. You can
                 * trim the String values to remove the spaced added to the end when it 
                 * was written. */
                Wine wine = new Wine(estate, grape, year, quantity, price);         

                // 5. Set the wineID since it was not set by the constructor
                wine.setWineID(id);

                /* 6. Add the wine object on to the ObservableList. Remember TableViews
                 * are just like ListViews we covered during our course. */
                olWines.add(wine);
            }// end loop through file records
            raf.close();
            
        /* 7. Add the ObservableList to the TableView so it can be displayed.
         */
         tblWines.setItems(olWines);
         
    }catch (IOException x) {
        //nothing - if no file, don't load anything
    }
         
        //END of Part 3
    }

    /**
     * Method for reading a String of a length size using a RandomAccessFile 
     * object that is passes as a parameter. It reads the string one character 
     * at a time and concatinates them into a string that is returned back
     * @param raf RandomAccessFile object
     * @param size the length od the string that needs to be read
     * @return  the String that was read from the file
     * @throws IOException throws back the IOExceptin thrown by readChar() method
     */
    private String readString(RandomAccessFile raf, int size) throws IOException {
        //TODO: Part 2:
        //Read the record one character at a time, concatinate them and 
        // return back as String   
        String line = "";
        for (int i=0; i < size; i++) {
            line += String.valueOf(raf.readChar());
        }        
        return line;
        
        //END of Part 2
    }

    /**
     * Accessor method for getting the item (row) that is selected in the 
     * TableView and return the wine object that correspond to that table row.
     * This method can be used in other controller to find out which row/wine
     * was selected
     * @return the wine object represented by the tableView selection
     */
    public Wine getSelectedWine(){
        Wine w = tblWines.getSelectionModel().getSelectedItem();
        return w;
    }

    /**
     * Accessor for getting the observable list that is used by the TableView.
     * This method can be used in other controllers
     * @return the ObservableList with Wine objects used in the TableView
     */
    public ObservableList<Wine> getObservableList() {
        return olWines;
    }
    
    /**
     * Accessor for getting the TableView from other classes (controllers)
     * @return the TableView object used in this controller
     */
    public TableView<Wine> getTableView(){
        return tblWines;
    }
}
