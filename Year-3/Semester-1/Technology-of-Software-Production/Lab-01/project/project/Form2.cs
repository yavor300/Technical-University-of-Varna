using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace project
{
    public partial class Form2 : Form
    {
        public Form2()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            double firstNumber, secondNumber;
            bool isFirstNumberParsed = Double.TryParse(textBox1.Text, out firstNumber);
            bool isSecondNumberParse = Double.TryParse(textBox2.Text, out secondNumber);

            if (!isFirstNumberParsed || !isSecondNumberParse)
            {
                MessageBox.Show("The provided values are not correct numbers!");
                textBox1.Clear();
                textBox2.Clear();
                textBox3.Clear();
                return;
            }

            textBox3.Text = Calculator.AddNumbers(firstNumber, secondNumber).ToString();

        }
    }
}
