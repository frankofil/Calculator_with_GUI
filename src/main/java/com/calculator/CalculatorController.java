package com.calculator;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Class responsible for controlling the behaviour of the calculator app
 *
 * @author Franciszek Myslek
 * @version 0.1
 */
public class CalculatorController {
    @FXML
    private Label textDisplayBox;

    @FXML
    protected void onTestButtonClick() {
        textDisplayBox.setText("My test is working");
    }
}
