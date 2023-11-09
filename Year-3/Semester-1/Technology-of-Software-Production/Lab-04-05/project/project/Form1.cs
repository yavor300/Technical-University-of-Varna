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
            String s = textBox1.Text.ToUpper();
            char[] letters = s.ToCharArray();
            char[] lettersReversed = s.ToCharArray();
            Array.Reverse(letters);
            Boolean isPalindrome = true;
            for (int i = 0; i < letters.Length; i++) {
                if (letters[i] != lettersReversed[i]) {
                    isPalindrome = false;
                    break;
                }
            }
            if (isPalindrome)
            {
                MessageBox.Show("Палиндром!");
            }
            else {
                MessageBox.Show("Не е палиндром!");
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            String s = textBox1.Text;
            String result = "";
            for (int i = 0; i < s.Length; i++) {
                result += "" + (int)s[i] + " ";
            }
            MessageBox.Show(result);
        }



    }
}
