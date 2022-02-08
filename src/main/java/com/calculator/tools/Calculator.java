package com.calculator.tools;

import javafx.util.Pair;

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
        equation = equation.replaceAll(" ", "");
        char[] tokens = equation.toCharArray();
        // Stack for numbers
        Stack<Float> values = new Stack<>();
        // Stack for operators
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            char curr = tokens[i];

            // Skip if current character is a whitespace
            if (curr == ' ') continue;

            // Deal with numbers
            if (curr >= '0' && curr <= '9') {
                StringBuilder buffer = new StringBuilder();
                // Parse full number
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.'))
                    buffer.append(tokens[i++]);
                values.push(Float.parseFloat(buffer.toString()));
                i--;
            } else if (curr == '(')
                operators.push(curr);
            else if (curr == ')') {
                // Solve entire brace
                while (!operators.empty() && operators.peek() != '(') {
                    Pair<Stack<Float>, Stack<Character>> stacks = applyOperation(operators.pop(), values, operators);
                    values = stacks.getKey();
                    operators = stacks.getValue();
                }
                if (operators.empty())
                    throw new IllegalArgumentException("Invalid expression");
                else
                    operators.pop(); // Take out the opening bracket
            } else if (curr == '+' || curr == '-' || curr == '*' || curr == '/') {
                if (i >= tokens.length - 1)
                    throw new IllegalArgumentException("Incorrect syntax: " + curr);
                // Evaluate previous operators with higher or same precedence
                while (!operators.empty() && hasPrecedence(curr, operators.peek())) {
                    Pair<Stack<Float>, Stack<Character>> stacks = applyOperation(operators.pop(), values, operators);
                    values = stacks.getKey();
                    operators = stacks.getValue();
                }
                operators.push(curr);
            } else
                throw new IllegalArgumentException("Unknown symbol: " + curr);
        }

        // Apply remaining operators to remaining operators
        while (!operators.empty()) {
            Pair<Stack<Float>, Stack<Character>> stacks = applyOperation(operators.pop(), values, operators);
            values = stacks.getKey();
            operators = stacks.getValue();
        }

        return values.empty() || values.peek() == -0f ? 0 : values.pop();
    }

    /*
     * Compares two operators based on their precedence. True if operator2 has higher or same precedence as operator1, otherwise returns false
     */
    private static boolean hasPrecedence(char operator1, char operator2) {
        if (operator1 == '-' && operator2 == '(')
            return false;
        if (operator2 == '(' || operator2 == ')')
            return false;
        return !((operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-'));
    }

    /*
     * Applies mathematical operator to a and b
     */
    private static Pair<Stack<Float>, Stack<Character>> applyOperation(char operator, Stack<Float> values, Stack<Character> operators)
            throws UnsupportedOperationException, IllegalArgumentException {
        if (values.empty())
            throw new IllegalArgumentException("Invalid syntax");
        else if (values.size() == 1) {
            if (operator == '-')
                values.push((-1) * values.pop());
            else
                throw new IllegalArgumentException("Invalid syntax");
        } else {
            switch (operator) {
                case '+':
                    values.push(values.pop() + values.pop());
                    break;
                case '*':
                    values.push(values.pop() * values.pop());
                    break;
                case '/':
                    float b = values.pop(), a = values.pop();
                    if (b == 0)
                        throw new UnsupportedOperationException("Cannot divide by zero");
                    else
                        values.push(a / b);
                    break;
                case '-':
                    if (!operators.empty() && operators.peek() == '(')
                        values.push((-1) * values.pop());
                    else {
                        float second = values.pop();
                        values.push(values.pop() - second);
                    }

                    break;
                default:
                    throw new UnsupportedOperationException("Unknown operation: " + operator);
            }
        }
        return new Pair<>(values, operators);
    }
}
