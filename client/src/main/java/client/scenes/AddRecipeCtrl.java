package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Recipe;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.util.List;


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

    private final ServerUtils server;
    private final MainCtrl pc;

    @Inject
    public AddRecipeCtrl(MainCtrl m, ServerUtils server) {
        this.server = server;
        this.pc = m;
    }

    public void confirmClick(){
        String name = recipeName.getText();
        String lang = language.getText();

        if (name == null || name.trim().isEmpty()) {
            showAlert("Error", "Name is required!", Alert.AlertType.ERROR);
            return;
        }
        if (lang == null || lang.trim().isEmpty()) {
            showAlert("Error", "Language is required!", Alert.AlertType.ERROR);
            return;
        }
        Recipe recipe = new Recipe(name, lang, List.of("Add steps here"));

        try{
            server.addRecipe(recipe);
            showAlert("Success", "Recipe added successfully!", Alert.AlertType.INFORMATION);
            pc.ShowList();
            clearFields();
        } catch (Exception e){
            showAlert("Error", "Failed to add Recipe: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void cancelClick(){
        clearFields();
        pc.ShowList();
    }

    public void clearFields(){
        recipeName.clear();
        language.clear();
    }

    /**
     * A model of an alert that can be customized for different purposes
     * @param title a general statement like success or error
     * @param message detailed description of the action or problem
     * @param type it can be simply informative, show an error, etc.
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.getDialogPane().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                alert.close();
                event.consume();
            }
        });
        alert.showAndWait();
    }
}
