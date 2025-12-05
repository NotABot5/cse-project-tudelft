package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Recipe;
import javafx.application.Platform;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class RecipeListCtrl {
    @FXML
    protected TableView<Recipe> table;
    @FXML
    protected TableColumn<Recipe, Long> idColumn;
    @FXML
    protected TableColumn<Recipe, String> nameColumn;
    @FXML
    protected TableColumn<Recipe, String> languageColumn;
    @FXML
    protected Button addButton;

    private MainCtrl pc;

    @Inject
    public RecipeListCtrl(MainCtrl m) {
        this.pc = m;
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
        new Thread(() -> {
            List<Recipe> recipeList = ServerUtils.getRecipes();
            Platform.runLater(() ->
                    table.setItems(FXCollections.observableList(recipeList))
            );
        }).start();
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

    public void addRecipeButton(){
        pc.ShowAddRecipe();
    }
}
