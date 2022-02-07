package com.calculator.tools;

/**
 * Class representation of the calculator
 *
 * @author Franciszek Myslek
 * @version 0.1
 */
public class Calculator {

    private String equation;
    private String display;

    /**
     * Returns the equation that is currently being evaluated
     *
     * @return Equation that should be displayed
     */
    public String getEquation() {
        return this.equation;
    }

    /**
     * The information that should be displayed on the main screen (current input or calculated output)
     *
     * @return Information that should be displayed on the main screen
     */
    public String getDisplay() {
        return this.display;
    }

    public void recordSignal(char signal) {
        this.display = String.valueOf(signal);
    }
}
