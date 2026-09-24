package main.java.com.jgunzalesindustries.banco.sahur;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.com.jgunzalesindustries.banco.sahur.util.SceneManager;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.showLoginView();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
