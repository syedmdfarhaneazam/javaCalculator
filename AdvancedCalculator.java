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
    System.out.println(YELLOW + "Welcome to Advanced Calculator!" + RESET);
    System.out.println(YELLOW + "Enter expressions (type 'exit' to quit)" + RESET);

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
      }

      try {
        if (input.isEmpty()) {
          System.out.println(RED + "Invalid input! Please enter an expression." + RESET);
          continue;
        }

        if (input.matches("^[+\\-*/%^].*")) {
          input = result + input;
        }

        result = evaluateExpression(input);
        System.out.println(GREEN + "Result: " + result + RESET);
      } catch (Exception e) {
        System.out.println(RED + "Error: Invalid expression! Please try again." + RESET);
      }
    }
  }

  // Function to save variables
  private static void saveVariable(String varName) {
    if (!varName.matches("[a-zA-Z]+")) {
      System.out.println(RED + "Invalid variable name!" + RESET);
      return;
    }
    variables.put(varName, result);
    System.out.println(GREEN + "Saved: " + varName + " = " + result + RESET);
  }

  // Function to recall variables
  private static void recallVariable(String varName) {
    if (variables.containsKey(varName)) {
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

  // Function to evaluate expressions
  private static double evaluateExpression(String expression) {
    expression = replaceVariables(expression); // Replace variables with values
    expression = expression.replaceAll("\\s+", "");

    while (expression.contains("(")) {
      Matcher matcher = Pattern.compile("\\(([^()]+)\\)").matcher(expression);
      while (matcher.find()) {
        String subExpr = matcher.group(1);
        double subResult = evaluateSimpleExpression(subExpr);
        expression = expression.replace("(" + subExpr + ")", String.valueOf(subResult));
      }
    }

    return evaluateSimpleExpression(expression);
  }

  // Replace variables in expressions
  private static String replaceVariables(String expression) {
    for (String var : variables.keySet()) {
      expression = expression.replace(var, variables.get(var).toString());
    }
    return expression;
  }

  // Simple expressions evaluate karne ka function
  private static double evaluateSimpleExpression(String expression) {
    Pattern pattern = Pattern.compile("([-+*/%^]?\\d+(\\.\\d+)?|sin\\(\\d+\\)|cos\\(\\d+\\)|sqrt\\(\\d+\\))");
    Matcher matcher = pattern.matcher(expression);
    List<String> tokens = new ArrayList<>();

    while (matcher.find()) {
      tokens.add(matcher.group());
    }

    if (tokens.isEmpty()) {
      throw new IllegalArgumentException("Invalid Expression");
    }

    double result = evaluateToken(tokens.get(0));
    for (int i = 1; i < tokens.size(); i++) {
      String token = tokens.get(i);
      char operator = token.charAt(0);
      double value = evaluateToken(token.substring(1));

      switch (operator) {
        case '+':
          result += value;
          break;
        case '-':
          result -= value;
          break;
        case '*':
          result *= value;
          break;
        case '/':
          if (value == 0)
            throw new ArithmeticException("Division by zero!");
          result /= value;
          break;
        case '%':
          result %= value;
          break;
        case '^':
          result = Math.pow(result, value);
          break;
      }
    }
    return result;
  }

  // Evaluate token with functions
  private static double evaluateToken(String token) {
    if (token.matches("\\d+(\\.\\d+)?")) {
      return Double.parseDouble(token);
    } else if (token.startsWith("sin(")) {
      return Math.sin(Math.toRadians(extractNumber(token)));
    } else if (token.startsWith("cos(")) {
      return Math.cos(Math.toRadians(extractNumber(token)));
    } else if (token.startsWith("sqrt(")) {
      return Math.sqrt(extractNumber(token));
    }
    throw new IllegalArgumentException("Invalid Token: " + token);
  }

  // Extract number from function like sin(30)
  private static double extractNumber(String function) {
    return Double.parseDouble(function.replaceAll("[^0-9.]", ""));
  }
}
