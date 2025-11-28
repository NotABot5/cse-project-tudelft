package client;

import client.utils.ServerUtils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Use a ListView that displays simple Strings
        var recipeListView = new ListView<String>();

        // Fetch only the recipe names from the server using the new safe method
        List<String> recipeNames = ServerUtils.getRecipeNames();

        // Populate the list view
        recipeListView.getItems().setAll(FXCollections.observableArrayList(recipeNames));

        var mainLayout = new VBox();
        mainLayout.getChildren().add(recipeListView);

        var scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Recipe Browser");
        primaryStage.show();
    }
}
