package backend;

import com.calculator.tools.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorTests {

    /**
     * Tests the behaviour of positive numbers
     */
    @Test
    public void testPositiveNumbers() {
        String[] expressions = {"2", "(450)", "13.9", "0.112", ""};
        float[] results = {2, 450, (float) 13.9, (float) 0.112, 0};
        for (int i = 0; i < expressions.length; i++)
            Assertions.assertEquals(results[i],
                    Calculator.evaluateEquation(expressions[i]), "Invalid Number Conversion");
    }

    /**
     * Tests the behaviour of negative numbers
     */
    @Test
    public void testNegativeNumbers() {
        String[] expressions = {"-15", "(-192)", "-(0.862)", "- 9912"};
        float[] results = {-15, -192, (float) -0.862, -9912};
        for (int i = 0; i < expressions.length; i++)
            Assertions.assertEquals(results[i],
                    Calculator.evaluateEquation(expressions[i]), "Invalid Number Conversion");
    }

    /**
     * Tests the behaviour of multi negation
     */
    @Test
    public void testMultiNegation() {
        String[] expressions = {"-(-13)", "(-2) * (-(-(2)))", "-(-(-(-(-11))))"};
        float[] results = {13, -4, -11};
        for (int i = 0; i < expressions.length; i++)
            Assertions.assertEquals(results[i],
                    Calculator.evaluateEquation(expressions[i]), "Invalid Multi Negation");
    }

    /**
     * Tests the robustness of the calculator with unknown operators
     */
    @Test
    public void testInvalidCharacters() {
        String[] expressions = {"a", "2sdw1", "1 * a", "12-32sg", "(s _1)", "-a", "\u8912"};
        for (int i = 0; i < expressions.length; i++) {
            int finalI = i;
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> Calculator.evaluateEquation(expressions[finalI]), "Exception not thrown");
        }
    }

    /**
     * Tests addition
     */
    @Test
    public void testNormalAddition() {
        String[] expressions = {"2+2", "6.4 + 11", "(-11) + 13", "23 + 1 + 9.2 + 312", "11 + 2 + (-25)", "(-10) + (-321)"};
        float[] results = {4, (float) 17.4, 2, (float) 345.2, -12, -331};
        for (int i = 0; i < expressions.length; i++)
            Assertions.assertEquals(results[i],
                    Calculator.evaluateEquation(expressions[i]), "Invalid Addition Result");
    }

    /**
     * Tests subtraction
     */
    @Test
    public void testNormalSubtraction() {
        String[] expressions = {"2-2", "6.4 - 11", "(-11) - 13", "23 - 1 + 9.2 - 312", "11 - 2 + (-25)", "(-10) - (-321)"};
        float[] results = {0, (float) -4.6, -24, (float) -280.8, -16, 311};
        for (int i = 0; i < expressions.length; i++)
            Assertions.assertEquals(results[i],
                    Calculator.evaluateEquation(expressions[i]), "Invalid Subtraction Result");
    }

    /**
     * Tests multiplication
     */
    @Test
    public void testNormalMultiplication() {
        String[] expressions = {"2*2", "3 * (2 * 3)", "3 * 2 * 3", "102 * (-901)", "(-120) * (-21)", "0 * (-123) * 913"};
        float[] results = {4, 18, 18, -91902, 2520, 0};
        for (int i = 0; i < expressions.length; i++)
            Assertions.assertEquals(results[i],
                    Calculator.evaluateEquation(expressions[i]), "Invalid Multiplication Result");
    }

    /**
     * Tests division
     */
    @Test
    public void testNormalDivision() {
        String[] expressions = {"2/2", "4/500", "0 / 124112", "(-1912) / 9120", "(-123) / (-991)", "1238 / (-13)"};
        float[] results = {1, (float) 0.008, 0, (float) (-1912) / 9120, (float) (-123) / (-991), (float) 1238 / (-13)};
        for (int i = 0; i < expressions.length; i++)
            Assertions.assertEquals(results[i],
                    Calculator.evaluateEquation(expressions[i]), "Invalid Division Result");
    }

    /**
     * Tests breaking the division
     */
    @Test
    public void testErrorDivision() {
        String[] expressions = {"0 / 0", "(-12) / 0", "123 / 0", "12 * 391 / 0 * 123"};
        for (int i = 0; i < expressions.length; i++) {
            int finalI = i;
            Assertions.assertThrows(UnsupportedOperationException.class,
                    () -> Calculator.evaluateEquation(expressions[finalI]), "Exception not thrown");
        }
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Calculator.evaluateEquation("\u00B6 / 138"), "Exception not thrown");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Calculator.evaluateEquation("ad / 12"), "Exception not thrown");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Calculator.evaluateEquation("(-123) / 1f38"), "Exception not thrown");
    }

    /**
     * Tests precedence of the operator
     */
    @Test
    public void testNormalPrecedence() {
        String[] expressions = {"2 + 2 * 2", "901 * 52 + 11 / 4", "(-321) + 221 / (-31) * (-813)",
                "0 / 13 + 12 * 3", "10 + 2 * 6", "100 * 2 + 12"};
        float[] results = {6, (float) 46854.75, (float) 221 / 25203 - 321, 36, 22, 212};
        for (int i = 0; i < expressions.length; i++)
            Assertions.assertEquals(results[i],
                    Calculator.evaluateEquation(expressions[i]), "Invalid Precedence in calculation");
    }

    /**
     * Tests precedence of the operators in expressions with brackets
     */
    @Test
    public void testPrecedenceWithBrackets() {
        String[] expressions = {"100 * ( 2 + 12 )", "100 * ( 2 + 12 ) / 14", "0 * (123 - 123 * 12) + 12 / 6",
                "891 / ((123  * 34) + 92) + 813 * 5", "(((-312) * (823)) / 2431) + 2"};
        float[] results = {1400, 100, 2, (float) 891 / ((123 * 34) + 92) + 813 * 5, (float) (((-312) * (823)) / 2431) + 2};
        for (int i = 0; i < expressions.length; i++)
            Assertions.assertEquals(results[i],
                    Calculator.evaluateEquation(expressions[i]), "Invalid Precedence in calculation");
    }

    /**
     * Tests breaking the division
     */
    @Test
    public void testErrorExpressions() {
        String[] expressions = {"+ 31", "672 * 23 -", "(-31 ) + 2)", "9231 / / 123", "032.12 / * 112"};
        for (int i = 0; i < expressions.length; i++) {
            int finalI = i;
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> Calculator.evaluateEquation(expressions[finalI]), "Exception not thrown");
        }
    }
}

