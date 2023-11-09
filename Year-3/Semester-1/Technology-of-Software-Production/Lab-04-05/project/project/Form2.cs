using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Microsoft.VisualBasic;

namespace project
{
    public partial class Form2 : Form
    {
        private Button button1 = new Button();

        private Button button2 = new Button();
        private ListBox listBox1 = new ListBox();
        private ListBox listBox2 = new ListBox();


        public Form2()
        {
            InitializeComponent();
        }

        private void Form2_Load(object sender, EventArgs e)
        {
            CreateButton(button1, 250,10);
            button1.Click += new EventHandler(this.button1_Click);

            CreateButton(button2, 250, 50);
            button2.Click += new EventHandler(this.button2_Click);
            
        }

        private void CreateButton(Button button, int locationX, int locationY)
        {

            button.Size = new System.Drawing.Size(120, 30);
            button.Location = new System.Drawing.Point(locationX, locationY);
            button.Text = "Create ListBox";
            this.Controls.Add(button);
        }

        private void CreateListBox(ListBox listbox, int locationX, int locationY)
        {

            listbox.Size = new System.Drawing.Size(100, 100);
            listbox.Location = new System.Drawing.Point(locationX, locationY);
            this.Controls.Add(listbox);
            listbox.MultiColumn = true;
            listbox.SelectionMode = SelectionMode.MultiExtended;
        }

        protected void button1_Click(object sender, EventArgs e)
        {
            CreateListBox(listBox1, 10, 10);

            string[] lines = System.IO.File.ReadAllLines("F:\\TU-Varna\\TSP\\Lab-04\\project\\tuvarna.txt");

            foreach (string str in lines)
            {

                listBox1.Items.Add(str);

            }

        }

        protected void button2_Click(object sender, EventArgs e)
        {
            CreateListBox(listBox2, 10, 150);
            double salary = 0;
            bool s = Double.TryParse(Interaction.InputBox("Въведете заплата: "), out salary);
            listBox2.Items.Add(salary);
        }
    }
}
