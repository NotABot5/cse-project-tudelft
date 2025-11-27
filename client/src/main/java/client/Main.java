package client;

import commons.Recipe;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        var recipeListView = new ListView<Recipe>();

        List<Recipe> fakeRecipes = new ArrayList<>();
        fakeRecipes.add(new Recipe("Spaghetti Carbonara", "en", new ArrayList<>()));
        fakeRecipes.add(new Recipe("Chicken Noodle Soup", "en", new ArrayList<>()));

        recipeListView.getItems().setAll(FXCollections.observableArrayList(fakeRecipes));


        var mainLayout = new VBox();
        mainLayout.getChildren().addAll(recipeListView);

        var scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Food Backlog");
        primaryStage.show();
    }
}
