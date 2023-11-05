using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab_02
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Double Val1;
            Double Val2;
            Double Val3;
            try
            {
                Val1 = Double.Parse(textBox6.Text);
                Val2 = Double.Parse(textBox7.Text);
                Val3 = Double.Parse(textBox8.Text);
            }
            catch {
                MessageBox.Show ("Enter the correct data before pressing the button!");
                textBox6.Clear(); textBox7.Clear(); textBox8.Clear();
                return;
            }
            
            Double D = Math.Pow(Val2, 2) - 4 * Val1 * Val3;

            if (D < 0) {
                MessageBox.Show("No answers!");
                textBox6.Clear(); textBox7.Clear(); textBox8.Clear();
                label12.Text = "";
                label13.Text = "";
            }
            else if (D == 0)
            {
                label12.Text = (((0 - Val2) + Math.Sqrt(D)) / 2).ToString();
                label13.Text = (((0 - Val2) + Math.Sqrt(D)) / 2).ToString();
            }
            else {
                label12.Text = (((0 - Val2) + Math.Sqrt(D)) / 2).ToString();
                label13.Text = (((0 - Val2) - Math.Sqrt(D)) / 2).ToString();
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            label12.Text = "";
            label13.Text = "";
        }

    }
}
