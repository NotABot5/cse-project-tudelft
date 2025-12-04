package client;

import client.utils.ServerUtils;
import commons.Ingredient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
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

public class IngredientList extends Application {

    @FXML
    protected TableView<Ingredient> table;
    @FXML
    protected TableColumn<Ingredient, Long> idColumn;
    @FXML
    protected TableColumn<Ingredient, String> nameColumn;
    @FXML
    protected TableColumn<Ingredient, Double> proteinColumn;
    @FXML
    protected TableColumn<Ingredient, Double> fatColumn;
    @FXML
    protected TableColumn<Ingredient, Double> carbsColumn;

    public static void main(String[] args) { launch(args);  }

    /**
     * Loads the FXML layout and shows the main application window.
     *
     * @param primaryStage the primary JavaFX stage
     * @throws Exception if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/IngredientList.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ingredient List");
        primaryStage.show();

    }
    /**
     * Called automatically after the FXML has been loaded.
     * Sets all TextFields to non-editable initially.
     */

    @FXML
    public void initialize() {
        setEditable();
        idColumn.setCellValueFactory(cell -> new SimpleLongProperty(cell.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        proteinColumn.setCellValueFactory(cell ->new SimpleDoubleProperty(cell.getValue().getProtein()).asObject());
        fatColumn.setCellValueFactory(cell ->new SimpleDoubleProperty(cell.getValue().getFat()).asObject());
        carbsColumn.setCellValueFactory(cell ->new SimpleDoubleProperty(cell.getValue().getCarbs()).asObject());
        loadIngredientTable();

    }
    /**
     * Helper method to load ingredient table
     * Makes use of getAll method from ingredientController and sets items
     */
    private void loadIngredientTable() {
        new Thread(() -> {
            List<Ingredient> ingredientList = ServerUtils.getIngredients();
            Platform.runLater(() ->
                    table.setItems(FXCollections.observableList(ingredientList))
            );
        }).start();
    }
    /**
     * Sets all TextFields to editable or read-only.
     */

    protected void setEditable() {
        table.setEditable(false);
        idColumn.setEditable(false);
        nameColumn.setEditable(false);
        proteinColumn.setEditable(false);
        fatColumn.setEditable(false);
        carbsColumn.setEditable(false);
    }
}


