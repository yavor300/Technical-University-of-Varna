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
            textBox4.Enabled = false;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (NumberValidator.ValidateNumericInput(textBox1.Text) &&
                           NumberValidator.ValidateNumericInput(textBox2.Text) &&
                           NumberValidator.ValidateNumericInput(textBox3.Text))
            {
                double basePay = double.Parse(textBox1.Text);
                double additionalPay = double.Parse(textBox2.Text);
                double fee = double.Parse(textBox3.Text);

                double amountToReceive = basePay + additionalPay - fee;

                textBox4.Text = amountToReceive.ToString();
            }
            else {
                MessageBox.Show("Invalid numbers!");
            }
        }

        
        private void textBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsControl(e.KeyChar) && !char.IsDigit(e.KeyChar) &&
        (e.KeyChar != ','))
            {
                e.Handled = true;
            }

            TextBox textBox = (TextBox)sender;
            if ((e.KeyChar == ',') && (textBox.Text.IndexOf(',') > -1))
            {
                e.Handled = true;
            }
        }
    }
}
