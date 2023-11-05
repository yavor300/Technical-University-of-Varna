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
    public partial class Form3 : Form
    {
        double a, b;

        public Form3()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            processOperation(sender);
        }

        private void button2_Click(object sender, EventArgs e)
        {
            processOperation(sender);
        }

        private void button3_Click(object sender, EventArgs e)
        {
            try
            {
                processOperation(sender);
            }
            catch (DivideByZeroException)
            {
                MessageBox.Show("Division by zero!");
                textBox1.Clear();
                textBox2.Clear();
                textBox3.Clear();
                return;
            }
            
        }

        private void button4_Click(object sender, EventArgs e)
        {
            processOperation(sender);
        }

        private void processOperation(object sender)
        {
            if (!NumberValidator.TryParseTextBoxesToDoubles(textBox1, textBox2, out a, out b))
            {
                MessageBox.Show("The provided values are not correct numbers!");
                textBox1.Clear();
                textBox2.Clear();
                textBox3.Clear();
                return;
            };

            double result = Calculator.Calculate((sender as Button).Text, a, b);

            textBox3.Text = result.ToString();
        }

        
    }
}
