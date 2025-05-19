import java.util.*;
import java.util.regex.*;
import java.lang.Math;

public class AdvancedCalculator {
    // Color Codes
    private static final String RESET = "\033[0m";
    private static final String GREEN = "\033[1;32m"; // Success
    private static final String RED = "\033[1;31m"; // Error
    private static final String CYAN = "\033[1;36m"; // User Input
    private static final String YELLOW = "\033[1;33m"; // Warnings

    private static double result = 0.0;
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Double> variables = new HashMap<>();

    public static void main(String[] args) {
        System.out.println(YELLOW + "Welcome to Advanced Scientific Calculator!" + RESET);
        System.out.println(YELLOW + "Enter expressions (type 'exit' to quit)" + RESET);
        System.out.println(YELLOW + "Examples: sin(80)+cos(60), 5+4+9, (5+4)*(6/7)" + RESET);
        System.out.println(YELLOW + "Functions: sin, cos, tan, sqrt, log, exp, etc." + RESET);

        while (true) {
            System.out.print(CYAN + "> " + RESET);
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println(YELLOW + "Exiting Calculator... Bye! 🚀" + RESET);
                break;
            } else if (input.equalsIgnoreCase("clear")) {
                result = 0.0;
                System.out.println(GREEN + "Result Cleared!" + RESET);
                continue;
            } else if (input.startsWith("save ")) {
                saveVariable(input.substring(5));
                continue;
            } else if (input.startsWith("recall ")) {
                recallVariable(input.substring(7));
                continue;
            } else if (input.equalsIgnoreCase("recall")) {
                displayAllVariables();
                continue;
            } else if (input.equalsIgnoreCase("help")) {
                displayHelp();
                continue;
            }

            try {
                if (input.isEmpty()) {
                    System.out.println(RED + "Invalid input! Please enter an expression." + RESET);
                    continue;
                }

                // Handle using previous result with operators
                if (input.matches("^[+\\-*/%^].*")) {
                    input = result + input;
                }

                // Replace variables in the expression
                input = replaceVariables(input);

                // Evaluate the expression
                result = evaluateExpression(input);
                System.out.println(GREEN + "Result: " + result + RESET);
            } catch (Exception e) {
                System.out.println(RED + "Error: " + e.getMessage() + RESET);
            }
        }
    }

    // Function to display help
    private static void displayHelp() {
        System.out.println(YELLOW + "=== Scientific Calculator Help ===" + RESET);
        System.out.println(CYAN + "Basic Operations: + - * / % ^" + RESET);
        System.out.println(CYAN + "Functions: sin, cos, tan, sqrt, log, exp" + RESET);
        System.out.println(CYAN + "Constants: pi, e" + RESET);
        System.out.println(CYAN + "Commands:" + RESET);
        System.out.println(CYAN + "  save [name] - Save current result to variable" + RESET);
        System.out.println(CYAN + "  recall - Show all saved variables" + RESET);
        System.out.println(CYAN + "  recall [name] - Show specific variable value" + RESET);
        System.out.println(CYAN + "  clear - Reset current result to 0" + RESET);
        System.out.println(CYAN + "  exit - Quit the calculator" + RESET);
        System.out.println(CYAN + "  help - Display this help" + RESET);
    }

    // Function to save variables
    private static void saveVariable(String input) {
        String[] parts = input.split("\\s+", 2);
        String varName = parts[0];

        if (!varName.matches("[a-zA-Z][a-zA-Z0-9]*")) {
            System.out
                    .println(RED + "Invalid variable name! Start with a letter, use only letters and numbers." + RESET);
            return;
        }

        // If there's a value provided, evaluate it
        if (parts.length > 1 && !parts[1].isEmpty()) {
            try {
                double value = evaluateExpression(parts[1]);
                variables.put(varName, value);
                System.out.println(GREEN + "Saved: " + varName + " = " + value + RESET);
            } catch (Exception e) {
                System.out.println(RED + "Error evaluating expression: " + e.getMessage() + RESET);
            }
        } else {
            // Otherwise save the current result
            variables.put(varName, result);
            System.out.println(GREEN + "Saved: " + varName + " = " + result + RESET);
        }
    }

    // Function to recall variables
    private static void recallVariable(String varName) {
        if (variables.containsKey(varName)) {
            result = variables.get(varName); // Set result to recalled value
            System.out.println(GREEN + "Recalled: " + varName + " = " + variables.get(varName) + RESET);
        } else {
            System.out.println(RED + "Error: Variable not found!" + RESET);
        }
    }

    // Display all saved variables
    private static void displayAllVariables() {
        if (variables.isEmpty()) {
            System.out.println(RED + "No saved variables!" + RESET);
        } else {
            System.out.println(GREEN + "Saved Variables:" + RESET);
            variables.forEach((key, value) -> System.out.println(CYAN + key + " = " + value + RESET));
        }
    }

    // Replace variables in expressions
    private static String replaceVariables(String expression) {
        // Replace constants first
        expression = expression.replaceAll("\\bpi\\b", String.valueOf(Math.PI));
        expression = expression.replaceAll("\\be\\b", String.valueOf(Math.E));

        // Replace user variables
        for (Map.Entry<String, Double> entry : variables.entrySet()) {
            String regex = "\\b" + entry.getKey() + "\\b";
            expression = expression.replaceAll(regex, entry.getValue().toString());
        }

        return expression;
    }

    // Main expression evaluation function
    private static double evaluateExpression(String expression) {
        // Remove all spaces
        expression = expression.replaceAll("\\s+", "");

        try {
            return parseExpression(expression);
        } catch (Exception e) {
            throw new RuntimeException("Invalid expression: " + e.getMessage());
        }
    }

    // Parse expression using recursive descent parser
    private static double parseExpression(String expression) {
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }

        // Parse addition and subtraction
        int pos = findLastOperator(expression, "+-");
        if (pos > 0) {
            String leftExpr = expression.substring(0, pos);
            String rightExpr = expression.substring(pos + 1);
            if (expression.charAt(pos) == '+') {
                return parseExpression(leftExpr) + parseExpression(rightExpr);
            } else {
                return parseExpression(leftExpr) - parseExpression(rightExpr);
            }
        }

        // Parse multiplication, division and modulo
        pos = findLastOperator(expression, "*/%");
        if (pos > 0) {
            String leftExpr = expression.substring(0, pos);
            String rightExpr = expression.substring(pos + 1);
            if (expression.charAt(pos) == '*') {
                return parseExpression(leftExpr) * parseExpression(rightExpr);
            } else if (expression.charAt(pos) == '/') {
                double divisor = parseExpression(rightExpr);
                if (divisor == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return parseExpression(leftExpr) / divisor;
            } else { // %
                return parseExpression(leftExpr) % parseExpression(rightExpr);
            }
        }

        // Parse power
        pos = findLastOperator(expression, "^");
        if (pos > 0) {
            String leftExpr = expression.substring(0, pos);
            String rightExpr = expression.substring(pos + 1);
            return Math.pow(parseExpression(leftExpr), parseExpression(rightExpr));
        }

        // Handle parentheses
        if (expression.startsWith("(") && expression.endsWith(")")) {
            return parseExpression(expression.substring(1, expression.length() - 1));
        }

        // Handle functions
        if (expression.contains("(")) {
            return parseFunction(expression);
        }

        // Parse number
        try {
            return Double.parseDouble(expression);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number: " + expression);
        }
    }

    // Find the last operator outside of parentheses
    private static int findLastOperator(String expression, String operators) {
        int parenthesesCount = 0;

        // Look from right to left
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);

            if (c == ')') {
                parenthesesCount++;
            } else if (c == '(') {
                parenthesesCount--;
            } else if (parenthesesCount == 0 && operators.indexOf(c) >= 0) {
                // Make sure it's not a unary + or - by checking previous char
                if ((c == '+' || c == '-') && i > 0) {
                    char prev = expression.charAt(i - 1);
                    // If previous char is an operator or '(', then this is a unary operator
                    if ("+-*/%(^".indexOf(prev) >= 0) {
                        continue;
                    }
                }
                return i;
            }
        }

        return -1;
    }

    // Parse function (sin, cos, etc.)
    private static double parseFunction(String expression) {
        int openParenPos = expression.indexOf('(');
        if (openParenPos <= 0) {
            throw new IllegalArgumentException("Invalid function call format");
        }

        String funcName = expression.substring(0, openParenPos).toLowerCase();

        // Find the matching closing parenthesis
        int closeParenPos = findMatchingParenthesis(expression, openParenPos);
        if (closeParenPos == -1) {
            throw new IllegalArgumentException("Mismatched parentheses");
        }

        // Extract and evaluate the arguments
        String argsStr = expression.substring(openParenPos + 1, closeParenPos);
        double arg = parseExpression(argsStr);

        // Apply the function
        switch (funcName) {
            case "sin":
                return Math.sin(Math.toRadians(arg));
            case "cos":
                return Math.cos(Math.toRadians(arg));
            case "tan":
                return Math.tan(Math.toRadians(arg));
            case "sqrt":
                if (arg < 0) {
                    throw new ArithmeticException("Cannot take square root of negative number");
                }
                return Math.sqrt(arg);
            case "log":
                if (arg <= 0) {
                    throw new ArithmeticException("Cannot take log of non-positive number");
                }
                return Math.log10(arg);
            case "ln":
                if (arg <= 0) {
                    throw new ArithmeticException("Cannot take ln of non-positive number");
                }
                return Math.log(arg);
            case "exp":
                return Math.exp(arg);
            case "abs":
                return Math.abs(arg);
            case "floor":
                return Math.floor(arg);
            case "ceil":
                return Math.ceil(arg);
            case "round":
                return Math.round(arg);
            case "asin":
                if (arg < -1 || arg > 1) {
                    throw new ArithmeticException("Argument for asin must be in range [-1, 1]");
                }
                return Math.toDegrees(Math.asin(arg));
            case "acos":
                if (arg < -1 || arg > 1) {
                    throw new ArithmeticException("Argument for acos must be in range [-1, 1]");
                }
                return Math.toDegrees(Math.acos(arg));
            case "atan":
                return Math.toDegrees(Math.atan(arg));
            default:
                throw new IllegalArgumentException("Unknown function: " + funcName);
        }
    }

    // Find matching closing parenthesis
    private static int findMatchingParenthesis(String expr, int openPos) {
        int count = 1;
        for (int i = openPos + 1; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
                if (count == 0) {
                    return i;
                }
            }
        }
        return -1;
    }
}
