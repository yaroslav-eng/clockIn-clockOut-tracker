package org.openjfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainJavaFX extends Application {

    Scene menuScene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        App app = new App();
        app.setController();
        menuScene = new Scene(app.grid, 600, 900);

        primaryStage.setTitle("Shift Tracker Application");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
