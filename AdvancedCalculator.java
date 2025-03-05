import java.util.*;
import java.util.regex.*;
import java.lang.Math;

public class AdvancedCalculator {
  private static final Map<String, Double> memory = new HashMap<>();
  private static double result = 0.0;
  private static final Scanner scanner = new Scanner(System.in);
  private static String pendingOperator = null;

  public static void main(String[] args) {
    System.out.println("Advanced Scientific Calculator");
    System.out.println("Operations: +, -, *, /, ^ (power), % (modulo), sqrt, ! (factorial)");
    System.out.println("Trigonometric: sin, cos, tan, sec, cosec, cot");
    System.out.println("Logarithmic: log (base 10), ln (natural log)");
    System.out.println("Commands: save <var>, recall <var>, clear, exit");

    while (true) {
      System.out.print("Enter number or expression: ");
      String input = scanner.nextLine().trim();

      if (input.equalsIgnoreCase("exit")) {
        System.out.println("Exiting Calculator...");
        break;
      }

      if (input.equalsIgnoreCase("clear")) {
        result = 0;
        pendingOperator = null;
        System.out.println("Cleared. Start new calculation.");
        continue;
      }

      if (input.startsWith("save ")) {
        String varName = input.substring(5).trim();
        memory.put(varName, result);
        System.out.println("Saved: " + varName + " = " + result);
        continue;
      }

      if (input.startsWith("recall ")) {
        String varName = input.substring(7).trim();
        if (memory.containsKey(varName)) {
          result = memory.get(varName);
          System.out.println("Recalled: " + varName + " = " + result);
        } else {
          System.out.println("Error: Variable not found.");
        }
        continue;
      }
      // erroe catrch
      // try
      try {
        if (isNumeric(input)) {
          if (pendingOperator != null) {
            result = applyOperation(result, Double.parseDouble(input), pendingOperator);
            pendingOperator = null;
          } else {
            result = Double.parseDouble(input);
          }
        } else if (isOperator(input)) {
          pendingOperator = input;
          continue;
        } else {
          result = evaluateExpression(input);
        }
        System.out.println("Result: " + result);
      } catch (Exception e) {
        System.out.println("Error in expression.");
      }
    }
  }

  private static double evaluateExpression(String expression) throws Exception {
    expression = expression.toLowerCase();
    for (String key : memory.keySet()) {
      expression = expression.replace(key, memory.get(key).toString());
    }

    if (expression.matches("sin\\(.*\\)"))
      return Math.sin(Math.toRadians(extractNumber(expression)));
    if (expression.matches("cos\\(.*\\)"))
      return Math.cos(Math.toRadians(extractNumber(expression)));
    if (expression.matches("tan\\(.*\\)"))
      return Math.tan(Math.toRadians(extractNumber(expression)));
    if (expression.matches("sec\\(.*\\)"))
      return 1 / Math.cos(Math.toRadians(extractNumber(expression)));
    if (expression.matches("cosec\\(.*\\)"))
      return 1 / Math.sin(Math.toRadians(extractNumber(expression)));
    if (expression.matches("cot\\(.*\\)"))
      return 1 / Math.tan(Math.toRadians(extractNumber(expression)));
    if (expression.matches("log\\(.*\\)"))
      return Math.log10(extractNumber(expression));
    if (expression.matches("ln\\(.*\\)"))
      return Math.log(extractNumber(expression));
    if (expression.matches("sqrt\\(.*\\)"))
      return Math.sqrt(extractNumber(expression));
    if (expression.matches(".*!"))
      return factorial((int) extractNumber(expression));

    return evaluateMath(expression);
  }

  private static double evaluateMath(String expression) throws Exception {
    expression = expression.replaceAll("\\s+", "");
    expression = expression.replaceAll("--", "+");

    Pattern pattern = Pattern.compile("(-?\\d+(\\.\\d+)?)([+\\-*/%^])(-?\\d+(\\.\\d+)?)");
    Matcher matcher = pattern.matcher(expression);

    if (matcher.find()) {
      double num1 = Double.parseDouble(matcher.group(1));
      double num2 = Double.parseDouble(matcher.group(4));
      char operator = matcher.group(3).charAt(0);

      return applyOperation(num1, num2, String.valueOf(operator));
    }
    throw new Exception("Invalid expression");
  }

  private static double applyOperation(double num1, double num2, String operator) throws Exception {
    switch (operator) {
      case "+":
        return num1 + num2;
      case "-":
        return num1 - num2;
      case "*":
        return num1 * num2;
      case "/":
        if (num2 == 0)
          throw new ArithmeticException("Division by zero");
        return num1 / num2;
      case "%":
        return num1 % num2;
      case "^":
        return Math.pow(num1, num2);
      default:
        throw new Exception("Invalid operator");
    }
  }

  private static double extractNumber(String str) {
    Matcher matcher = Pattern.compile("-?\\d+(\\.\\d+)?").matcher(str);
    return matcher.find() ? Double.parseDouble(matcher.group()) : 0;
  }

  private static double factorial(int n) {
    return (n <= 1) ? 1 : n * factorial(n - 1);
  }

  private static boolean isNumeric(String str) {
    return str.matches("-?\\d+(\\.\\d+)?");
  }

  private static boolean isOperator(String str) {
    return str.matches("[+\\-*/%^]");
  }
}
