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

    @FXML private TextField fieldName;
    @FXML private TextField fieldFat;
    @FXML private TextField fieldCarbs;
    @FXML private TextField fieldProtein;
    @FXML private ChoiceBox<Ingredient> ingredientChoiceBox;




    private String oldName, oldFat, oldCarbs, oldProtein;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/example.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ingredient Editor");
        primaryStage.show();
    }

    @FXML
    public void initialize() {
        setEditable(false);
    }

    private void setEditable(boolean value) {
        fieldName.setEditable(value);
        fieldFat.setEditable(value);
        fieldCarbs.setEditable(value);
        fieldProtein.setEditable(value);
    }

    @FXML
    public void onEdit() {

        oldName = fieldName.getText();
        oldFat = fieldFat.getText();
        oldCarbs = fieldCarbs.getText();
        oldProtein = fieldProtein.getText();

        setEditable(true);
    }

    @FXML
    public void onSave() {

        System.out.println("Saved:");
        System.out.println("Name: " + fieldName.getText());
        System.out.println("Fat: " + fieldFat.getText());
        System.out.println("Carbs: " + fieldCarbs.getText());
        System.out.println("Protein: " + fieldProtein.getText());

        setEditable(false);
    }

    @FXML
    public void onCancel() {
        // oude waarden terugzetten
        fieldName.setText(oldName);
        fieldFat.setText(oldFat);
        fieldCarbs.setText(oldCarbs);
        fieldProtein.setText(oldProtein);

        setEditable(false);
    }

    @FXML
    public void onClear() {
        fieldName.clear();
        fieldFat.clear();
        fieldCarbs.clear();
        fieldProtein.clear();
    }
}
