package Client;

import commons.Ingredient;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainLecture extends Application {

    @FXML protected TextField fieldName;
    @FXML protected TextField fieldFat;
    @FXML protected TextField fieldCarbs;
    @FXML protected TextField fieldProtein;
    @FXML protected ChoiceBox<Ingredient> ingredientChoiceBox;




    protected String oldName, oldFat, oldCarbs, oldProtein;

    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Loads the FXML layout and shows the main application window.
     *
     * @param primaryStage the primary JavaFX stage
     * @throws Exception if the FXML file cannot be loaded
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/example.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ingredient Editor");
        primaryStage.show();
    }
    /**
     * Called automatically after the FXML has been loaded.
     * Sets all TextFields to non-editable initially.
     */

    @FXML
    public void initialize() {
        setEditable(false);
    }
    /**
     * Sets all TextFields to editable or read-only.
     *
     * @param value true to make editable, false to make read-only
     */

    protected void setEditable(boolean value) {
        fieldName.setEditable(value);
        fieldFat.setEditable(value);
        fieldCarbs.setEditable(value);
        fieldProtein.setEditable(value);
    }
    /**
     * Starts the editing mode.
     * Saves the current field values to allow restoring them if cancelled.
     */

    @FXML
    public void onEdit() {

        oldName = fieldName.getText();
        oldFat = fieldFat.getText();
        oldCarbs = fieldCarbs.getText();
        oldProtein = fieldProtein.getText();

        setEditable(true);
    }
    /**
     * Saves the changes (currently prints them to the console)
     * and sets the fields back to read-only.
     */

    @FXML
    public void onSave() {

        System.out.println("Saved:");
        System.out.println("Name: " + fieldName.getText());
        System.out.println("Fat: " + fieldFat.getText());
        System.out.println("Carbs: " + fieldCarbs.getText());
        System.out.println("Protein: " + fieldProtein.getText());

        setEditable(false);
    }
    /**
     * Cancels the current edits and restores the fields to their previous values.
     */

    @FXML
    public void onCancel() {
        // oude waarden terugzetten
        fieldName.setText(oldName);
        fieldFat.setText(oldFat);
        fieldCarbs.setText(oldCarbs);
        fieldProtein.setText(oldProtein);

        setEditable(false);
    }
    /**
     * Clears all TextFields.
     */

    @FXML
    public void onClear() {
        fieldName.clear();
        fieldFat.clear();
        fieldCarbs.clear();
        fieldProtein.clear();
    }
}
