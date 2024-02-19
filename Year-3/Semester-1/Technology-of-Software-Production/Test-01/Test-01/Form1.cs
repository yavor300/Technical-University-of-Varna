using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Test_01
{

    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();

        }

        private void button1_Click(object sender, EventArgs e)
        {
            int number = 0;
            if (int.TryParse(textBox1.Text, out number))
            {
                Form2 form2 = new Form2(number % 2 == 0);
                form2.Show();
            }
            else
            {
                MessageBox.Show("Please enter a valid number!");
                textBox1.Clear();
            }
        }
    }
}
