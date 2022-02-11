package com.calculator.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Class representation of the calculator
 *
 * @author Franciszek Myslek
 * @version 0.1
 */
public class Calculator {

    // ToDO
    private String _equation;
    private String _display;

    private final ArrayList<Character> specialOperators;

    /**
     * Constructor for initialising the calculator
     */
    public Calculator() {
        // All allowed special operators in the input
        Character[] specialOperatorsArray = {'%', '-', '+', '=', '\u00F7', '\u2A2F', '\u00B1', 'C'};
        specialOperators = new ArrayList<>(List.of(specialOperatorsArray));
    }

    /**
     * ToDo
     * Returns the equation that is currently being evaluated
     *
     * @return Equation that should be displayed
     */
    public String getEquation() {
        return _equation;
    }

    /**
     * The information that should be displayed on the main screen (current input or calculated output)
     *
     * @return Information that should be displayed on the main screen
     */
    public String getDisplay() {
        return _display;
    }

    /**
     * Send signal to the calculator about a new event
     *
     * @param signal character that will be added to the equation
     */
    public void recordSignal(char signal) throws IllegalArgumentException {
        // Catch unknown characters
        if (!((signal >= '0' && signal <= '9') || signal == '.' || specialOperators.contains(signal)))
            throw new IllegalArgumentException("Unknown symbol " + signal);
        // Evaluate updated equation
        _equation = _equation == null ? String.valueOf(signal) : _equation + signal;
        _display = String.valueOf(Calculator.evaluateEquation(this.translateEquation(_equation)));
    }

    /* ToDo
     * Reformats the input equation string into mathematically correct expressions
     */
    private String translateEquation(String equation) throws IllegalArgumentException {
        if (equation == null)
            throw new IllegalArgumentException("Incorrect format");
        return equation;
    }

    /**
     * Evaluates the arithmetic expression
     *
     * @param equation Mathematically correct arithmetic expression
     * @return The result of the evaluation
     * @author Improved algorithm from geeksforgeeks.org
     */
    public static Float evaluateEquation(String equation) throws IllegalArgumentException {
        char[] tokens = equation.replaceAll(" ", "").toCharArray();
        // Stack for numbers
        Stack<Float> values = new Stack<>();
        // Stack for operators
        Stack<Character> operators = new Stack<>();
        // Previous value for dealing with negative numbers
        char previous = 0;
        boolean negate = false;

        for (int i = 0; i < tokens.length; i++) {
            char curr = tokens[i];

            // Deal with numbers
            if (curr >= '0' && curr <= '9') {
                StringBuilder buffer = new StringBuilder();
                // Parse full number
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.'))
                    buffer.append(tokens[i++]);
                // Negate the value if necessary
                if (negate)
                    values.push((-1) * Float.parseFloat(buffer.toString()));
                else
                    values.push(Float.parseFloat(buffer.toString()));
                negate = false;
                curr = buffer.charAt(buffer.length() - 1);
                i--;
            } else if (curr == '(') {
                operators.push(curr);
            } else if (curr == ')') {
                // Solve entire brace
                while (!operators.empty() && hasPrecedence(curr, operators.peek()))
                    applyOperation(operators.pop(), values);
                if (operators.empty())
                    throw new IllegalArgumentException("Invalid expression");
                else
                    operators.pop(); // Take out the opening bracket
            } else if (curr == '+' || curr == '-' || curr == '*' || curr == '/') {
                // Check if the sign is at the end of the expression
                if (i >= tokens.length - 1)
                    throw new IllegalArgumentException("Incorrect syntax: " + curr);
                // Check if this '-' represents negative number
                if (curr == '-' && (previous == '\0' || previous == '(')) {
                    negate = !negate;
                } else {
                    // Evaluate previous operators with higher or same precedence
                    while (!operators.empty() && hasPrecedence(curr, operators.peek()))
                        applyOperation(operators.pop(), values);
                    operators.push(curr);
                }
            } else
                throw new IllegalArgumentException("Unknown symbol: " + curr);
            previous = curr;
        }

        // Apply remaining operators to remaining operators
        while (!operators.empty())
            applyOperation(operators.pop(), values);

        return values.empty() || values.peek() == -0f ? 0 : values.pop();
    }

    /*
     * Compares two operators based on their precedence. True if operator2 has higher or same precedence as operator1, otherwise returns false
     */
    private static boolean hasPrecedence(char operator1, char operator2) {
        if (operator2 == '(' || operator2 == ')')
            return false;
        return !((operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-'));
    }

    /*
     * Applies mathematical operator to a and b
     */
    private static void applyOperation(char operator, Stack<Float> values)
            throws UnsupportedOperationException, IllegalArgumentException {
        if (values.size() < 2)
            throw new IllegalArgumentException("Invalid syntax");
        else {
            switch (operator) {
                case '+' -> values.push(values.pop() + values.pop());
                case '-' -> {
                    float b = values.pop();
                    values.push(values.pop() - b);
                }
                case '*' -> values.push(values.pop() * values.pop());
                case '/' -> {
                    float b = values.pop(), a = values.pop();
                    if (b == 0)
                        throw new UnsupportedOperationException("Cannot divide by zero");
                    else
                        values.push(a / b);
                }
                default -> throw new UnsupportedOperationException("Unknown operation: " + operator);
            }
        }
    }
}
