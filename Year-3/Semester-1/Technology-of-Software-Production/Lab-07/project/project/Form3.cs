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
    public partial class Form3 : Form
    {
        public Form3()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (!IsCyrillic(textBox1.Text) || !Char.IsUpper(textBox1.Text[0]))
            {
                errorProvider1.SetError(textBox1, "Please enter a first name using cyrillic characters only starting with uppercase letter.");
            }
            else
            {
                errorProvider1.SetError(textBox1, "");
            }

            if (!IsCyrillic(textBox2.Text) || !Char.IsUpper(textBox2.Text[0]))
            {
                errorProvider1.SetError(textBox2, "Please enter a second name using cyrillic characters only starting with uppercase letter.");
            }
            else
            {
                errorProvider1.SetError(textBox2, "");
            }

            if (!IsCyrillic(textBox3.Text) || !Char.IsUpper(textBox3.Text[0]))
            {
                errorProvider1.SetError(textBox3, "Please enter a last name using cyrillic characters only starting with uppercase letter.");
            }
            else
            {
                errorProvider1.SetError(textBox3, "");
            }

            if (!IsValidEGN(textBox4.Text))
            {
                errorProvider1.SetError(textBox4, "Please enter a valid EGN.");
            }
            else
            {
                errorProvider1.SetError(textBox4, "");
            }

            if (!IsCyrillic(textBox5.Text))
            {
                errorProvider1.SetError(textBox5, "Please enter an address in cyrilic.");
            }
            else
            {
                errorProvider1.SetError(textBox5, "");
            }

            if (string.IsNullOrEmpty(errorProvider1.GetError(textBox1)) &&
       string.IsNullOrEmpty(errorProvider1.GetError(textBox2)) &&
       string.IsNullOrEmpty(errorProvider1.GetError(textBox3)) &&
       string.IsNullOrEmpty(errorProvider1.GetError(textBox4)) &&
       string.IsNullOrEmpty(errorProvider1.GetError(textBox5)))
            {
                new Form4().Show();
            }
        }


        private bool IsCyrillic(string text)
        {
            return Regex.IsMatch(text, @"^[\p{IsCyrillic}\s]+$");
        }

        public bool IsValidEGN(string egn)
        {
            return Regex.IsMatch(egn, @"^\d{10}$");
        }
    }

}
