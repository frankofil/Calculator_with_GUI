package com.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class for starting the Calculator app
 *
 * @author Franciszek Myslek
 * @version 0.1
 */
public class CalculatorApplication extends Application {

    private static final int windowWidth = 400;
    private static final int windowHeight = 640;

    private static final String windowTitle = "Calculator";

    /**
     * Sets the first scene of the calculator app and loads the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CalculatorApplication.class.getResource("calculator-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), windowWidth, windowHeight);
        stage.setTitle(windowTitle);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Starts the calculator app
     */
    public static void main(String[] args) {
        launch();
    }
}