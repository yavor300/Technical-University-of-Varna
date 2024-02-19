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
        Random randomizer = new Random();
        int timeLeft;

        public Form2()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            StartGame();
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            if (timeLeft > 0)
            {
                timeLeft = timeLeft - 1;
                label6.Text = timeLeft.ToString();
            }
            else
            {
                timer1.Stop();
                MessageBox.Show("You didn't finish in time.", "Sorry");
            }
        }

        public void StartGame()
        {

            numericUpDown1.Value = 0;
            numericUpDown2.Value = 0;
            numericUpDown3.Value = 0;
            numericUpDown4.Value = 0;

            // Generate random numbers for each math operation
            int addend1 = randomizer.Next(51);
            int addend2 = randomizer.Next(51);
            int minuend = randomizer.Next(1, 51);
            int subtrahend = randomizer.Next(1, minuend);
            int multiplicand = randomizer.Next(51);
            int multiplier = randomizer.Next(51);
            int dividend = randomizer.Next(2, 51);
            int divisor = randomizer.Next(1, dividend);

            // Set the labels to the generated numbers
            label2.Text = addend1.ToString();
            label11.Text = addend2.ToString();

            label3.Text = minuend.ToString();
            label10.Text = subtrahend.ToString();

            label4.Text = multiplicand.ToString();
            label9.Text = multiplier.ToString();

            label5.Text = dividend.ToString();
            label8.Text = divisor.ToString();

            button2.Enabled = true;

            // Reset the timer
            timeLeft = 30;
            label6.Text = timeLeft.ToString();
            timer1.Start();
        }

        private void CheckAnswers()
        {
            int correctAnswers = 0;

            // Assume addResult, subtractResult, multiplyResult, divideResult are your NumericUpDown controls
            int userAdd = (int)numericUpDown1.Value;
            int userSubtract = (int)numericUpDown2.Value;
            int userMultiply = (int)numericUpDown3.Value;
            int userDivide = (int)numericUpDown4.Value;

            // Check addition
            if (userAdd == (int.Parse(label2.Text) + int.Parse(label11.Text)))
            {
                correctAnswers++;
            }

            // Check subtraction
            if (userSubtract == (int.Parse(label3.Text) - int.Parse(label10.Text)))
            {
                correctAnswers++;
            }

            // Check multiplication
            if (userMultiply == (int.Parse(label4.Text) * int.Parse(label9.Text)))
            {
                correctAnswers++;
            }

            // Check division - be careful with integer division if you expect non-integer results
            if (userDivide == (int)(int.Parse(label5.Text) / (double)int.Parse(label8.Text)))
            {
                correctAnswers++;
            }

            // Show the result in a message box
            MessageBox.Show(String.Format("You got {0} out of 4 correct!", correctAnswers), "Results");
        }

        private void button2_Click(object sender, EventArgs e)
        {
            FinishGame();
        }

        private void FinishGame()
        {
            timer1.Stop();
            CheckAnswers();
            button2.Enabled = false;
        }

    }
}

