package com.calculator;


import com.calculator.tools.Calculator;
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
    private Label equationDisplay;
    private final Calculator calculator;

    /**
     * Constructor for initializing the Calculator Controller
     */
    public CalculatorController() {
        this.calculator = new Calculator();
    }

    /**
     * Action executed after pressing a button. Interacting with the calculator's GUI
     */
    @FXML
    protected void buttonPressed(ActionEvent event) {
        System.out.println("Button " + ((Button) event.getSource()).getText() + " pressed");
        this.calculator.recordSignal(((Button) event.getSource()).getText().charAt(0));
        textDisplayBox.setText(this.calculator.getDisplay());
    }
}
