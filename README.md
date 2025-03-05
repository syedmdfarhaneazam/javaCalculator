# 📌 Advanced Scientific Calculator in Java  

![Java Logo](https://upload.wikimedia.org/wikipedia/en/3/30/Java_programming_language_logo.svg)

### 🧮 A powerful **Scientific Calculator** built in Java with support for **trigonometric, logarithmic, arithmetic, and factorial operations**.

---

## 🚀 Features
✅ Supports **Basic Arithmetic Operations** (`+`, `-`, `*`, `/`, `%`, `^`)
✅ **Trigonometric Functions** (`sin`, `cos`, `tan`, `sec`, `cosec`, `cot`)
✅ **Logarithmic Functions** (`log`, `ln`)
✅ **Square Root Calculation** (`sqrt`)
✅ **Factorial Calculation** (`!`)
✅ **Memory Storage** (`save <var>`, `recall <var>`, `clear`)
✅ **Supports Step-by-Step Computation**
✅ **Error Handling for Invalid Inputs**

---

## 📖 Usage Manual
### **Commands and Operations**
```
Basic Arithmetic:
  - Addition (+), Subtraction (-), Multiplication (*), Division (/)
  - Power (^), Modulo (%)

Trigonometry:
  - sin(angle), cos(angle), tan(angle)
  - sec(angle), cosec(angle), cot(angle)

Logarithms:
  - log(value) -> Base 10
  - ln(value)  -> Natural Logarithm

Special Functions:
  - sqrt(value) -> Square Root
  - n! -> Factorial

Memory Operations:
  - save <var>  -> Saves the current result in memory
  - recall <var> -> Retrieves saved value
  - clear -> Resets calculator
  - exit  -> Closes the calculator
```

---

## 🖥️ Example Outputs
```
Advanced Scientific Calculator
Operations: +, -, *, /, ^, %, sqrt, !
Trigonometric: sin, cos, tan, sec, cosec, cot
Logarithmic: log, ln
Commands: save <var>, recall <var>, clear, exit
```
### 📌 **Basic Calculation**
```
Enter number or expression: 25 / 5
Result: 5.0
Enter number or expression: save myValue
Saved: 5.0
Enter number or expression: sqrt(recall myValue)
Result: 2.23606797749979
Enter number or expression: clear
Memory cleared.
```
### 📌 **Trigonometric Operations**
```
Enter number or expression: sin(30)
Result: 0.5
Enter number or expression: cos(60)
Result: 0.5
```
### 📌 **Complex Expression**
```
Enter number or expression: sqrt(144) + sin(45) * 10
Result: 19.071
```
### 📌 **Factorial Calculation**
```
Enter number or expression: 5!
Result: 120.0
```
### 📌 **Logarithmic Operations**
```
Enter number or expression: log(1000)
Result: 3.0
Enter number or expression: ln(2.718)
Result: 1.0
```

---

## 🔧 How to Run
1️⃣ **Compile the Code:**
```sh
javac AdvancedCalculator.java
```

2️⃣ **Run the Program:**
```sh
java AdvancedCalculator
```

3️⃣ **Create an Alias in your ```.bashrc``` or ```.zshrc``` file:**
paste->
```sh
alias runcalc='cd /path/to/your/directory && java AdvancedCalculator'
```

4️⃣  **Run it when you want:**
```sh
$ runcalc
```
---

## 📜 License
This project is **open-source** and free to use. Contributions are welcome! 😃

---

## 💡 Future Improvements
🚀 **Support for Parentheses in Expressions**
🚀 **Graph Plotting for Functions**
🚀 **GUI Version using JavaFX**

---

**👨‍💻 Created in Java**

