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
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            int month = int.Parse(dateTimePicker1.Value.ToString("MM"));
            int day = int.Parse(dateTimePicker1.Value.ToString("dd"));

            switch (month)
            {
                case 1:
                    listBox1.Items.Add(day <= 19 ? "Capricorn" : "Aquarius");
                    break;
                case 2:
                    listBox1.Items.Add(day <= 18 ? "Aquarius" : "Pisces");
                    break;
                case 3:
                    listBox1.Items.Add(day <= 20 ? "Pisces" : "Aries");
                    break;
                case 4:
                    listBox1.Items.Add(day <= 19 ? "Aries" : "Taurus");
                    break;
                case 5:
                    listBox1.Items.Add(day <= 20 ? "Taurus" : "Gemini");
                    break;
                case 6:
                    listBox1.Items.Add(day <= 20 ? "Gemini" : "Cancer");
                    break;
                case 7:
                    listBox1.Items.Add(day <= 22 ? "Cancer" : "Leo");
                    break;
                case 8:
                    listBox1.Items.Add(day <= 22 ? "Leo" : "Virgo");
                    break;
                case 9:
                    listBox1.Items.Add(day <= 22 ? "Virgo" : "Libra");
                    break;
                case 10:
                    listBox1.Items.Add(day <= 22 ? "Libra" : "Scorpio");
                    break;
                case 11:
                    listBox1.Items.Add(day <= 21 ? "Scorpio" : "Sagittarius");
                    break;
                case 12:
                    listBox1.Items.Add(day <= 21 ? "Sagittarius" : "Capricorn");
                    break;
                default:
                    listBox1.Items.Add("Invalid month");
                    break;
            }
        }
    }
}
