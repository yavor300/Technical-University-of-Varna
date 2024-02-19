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
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {

            if (FormInputValidator.IsUserUnputValid(errorProvider1, textBox1, textBox2, textBox3, textBox4, textBox5))
            {
                new Form2().Show();
            }
        }
    }
}
