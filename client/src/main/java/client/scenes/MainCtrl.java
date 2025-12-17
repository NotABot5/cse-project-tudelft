package client.scenes;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
public class MainCtrl {


    private Stage primaryStage;
    private Scene recipeList;
    private Scene addRecipe;
    private Scene ingredientList;
    private RecipeListCtrl recipeListCtrl;

    public void initialize(Stage primaryStage,
                           Pair<IngredientListCtrl, Parent> ingredientList,
                           Pair<RecipeListCtrl, Parent> list,
                           Pair<AddRecipeCtrl, Parent> adding)
    {
        this.primaryStage = primaryStage;
        this.recipeList = new Scene(list.getValue());
        this.recipeListCtrl = list.getKey();
        this.addRecipe = new Scene(adding.getValue());
        this.ingredientList = new Scene(ingredientList.getValue());
        showList();
        primaryStage.show();

    }

    /**
     * Show list of all recipes
     */
    public void showList(){
        primaryStage.setTitle("Recipe list");
        recipeListCtrl.refreshAll();
        primaryStage.setScene(recipeList);
        primaryStage.show();
    }

    /**
     * Show scene with adding recipes
     */
    public void showAddRecipe(){
        primaryStage.setTitle("Add recipe view");
        primaryStage.setScene(addRecipe);
        primaryStage.show();
    }

    /**
     * Show scene with ingredient list
     */
    public void showIngredientList() {
        primaryStage.setTitle("Ingredient list");
        primaryStage.setScene(ingredientList);
        primaryStage.show();
    }

    public File getUserSelectedFile(FileChooser fc) {
        return fc.showSaveDialog(primaryStage);
    }
}
