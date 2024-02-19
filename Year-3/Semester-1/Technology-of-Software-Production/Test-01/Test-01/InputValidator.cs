using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Test_01
{
    class InputValidator
    {
        public static bool isInputValid(string input)
        {
            for (int i = 0; i < input.Length; i++)
            {
                if (!Char.IsDigit(input[i]) && !Char.IsLetter(input[i]))
                {
                    return false;
                }
            }
            return true;
        }
    }
}
