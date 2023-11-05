using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace project
{
    class NumberValidator
    {
        public static bool TryParseTextBoxesToDoubles(TextBox textBox1, TextBox textBox2, out double value1, out double value2)
        {
            bool valid1 = double.TryParse(textBox1.Text, out value1);
            bool valid2 = double.TryParse(textBox2.Text, out value2);

            return valid1 && valid2;
        }
    }
}
