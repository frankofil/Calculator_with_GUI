package com.calculator;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    protected void buttonPressed(ActionEvent event) {
        System.out.println("Button " + ((Button) event.getSource()).getText() + " pressed");
        textDisplayBox.setText(((Button) event.getSource()).getText());
    }
}
