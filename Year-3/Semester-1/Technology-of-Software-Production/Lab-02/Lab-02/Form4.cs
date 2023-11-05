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
    public partial class Form4 : Form
    {
        private int width = 0;
        private int height = 0;

        public Form4()
        {
            InitializeComponent();
        }

        private void vScrollBar1_ValueChanged(object sender, EventArgs e)
        {
            width = hScrollBar1.Value;
            height = vScrollBar1.Value;
            UpdateLabels();
        }

        private void hScrollBar1_ValueChanged(object sender, EventArgs e)
        {
            width = hScrollBar1.Value;
            height = vScrollBar1.Value;
            UpdateLabels();
        }

        private void UpdateLabels()
        {
            label1.Text = "width = " + width.ToString();
            label2.Text = "height = " + height.ToString();
            label3.Text = "AREA = " + (width * height).ToString();
        }

    }
}