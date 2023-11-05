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
    public partial class Form2 : Form
    {
        public Form2()
        {
            InitializeComponent();
        }

        private void comboBox2_SelectedIndexChanged(object sender, EventArgs e)
        {
            double value;
            bool IsParsable = Double.TryParse(textBox1.Text, out value);
            if (!IsParsable)
            {
                MessageBox.Show("Enter a valid number!");
                return;
            }
            textBox2.Text = ConvertUnits(value, comboBox1.Text, comboBox2.Text).ToString();
        }

        private void Form2_Load(object sender, EventArgs e)
        {
            comboBox1.Items.AddRange(new string[] { "feet", "yard", "inch", "mile" });
            comboBox2.Items.AddRange(new string[] { "feet", "yard", "inch", "mile" });
            textBox1.Text = "1";
            comboBox1.SelectedIndex = 0; // Set default selected index
            comboBox2.SelectedIndex = 0;
            comboBox1.SelectedIndex = 0;
        }

        // This method should be linked to the Click event of your 'Cancel' button.
        private void cancelButton_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }


        private double ConvertUnits(double value, string fromUnit, string toUnit)
        {
            double baseMile;

            // Convert the input value to miles
            switch (fromUnit)
            {
                case "feet":
                    baseMile = value / 5280;
                    break;
                case "yard":
                    baseMile = value / 1760;
                    break;
                case "inch":
                    baseMile = value / 63360;
                    break;
                case "mile":
                    baseMile = value;
                    break;
                default:
                    throw new InvalidOperationException("Invalid unit.");
            }

            // Convert from miles to the target unit
            switch (toUnit)
            {
                case "feet":
                    return baseMile * 5280;
                case "yard":
                    return baseMile * 1760;
                case "inch":
                    return baseMile * 63360;
                case "mile":
                    return baseMile;
                default:
                    throw new InvalidOperationException("Invalid unit.");
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            textBox1.Text = "1";
            comboBox1.SelectedIndex = 0; // Set default selected index
            comboBox2.SelectedIndex = 0;
            comboBox1.SelectedIndex = 0;
            textBox2.Clear();
        }

    }
}
