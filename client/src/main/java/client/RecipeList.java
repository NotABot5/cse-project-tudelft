package client;


import commons.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import server.api.RecipeController;
import javafx.application.Application;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;


public class RecipeList extends Application {

    @FXML
    protected TableView<Recipe> table;
    @FXML
    protected TableColumn<Recipe, Long> idColumn;
    @FXML
    protected TableColumn<Recipe, String> nameColumn;
    @FXML
    protected TableColumn<Recipe, String> languageColumn;

    private final RecipeController recipeController;

    @Autowired
    public RecipeList(RecipeController recipeController) {
        this.recipeController = recipeController;
    }

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/recipeList.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Recipe List");
        primaryStage.show();
    }

    /**
     * Called automatically after the FXML has been loaded.
     * Sets all TextFields to non-editable initially.
     */

    @FXML
    public void initialize() {
        setEditable(false);
        idColumn.setCellValueFactory(cell -> new SimpleLongProperty(cell.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        languageColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getLang()));
        loadRecipeTable();
    }

    /**
     * Helper method to load recipe table
     * Makes use of getAll method from recipeController and sets items
     */
    private void loadRecipeTable() {
        List<Recipe> recipeList = recipeController.getAll();
        table.setItems(FXCollections.observableList(recipeList));
    }



    /**
     * Sets all TextFields to editable or read-only.
     *
     * @param value true to make editable, false to make read-only
     */

    protected void setEditable(boolean value) {
        table.setEditable(value);
        idColumn.setEditable(value);
        nameColumn.setEditable(value);
        languageColumn.setEditable(value);
    }
}
