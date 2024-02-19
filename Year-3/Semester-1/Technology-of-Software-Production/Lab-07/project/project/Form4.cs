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
    public partial class Form4 : Form
    {
        public Form4()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (ValidateNumericInput(textBox1.Text) &&
                            ValidateNumericInput(textBox2.Text) &&
                            ValidateNumericInput(textBox3.Text))
            {
                // Parse the numeric values
                decimal basicScholarship = decimal.Parse(textBox1.Text);
                decimal additionalIncome = decimal.Parse(textBox2.Text);
                decimal tuitionFee = decimal.Parse(textBox3.Text);

                // Perform the calculation
                decimal amountToReceive = basicScholarship + additionalIncome - tuitionFee;

                // Display the result
                textBox4.Text = amountToReceive.ToString();
            }
            else
            {
                MessageBox.Show("Please enter valid numeric values.");
            }
        }

        private bool ValidateNumericInput(string input)
        {
            double digit;
            return double.TryParse(input, out digit);
        }

        private void textBox1_KeyPress(object sender, KeyPressEventArgs e)
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

        private void textBox2_KeyPress(object sender, KeyPressEventArgs e)
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

        private void textBox3_KeyPress(object sender, KeyPressEventArgs e)
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
