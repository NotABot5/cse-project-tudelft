package client.scenes;
import client.RecipeList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {


    private Stage primaryStage;
    private Scene recipeList;
    private Scene addRecipe;

    public void initialize(Stage primaryStage, Pair<RecipeListCtrl, Parent> list, Pair<AddRecipeCtrl, Parent> adding) {
        this.primaryStage = primaryStage;
        this.recipeList = new Scene(list.getValue());
        this.addRecipe = new Scene(adding.getValue());
        ShowList();
        primaryStage.show();

    }

    public void ShowList(){
        primaryStage.setScene(recipeList);
        primaryStage.show();
    }

    public void ShowAddRecipe(){
        primaryStage.setScene(addRecipe);
        primaryStage.show();
    }

}
