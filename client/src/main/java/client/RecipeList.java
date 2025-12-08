package client;

import client.scenes.AddRecipeCtrl;
import client.scenes.MainCtrl;
import client.scenes.RecipeListCtrl;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;


public class RecipeList extends Application {
    private static final Injector INJECTOR = Guice.createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML (INJECTOR);


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
        var recipeList = FXML.load(RecipeListCtrl.class, "client", "recipeList.fxml");
        var addRecipe = FXML.load(AddRecipeCtrl.class, "client", "addRecipe.fxml");
        var pc = INJECTOR.getInstance(MainCtrl.class);
        pc.initialize(primaryStage, recipeList, addRecipe);
    }
}



