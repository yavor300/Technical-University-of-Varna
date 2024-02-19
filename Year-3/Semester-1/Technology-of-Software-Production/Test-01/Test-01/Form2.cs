using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

namespace Test_01
{
    public partial class Form2 : Form
    {
        private bool _isEven;
        private List<int> numbers = new List<int>();

        public Form2(bool isEven)
        {
            InitializeComponent();
            _isEven = isEven;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string textToCheck = textBox1.Text;

            if (!InputValidator.isInputValid(textToCheck)) {
                MessageBox.Show("Only letters and digits are allowed!");
                textBox1.Clear();
                return;
            }

            char[] vowels = new char[] { 'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U' };
            int count = 0;
            string chosenSet = _isEven ? "Vowels" : "Consonants";

            richTextBox1.Clear();

            for (int i = 0; i < textToCheck.Length; i++)
            {
                if ((chosenSet == "Vowels" && vowels.Contains(textToCheck[i])) ||
                    (chosenSet == "Consonants" && !vowels.Contains(textToCheck[i]) && Char.IsLetter(textToCheck[i])))
                {
                    count++;
                    richTextBox1.AppendText(String.Format("{0} {1}\n", i, textToCheck[i]));
                }

                if (Char.IsDigit(textToCheck[i]))
                {
                    numbers.Add(int.Parse(textToCheck[i].ToString()));
                }
            }

            richTextBox1.AppendText(String.Format("Total letters: {0}\n", count));

            if (numbers.Count == 0)
            {
                richTextBox1.AppendText("No digits were entered in the text box.");
            }
            else
            {
                richTextBox1.AppendText(String.Format("The largest number is {0}.", numbers.Max().ToString()));
            }

            FileWriter.writeToFile("results.txt", richTextBox1.Text);
        }
    }
}
