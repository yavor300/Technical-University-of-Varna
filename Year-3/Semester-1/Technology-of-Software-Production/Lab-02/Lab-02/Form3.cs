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
    public partial class Form3 : Form
    {
        private List<double> userBills = new List<double>();
        int clientNumber = 1;

        public Form3()
        {
            InitializeComponent();
        }

        private void aboutTheProgramToolStripMenuItem_Click(object sender, EventArgs e)
        {
            MessageBox.Show("This is an electricity billing calculator.", "About the Program");
        }

        private void newCustomerToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ResetFormFields();
            IncrementClientNumber();
        }

        private void exitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            // Calculate the total bill for all users
            double totalBill = userBills.Sum();

            // Show the total bill to the user
            MessageBox.Show("Общата сметка на потребителите е: " + totalBill.ToString("N2"), "Обща Сметка", MessageBoxButtons.OK, MessageBoxIcon.Information);
            Application.Exit();
        }

        private double GetSelectedTariff()
        {
            // Assuming radio buttons for tariff selection are named rbtnTariff1, rbtnTariff2, rbtnTariff3
            if (this.radioButton1.Checked) return 0.34;
            if (this.radioButton2.Checked) return 0.44;
            if (this.radioButton3.Checked) return 0.54;
            return 0; // Default to 0 if none selected
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                double oldReading = Convert.ToDouble(textBox1.Text);
                double newReading = Convert.ToDouble(textBox2.Text);
                double consumption = newReading - oldReading;
                label6.Text = consumption.ToString("N2");

                double tariff = GetSelectedTariff();
                double sumToPay = consumption * tariff;
                label8.Text = sumToPay.ToString("C2");
                userBills.Add(sumToPay);
            }
            catch (FormatException)
            {
                MessageBox.Show("Please enter valid numbers for the meter readings.", "Input Error");
            }
        }

        private void ResetFormFields()
        {
            // Assuming txtBoxOldReading and txtBoxNewReading are the text boxes for meter readings
            // and lblConsumption and lblAmountToPay are the labels for showing results
            textBox1.Clear();
            textBox2.Clear();
            label6.Text = "0";
            label8.Text = "0";

            // Reset all radio buttons or other input fields if necessary
            radioButton1.Checked = false;
            radioButton2.Checked = false;
            radioButton3.Checked = false;

            // If you have other fields to clear or reset, add them here
        }

        private void IncrementClientNumber()
        {
            clientNumber++;
            label2.Text = clientNumber.ToString(); // Assuming lblClientNumber is the label showing the client number
        }

        private void Form3_Load(object sender, EventArgs e)
        {
            label2.Text = clientNumber.ToString();
        }
        
    }
}
