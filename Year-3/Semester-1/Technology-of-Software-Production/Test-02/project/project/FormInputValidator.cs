using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Text.RegularExpressions;


namespace project
{
    class FormInputValidator
    {

        public static bool IsUserUnputValid(ErrorProvider errorProvider1, TextBox textBox1, TextBox textBox2, TextBox textBox3, TextBox textBox4, TextBox textBox5)
        {
            bool isInputValid = true;


            if (!IsCyrillic(textBox1.Text) || !Char.IsUpper(textBox1.Text[0]))
            {
                errorProvider1.SetError(textBox1, "Please enter a first name using cyrillic characters only starting with uppercase letter.");
                isInputValid = false;
            }
            else
            {
                errorProvider1.SetError(textBox1, "");
            }

            if (!IsCyrillic(textBox2.Text) || !Char.IsUpper(textBox2.Text[0]))
            {
                errorProvider1.SetError(textBox2, "Please enter a second name using cyrillic characters only starting with uppercase letter.");
                isInputValid = false;
            }
            else
            {
                errorProvider1.SetError(textBox2, "");
            }

            if (!IsCyrillic(textBox3.Text) || !Char.IsUpper(textBox3.Text[0]))
            {
                errorProvider1.SetError(textBox3, "Please enter a third name using cyrillic characters only starting with uppercase letter.");
                isInputValid = false;
            }
            else
            {
                errorProvider1.SetError(textBox3, "");
            }


            if (!IsValidEGN(textBox4.Text))
            {
                errorProvider1.SetError(textBox4, "Please enter a valid EGN!");
                isInputValid = false;
            }
            else
            {
                errorProvider1.SetError(textBox4, "");
            }

            if (!IsAddressValid(textBox5.Text))
            {
                errorProvider1.SetError(textBox5, "Please enter a valid address containing cyrilic letters or numbers.");
                isInputValid = false;
            }
            else
            {
                errorProvider1.SetError(textBox5, "");
            }

            return isInputValid;
        }

        private static bool IsAddressValid(string text)
        {
            if (text.Length == 0) return false;

            bool isValid = true;
            for (int i = 0; i < text.Length; i++)
            {
                if ((text[i] < 'а' || text[i] > 'я') && (text[i] < 'А' || text[i] > 'Я') && !Char.IsDigit(text[i]) && text[i] != ' ')
                    isValid = false;
            }

            return isValid;
        }

        private static bool IsCyrillic(string text)
        {
            if (text.Length == 0) return false;

            bool isValid = true;
            for (int i = 0; i < text.Length; i++)
            {
                if ((text[i] < 'а' || text[i] > 'я') && (text[i] < 'А' || text[i] > 'Я'))
                    isValid = false;
            }

            return isValid;
        }

        private static bool IsValidEGN(string egn)
        {
            return Regex.IsMatch(egn, @"^\d{10}$");
        }
    }
}
