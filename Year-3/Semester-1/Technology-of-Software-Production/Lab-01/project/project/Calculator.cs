using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace project
{
    class Calculator
    {

        public static double AddNumbers(double a, double b)
        {
            return a + b;
        }

        public static double Calculate(string operation, double a, double b)
        {
            switch (operation)
            {
                case "+":
                    return a + b;
                case "-":
                    return a - b;
                case "*":
                    return a * b;
                case "/":
                    if (b == 0)
                    {
                        throw new DivideByZeroException("Cannot divide by zero.");
                    }
                    return a / b;
                default:
                    throw new ArgumentException("Invalid operation.");
            }
        }
    }
}