package client.scenes;

import client.utils.ServerUtils;
import commons.Ingredient;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class IngredientListCtrl {

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

    private final MainCtrl pc;

    @Inject
    public IngredientListCtrl(MainCtrl m) {
        pc = m;
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

    /**
     * Exits ingredient list view
     */
    @FXML
    protected void exitIngredients() {
        pc.showList();
    }
}


