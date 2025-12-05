package client.scenes;

import com.google.inject.Inject;
import javafx.application.Application;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;


public class AddRecipeCtrl {

    @FXML
    protected Label addText;
    @FXML
    protected TextField recipeName;
    @FXML
    protected TextField language;
    @FXML
    protected Button confirmButton;
    @FXML
    protected Button cancelButton;


    private MainCtrl pc;

    @Inject
    public AddRecipeCtrl(MainCtrl m) {
        this.pc = m;
    }

    public void confirmClick(){

        pc.ShowList();
    }

    public void cancelClick(){
        pc.ShowList();
    }




}
