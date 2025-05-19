# Advanced Scientific Calculator

A powerful command-line scientific calculator written in Java.

## Features

- Basic arithmetic operations (+, -, \*, /, %, ^)
- Trigonometric functions (sin, cos, tan)
- Inverse trigonometric functions (asin, acos, atan)
- Other math functions (sqrt, log, ln, exp, abs, floor, ceil, round)
- Variable storage and recall
- Mathematical constants (pi, e)
- Expression parsing with proper operator precedence
- Support for parentheses and nested expressions
- Color-coded output

## Installation

### Automatic Installation (Linux/macOS)

1. Clone this repository:

    ```
    git clone https://github.com/syedmdfarhaneazam/javaCalculator
    cd javaCalculator/scripts
    ```

2. Run the installation script:

    ```
    ./install.sh
    ```

3. Use the calculator from any terminal:
    ```
    calculate
    ```

### Manual Installation

1. Compile the Java file:

    ```
    javac AdvancedCalculator.java
    ```

2. Make the shell script executable:

    ```
    chmod +x calculate
    ```

3. Move both files to a directory in your PATH or create a symlink:

    ```
    # Option 1: Move files
    sudo mv AdvancedCalculator.class /usr/local/bin/
    sudo mv calculate /usr/local/bin/

    # Option 2: Create a symlink
    ln -s $(pwd)/calculate ~/.local/bin/calculate
    ```

## Usage

Run the calculator:

```
calculate
```

### Example Commands

- Basic calculations:

    ```
    > 5+4+9
    > (5+4)*(6/7)
    ```

- Trigonometric functions:

    ```
    > sin(80)+cos(60)
    > tan(45)
    ```

- Other functions:

    ```
    > sqrt(16)
    > log(100)
    ```

- Using constants:

    ```
    > pi*2
    > e^2
    ```

- Variable management:
    ```
    > save x 10
    > save y 20
    > x+y
    > recall
    ```

### Commands

- `exit` - Quit the calculator
- `clear` - Reset current result to 0
- `save [name]` - Save current result to variable
- `save [name] [expression]` - Evaluate expression and save to variable
- `recall` - Show all saved variables
- `recall [name]` - Show specific variable value
- `help` - Display help menu

## Requirements

- Java Runtime Environment (JRE) 8 or higher

## License

MIT License
