using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace project
{
    class NumberValidator
    {

        public static bool ValidateNumericInput(string input)
        {
            double digit;
            return double.TryParse(input, out digit);
        }
    }
}
