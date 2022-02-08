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

    /**
     * Turns on DEBUG Mode to see all incoming signals, evaluations, and translations of arithmetic expressions
     */
    public final boolean DEBUG_MODE = true;

    @FXML
    private Label resultDisplay;
    @FXML
    private Label equationDisplay;

    private final Calculator calculator;

    /**
     * Constructor for initializing the Calculator Controller
     */
    public CalculatorController() {
        calculator = new Calculator();
    }

    /**
     * Action executed after pressing a button. Interacting with the calculator's GUI
     */
    @FXML
    protected void buttonPressed(ActionEvent event) {
        if (DEBUG_MODE)
            System.out.println("Button " + ((Button) event.getSource()).getText() + " pressed");
        // Record new signal and update the displays
        calculator.recordSignal(((Button) event.getSource()).getText().charAt(0));
        resultDisplay.setText(calculator.getDisplay());
        equationDisplay.setText(calculator.getEquation());
    }
}
