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

namespace Practice_01
{
    public partial class Form4 : Form
    {

        private bool _isEven;

        public Form4(bool isEven)
        {
            InitializeComponent();
            _isEven = isEven;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string textToCheck = textBox1.Text;
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
            }

            richTextBox1.AppendText(String.Format("Total {0}", count));
            

            File.WriteAllText("E:\\Technical-University-of-Varna\\Year-3\\Semester-1\\Technology-of-Software-Production\\Practice-01\\Practice-01\\results.txt", richTextBox1.Text);

        }
    }
}
