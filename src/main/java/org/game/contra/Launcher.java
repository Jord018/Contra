package org.game.contra;

import javafx.application.Application;
import javafx.stage.Stage;
import org.game.contra.screens.ScreenOne;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create the first screen
        ScreenOne screenOne = new ScreenOne();
        
        // Set up the stage
        primaryStage.setTitle("Contra Game");
        primaryStage.setScene(screenOne.getScene());
        primaryStage.show();
    }
}