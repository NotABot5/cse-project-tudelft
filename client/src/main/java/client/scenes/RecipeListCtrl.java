package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Ingredient;
import commons.IngredientUsage;
import commons.Recipe;
import javafx.application.Platform;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    protected ListView<IngredientUsage> Listingredients;
    @FXML
    protected Button addButton;

    private MainCtrl pc;

    @Inject
    public RecipeListCtrl(MainCtrl m) {
        this.pc = m;
    }

    @FXML
    public void initialize() {
        setEditable(false);
        idColumn.setCellValueFactory(cell -> new SimpleLongProperty(cell.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        languageColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getLang()));
        loadRecipeTable();
        //is called when the recipe is clicked, used AI to make it more compact
        table.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        loadIngredientsFromRecipe(newVal);
                    }
                }
        );

        // gets everything in a listview
        Listingredients.setCellFactory(lv -> new ListCell<>() {
            protected void updateItem(IngredientUsage item, boolean empty) {
                if (item != null) {
                    setText(item.getIngredient().getName() + " - " + item.getAmount() + " " + item.getUnit());
                } else {
                    setText("");
                }
            }
        });
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
     * Called when the user clicked on the recipe.
     * Clears the List of ingredients and fills it with the new ingredients
     *
     * @param recipe as a unique item to choose the right ingredients
     */

    private void loadIngredientsFromRecipe(Recipe recipe) {

        if (recipe == null) return;

        List<IngredientUsage> ingredientsRecipe =
                ServerUtils.fetchAllIngredientsInRecipe(recipe.getId());

        Listingredients.getItems().clear();
        Listingredients.getItems().addAll(ingredientsRecipe);
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


    @FXML
    protected void AddnewIngredient(MouseEvent event) {

        Addingredient_popup();


    }
    //used https://codingtechroom.com/question/creating-popup-windows-in-javafx for some help of some things,
    //Maybe it can be done in FXML, but it was way more work like that and not less LoC
    //Used AI to get everything updated for the list, it was a few Lines, only adding a SetIngredient and refering to it in this method.

    /**
     * Opens a popup when a recipe is selected
     * Gives alerts when a wrong move is done
     */
    private void Addingredient_popup() {
        Recipe selectedRecipe = table.getSelectionModel().getSelectedItem();
        if (selectedRecipe == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Select a recipe first!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Stage popupStage = new Stage();
        popupStage.setTitle("Add Ingredient Usage");
        popupStage.initModality(Modality.APPLICATION_MODAL);

        TextField ingredientNameField = new TextField();
        ingredientNameField.setPromptText("Ingredient Name");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        TextField unitField = new TextField();
        unitField.setPromptText("Unit");

        Button addButton = new Button("Add");
        Button cancelButton = new Button("Cancel");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        grid.add(new Label("Ingredient Name:"), 0, 0);
        grid.add(ingredientNameField, 1, 0);
        grid.add(new Label("Amount:"), 0, 1);
        grid.add(amountField, 1, 1);
        grid.add(new Label("Unit:"), 0, 2);
        grid.add(unitField, 1, 2);

        HBox buttonBox = new HBox(10, addButton, cancelButton);
        grid.add(buttonBox, 1, 3);

        addButton.setOnAction(e -> {
            String name = ingredientNameField.getText().trim();
            String amountText = amountField.getText().trim();
            String unit = unitField.getText().trim();

            if (name.isEmpty() || amountText.isEmpty() || unit.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "All fields must be filled!", ButtonType.OK).showAndWait();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountText);
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Amount must be a number!", ButtonType.OK).showAndWait();
                return;
            }
            //to send everything to the server
            try {
                Ingredient ingredient = new Ingredient();
                ingredient.setName(name);

                Ingredient savedIngredient = ServerUtils.addIngredient(ingredient);

                IngredientUsage usage = new IngredientUsage();
                usage.setIngredient(savedIngredient);
                usage.setRecipe(selectedRecipe);
                usage.setAmount((int) amount);
                usage.setUnit(unit);

                IngredientUsage savedUsage = ServerUtils.addIngredientUsage(usage);

                Listingredients.getItems().add(savedUsage);
                popupStage.close();

            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Failed to add ingredient to server: " + ex.getMessage(), ButtonType.OK).showAndWait();
            }
        });

        cancelButton.setOnAction(e -> popupStage.close());

        Scene scene = new Scene(grid);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    public void addRecipeButton(){
        pc.ShowAddRecipe();
    }
}
