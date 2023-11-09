using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Practice_01
{
    public partial class Form1 : Form
    {

        private Form2 resultForm = new Form2();

        public Form1()
        {
            InitializeComponent();
            comboBox1.Items.Add("Vowels");
            comboBox1.Items.Add("Consonants");
            comboBox1.SelectedIndex = 0;
        }

        private void button1_Click(object sender, EventArgs e)
        {

            string textToCheck = textBox1.Text;
            char[] vowels = new char[] { 'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U' };
            int count = 0;

            listBox1.Items.Clear();

            for (int i = 0; i < textToCheck.Length; i++)
            {
                if ((comboBox1.SelectedItem.ToString() == "Vowels" && vowels.Contains(textToCheck[i])) ||
                    (comboBox1.SelectedItem.ToString() == "Consonants" && !vowels.Contains(textToCheck[i]) && Char.IsLetter(textToCheck[i])))
                {
                    count++;
                    listBox1.Items.Add(String.Format("{0} {1}", i, textToCheck[i]));
                }
            }

            listBox1.Items.Add(String.Format("Total {0}", count));

            resultForm.ShowResult(count);
        }
    }
}
