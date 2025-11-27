package commons;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class IngredientUI {

    private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
    private ListView<Ingredient> listView = new ListView<>(ingredients);

    private BorderPane root;

    public IngredientUI() {
        root = new BorderPane();

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");

        HBox controls = new HBox(10, addButton, editButton, deleteButton);
        controls.setPadding(new Insets(10));

        root.setTop(controls);
        root.setCenter(listView);

        addButton.setOnAction(e -> openIngredientDialog(null));

        editButton.setOnAction(e -> {
            Ingredient selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openIngredientDialog(selected);
            }
        });

        deleteButton.setOnAction(e -> {
            Ingredient selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                ingredients.remove(selected);
            }
        });
    }

    /**
     * Opens a dialog for adding or editing an ingredient.
     */
    private void openIngredientDialog(Ingredient ingredient) {
        Dialog<Ingredient> dialog = new Dialog<>();
        dialog.setTitle(ingredient == null ? "Add Ingredient" : "Edit Ingredient");

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        // Fields
        TextField nameField = new TextField(ingredient == null ? "" : ingredient.getName());
        TextField proteinField = new TextField(ingredient == null ? "" : String.valueOf(ingredient.getProtein()));
        TextField fatField = new TextField(ingredient == null ? "" : String.valueOf(ingredient.getFat()));
        TextField carbsField = new TextField(ingredient == null ? "" : String.valueOf(ingredient.getCarbs()));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);

        grid.add(new Label("Protein:"), 0, 1);
        grid.add(proteinField, 1, 1);

        grid.add(new Label("Fat:"), 0, 2);
        grid.add(fatField, 1, 2);

        grid.add(new Label("Carbs:"), 0, 3);
        grid.add(carbsField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == saveButton) {
                String name = nameField.getText();
                double protein = Double.parseDouble(proteinField.getText());
                double fat = Double.parseDouble(fatField.getText());
                double carbs = Double.parseDouble(carbsField.getText());

                if (ingredient == null) {
                    return new Ingredient(name, protein, fat, carbs);
                } else {
                    ingredient.setName(name);
                    ingredient.setProtein(protein);
                    ingredient.setFat(fat);
                    ingredient.setCarbs(carbs);
                    return ingredient;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            if (ingredient == null) {
                ingredients.add(result);
            } else {
                listView.refresh();
            }
        });
    }

    public BorderPane getRoot() {
        return root;
    }
}
