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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.List;
import java.util.Optional;

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
    @FXML
    protected Button cloneButton;
    @FXML
    protected Label RecipeNameLabel;
    @FXML
    protected Label LanguageLabel;

    private MainCtrl pc;

    @Inject
    public RecipeListCtrl(MainCtrl m) {
        this.pc = m;
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
        //is called when the recipe is clicked, used AI to make it more compact
        table.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        loadIngredientsFromRecipe(newVal);
                    }
                }
        );
        //option to delete a ingredient from the ui and the database using delete-key on keyboard
        Listingredients.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {

                IngredientUsage selectedItem = Listingredients
                        .getSelectionModel()
                        .getSelectedItem();

                if (selectedItem != null) {
                    deleteItem(selectedItem); // je bevestigings-popup
                }
            }
        });

        // gets everything in a listview
        Listingredients.setCellFactory(lv -> new ListCell<>() {
            protected void updateItem(IngredientUsage item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getIngredient().getName() + " - " + item.getAmount() + " " + item.getUnit());
                } else {
                    setText("");
                }
            }
        });
    }
    /**
     * Gives a warning-popup when you want to delete a item from a recipe
     * Deletes item in UI and in database (server)
     *

     */

    private void deleteItem(IngredientUsage item) {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete one item");
        alert.setContentText("Are you sure you want to delete this item?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Listingredients.getItems().remove(item);
                ServerUtils.deleteIngredientUsage(item.getId());
            }
        });
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

        Listingredients.getItems().setAll(ingredientsRecipe);
        RecipeNameLabel.setText(recipe.getName());
        LanguageLabel.setText("Language: " + recipe.getLang());
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

        ChoiceBox<Ingredient> ingredientSelection = new ChoiceBox<>();
        List<Ingredient> ingredients = ServerUtils.getIngredients();
        ingredientSelection.setConverter(new StringConverter<Ingredient>() {
            @Override
            public String toString(Ingredient ingredient) {
                if(ingredient == null) {
                    return "Select ingredient";
                }
                return ingredient.getName();
            }

            @Override
            public Ingredient fromString(String s) {
                return ingredients.stream()
                        .filter((Ingredient ing) -> ing.getName().equals(s))
                        .findFirst()
                        .orElse(null);
            }
        });
        ingredientSelection.getItems().setAll(ingredients);

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
        grid.add(ingredientSelection, 1, 0);
        grid.add(new Label("Amount:"), 0, 1);
        grid.add(amountField, 1, 1);
        grid.add(new Label("Unit:"), 0, 2);
        grid.add(unitField, 1, 2);

        HBox buttonBox = new HBox(10, addButton, cancelButton);
        grid.add(buttonBox, 1, 3);

        addButton.setOnAction(e -> {
            Ingredient selectedIngredient = ingredientSelection.getValue();
            String amountText = amountField.getText().trim();
            String unit = unitField.getText().trim();

            if (selectedIngredient == null || amountText.isEmpty() || unit.isEmpty()) {
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
                IngredientUsage usage = new IngredientUsage();
                usage.setIngredient(selectedIngredient);
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

    public void refreshAll() {
        table.getItems().clear();
        loadRecipeTable();
        Listingredients.getItems().clear();
        RecipeNameLabel.setText("Nothing currently selected");
        LanguageLabel.setText("Language: N/A");
    }

    @FXML
    protected void refreshData(MouseEvent event) {
        refreshAll();
    }

    @FXML
    protected void cloneRecipe() {
        Recipe selectedRecipe = table.getSelectionModel().getSelectedItem();
        if (selectedRecipe == null) {
            new Alert(Alert.AlertType.WARNING, "Select a recipe to clone!", ButtonType.OK).showAndWait();
            return;
        }

        TextInputDialog dialog = new TextInputDialog("Cloned " + selectedRecipe.getName());
        dialog.setTitle("Clone Recipe");
        dialog.setHeaderText("Enter a new name for the cloned recipe");
        dialog.setContentText("New name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> {
            ServerUtils.cloneRecipe(selectedRecipe.getId(), newName);
            loadRecipeTable();
        });
    }
}
