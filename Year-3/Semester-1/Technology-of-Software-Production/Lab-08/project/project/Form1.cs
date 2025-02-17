﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

namespace project
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            tabControl1.TabPages[0].Text = "Анкета стр. 1";
            tabControl1.TabPages[1].Text = "стр. 2";

            label1.Text = "1) Посочи 5 дисциплини, които ти харесват най-много. Започни от тази, която е водеща за теб.";
            label2.Text = "Дисциплина 1";
            label3.Text = "Дисциплина 2";
            label4.Text = "Дисциплина 3";
            label5.Text = "Дисциплина 4";
            label6.Text = "Дисциплина 5";

            comboBox1.Items.Add("Английски език");
            comboBox1.Items.Add("Web дизайн");
            comboBox1.Items.Add("Офис програмиране");
            comboBox1.Items.Add("Базово програмиране");
            comboBox1.Items.Add("Синтез и анализ");

            comboBox2.Items.Add("Английски език");
            comboBox2.Items.Add("Web дизайн");
            comboBox2.Items.Add("Офис програмиране");
            comboBox2.Items.Add("Базово програмиране");
            comboBox2.Items.Add("Синтез и анализ");

            comboBox3.Items.Add("Английски език");
            comboBox3.Items.Add("Web дизайн");
            comboBox3.Items.Add("Офис програмиране");
            comboBox3.Items.Add("Базово програмиране");
            comboBox3.Items.Add("Синтез и анализ");

            comboBox4.Items.Add("Английски език");
            comboBox4.Items.Add("Web дизайн");
            comboBox4.Items.Add("Офис програмиране");
            comboBox4.Items.Add("Базово програмиране");
            comboBox4.Items.Add("Синтез и анализ");

            comboBox5.Items.Add("Английски език");
            comboBox5.Items.Add("Web дизайн");
            comboBox5.Items.Add("Офис програмиране");
            comboBox5.Items.Add("Базово програмиране");
            comboBox5.Items.Add("Синтез и анализ");

            label7.Text = "2) Интересът към дисциплината е свързан с";
            checkedListBox1.Items.Add("Бъдеща професионална реализация");
            checkedListBox1.Items.Add("Придобиване на опит");
            checkedListBox1.Items.Add("Личност на преподавателя");
            checkedListBox1.Items.Add("Друго");

            label8.Text = "3) Отбележи интересът към дисциплините";
            groupBox1.Text = "Английски език";
            radioButton1.Text = "1";
            radioButton2.Text = "2";
            radioButton3.Text = "3";
            radioButton4.Text = "4";
            radioButton5.Text = "5";
            radioButton6.Text = "6";

            groupBox2.Text = "Web Design";
            radioButton12.Text = "1";
            radioButton11.Text = "2";
            radioButton10.Text = "3";
            radioButton9.Text = "4";
            radioButton8.Text = "5";
            radioButton7.Text = "6";

            groupBox3.Text = "Офис програмиране";
            radioButton18.Text = "1";
            radioButton17.Text = "2";
            radioButton16.Text = "3";
            radioButton15.Text = "4";
            radioButton14.Text = "5";
            radioButton13.Text = "6";

            groupBox4.Text = "Базово програмиране";
            radioButton24.Text = "1";
            radioButton23.Text = "2";
            radioButton22.Text = "3";
            radioButton21.Text = "4";
            radioButton20.Text = "5";
            radioButton19.Text = "6";

            groupBox5.Text = "Синтез и анализ";
            radioButton30.Text = "1";
            radioButton29.Text = "2";
            radioButton28.Text = "3";
            radioButton27.Text = "4";
            radioButton26.Text = "5";
            radioButton25.Text = "6";

            label9.Text = "4) За упражненията използвате";
            groupBox6.Text = "";
            radioButton31.Text = "Личен лаптоп";
            radioButton32.Text = "Предоставен от университета компютър";

            label10.Text = "5) На кое упражнение сте в момента?";

            label11.Text = "6) Посещавате ли лекции?";
            groupBox7.Text = "";
            radioButton33.Text = "Да";
            radioButton34.Text = "Не";

            label12.Text = "7) Бяха ли полезни упражненията?";
            groupBox8.Text = "";
            radioButton36.Text = "Да";
            radioButton35.Text = "Не";

            label13.Text = "8) Какво още бихте искали да научите?";

            button1.Text = "Continue-->";
            button2.Text = "<--Back";
            button3.Text = "Save";
            button4.Text = "Clear";
            button5.Text = "Summarize";
            button6.Text = "Exit";
        }

        private void button1_Click(object sender, EventArgs e)
        {
            TabPage t = tabControl1.TabPages[1];
            tabControl1.SelectedTab = t;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            TabPage t = tabControl1.TabPages[0];
            tabControl1.SelectedTab = t;
        }

        private void button3_Click(object sender, EventArgs e)
        {
            TextWriter f = File.AppendText("Potrebitel.txt");

string s = comboBox1.Text + "," + comboBox2.Text + "," + comboBox3.Text + "," + comboBox4.Text + "," + comboBox5.Text + ',' +
    checkedListBox1.Items[0] + "," + checkedListBox1.Items[1] + "," + checkedListBox1.Items[2] + "," + checkedListBox1.Items[3] + "," +

radioButton1.Checked + ',' + radioButton2.Checked + ',' + radioButton3.Checked + ',' + radioButton4.Checked + radioButton5.Checked + ',' + radioButton6.Checked + ',' +

radioButton7.Checked + ',' + radioButton8.Checked + ',' + radioButton9.Checked + ',' + radioButton10.Checked + radioButton11.Checked + ',' + radioButton12.Checked + ',' +

radioButton13.Checked + ',' + radioButton14.Checked + ',' + radioButton15.Checked + ',' + radioButton16.Checked + radioButton17.Checked + ',' + radioButton18.Checked + ',' +

radioButton19.Checked + ',' + radioButton20.Checked + ',' + radioButton21.Checked + ',' + radioButton22.Checked + radioButton23.Checked + ',' + radioButton24.Checked + ',' +

radioButton25.Checked + ',' + radioButton26.Checked + ',' + radioButton27.Checked + ',' + radioButton28.Checked + radioButton29.Checked + ',' + radioButton30.Checked + ',' +

radioButton31.Checked + ',' + radioButton32.Checked + ',' + textBox1.Text + radioButton33.Checked + ',' + radioButton34.Checked + ',' + textBox2.Text;
f.WriteLine(s);

f.Close();
        }

        //Брояч за анкетирани

        int br_anketni_karti = 0;

        // Броячи съм ComboBox1, за всяка от дисциплините - английски, web дизайн, офис програмиране, базово програмиране и синтез и анализ

        int sum_d1_a = 0, sum_d1_w = 0, sum_d1_opr = 0, sum_d1_bpr = 0, sum_d1_sa = 0;

        // Броячи съм ComboBox2, за всяка от дисциплините - английски, web дизайн, офис програмиране, базово програмиране и синтез и анализ

        int sum_d2_a = 0, sum_d2_w = 0, sum_d2_opr = 0, sum_d2_bpr = 0, sum_d2_sa = 0;

        // Броячи съм ComboBox3, за всяка от дисциплините - английски, web дизайн, офис програмиране, базово програмиране и синтез и анализ

        int sum_d3_a = 0, sum_d3_w = 0, sum_d3_opr = 0, sum_d3_bpr = 0, sum_d3_sa = 0;

        // Броячи съм ComboBox4, за всяка от дисциплините - английски, web дизайн, офис програмиране, базово програмиране и синтез и анализ

        int sum_d4_a = 0, sum_d4_w = 0, sum_d4_opr = 0, sum_d4_bpr = 0, sum_d4_sa = 0;

        // Броячи съм ComboBox5, за всяка от дисциплините - английски, web дизайн, офис програмиране, базово програмиране и синтез и анализ

        int sum_d5_a = 0, sum_d5_w = 0, sum_d5_opr = 0, sum_d5_bpr = 0, sum_d5_sa = 0;

        // Броячи съм checkedListBox, за всяка от дисциплините - английски, web дизайн, офис програмиране, базово програмиране и синтез и анализ

        int sum_ch0 = 0; int sum_ch1 = 0; int sum_ch2 = 0; int sum_ch3 = 0;

        // Броячи съм GroupBox1, 2, 3, 4 и 5, въпрос 3, за всяка от дисциплините - английски, web дизайн, офис програмиране, базово програмиране и синтез и анализ

        int sum_gr1_r1 = 0, sum_gr1_r2 = 0, sum_gr1_r3 = 0, sum_gr1_r4 = 0, sum_gr1_r5 = 0, sum_gr1_r6 = 0;

        int sum_gr2_r1 = 0, sum_gr2_r2 = 0, sum_gr2_r3 = 0, sum_gr2_r4 = 0, sum_gr2_r5 = 0, sum_gr2_r6 = 0;

        int sum_gr3_r1 = 0, sum_gr3_r2 = 0, sum_gr3_r3 = 0, sum_gr3_r4 = 0, sum_gr3_r5 = 0, sum_gr3_r6 = 0;

        int sum_gr4_r1 = 0, sum_gr4_r2 = 0, sum_gr4_r3 = 0, sum_gr4_r4 = 0, sum_gr4_r5 = 0, sum_gr4_r6 = 0;

        int sum_gr5_r1 = 0, sum_gr5_r2 = 0, sum_gr5_r3 = 0, sum_gr5_r4 = 0, sum_gr5_r5 = 0, sum_gr5_r6 = 0;

        // Броячи съм GroupBox6, 7, 8 съответно за въпрос 4, 6 и 7

        int sum_rb31 = 0; int sum_rb32 = 0;

        int sum_rb33 = 0; int sum_rb34 = 0;

        int sum_rb35 = 0; int sum_rb36 = 0;

        private void button6_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button4_Click(object sender, EventArgs e)
        {
            comboBox1.Text = "";

            comboBox2.Text = "";

            comboBox3.Text = "";

            comboBox4.Text = "";

            comboBox5.Text = "";

            foreach (TabPage t in tabControl1.TabPages)
            {

                foreach (Control c in t.Controls)
                {

                    if (c is GroupBox)
                    {

                        foreach (Control cc in c.Controls)
                        {

                            if (cc is RadioButton)
                            {

                                // MessageBox.Show(cc.Name);

                                if (cc as RadioButton != null)
                                {

                                    RadioButton r = (RadioButton)cc;

                                    r.Checked = false;

                                }

                            }

                        }

                    }

                }

            }

            foreach (int i in checkedListBox1.CheckedIndices)
            {

                checkedListBox1.SetItemCheckState(i, CheckState.Unchecked);

            }

            textBox1.Clear();

            textBox2.Clear();

            TabPage p = tabControl1.TabPages[0];

            tabControl1.SelectedTab = p;
        }

        private void button5_Click(object sender, EventArgs e)
        {
            br_anketni_karti = br_anketni_karti + 1;

if (comboBox1.Text == "Английски език")

sum_d1_a = sum_d1_a + 1;

if (comboBox1.Text == "Web дизайн")

sum_d1_w = sum_d1_w + 1;

if (comboBox1.Text == "Офис програмиране")

sum_d1_opr = sum_d1_opr + 1;

if (comboBox1.Text == "Базово програмиране")

sum_d1_bpr = sum_d1_bpr + 1;

if (comboBox1.Text == "Синтез и анализ")

sum_d1_sa = sum_d1_sa + 1;

if (comboBox2.Text == "Английски език")

sum_d2_a = sum_d2_a + 1;

if (comboBox3.Text == "Английски език")

sum_d3_a = sum_d3_a + 1;

if (comboBox4.Text == "Английски език")
sum_d4_a = sum_d4_a + 1;

if (comboBox5.Text == "Английски език")

sum_d5_a = sum_d5_a + 1;

foreach (int indexChecked in checkedListBox1.CheckedIndices)
{

    string t2 = checkedListBox1.GetItemCheckState(indexChecked).ToString();

    if ((t2 == "Checked") && (indexChecked == 0))

        sum_ch0 = sum_ch0 + 1;

    if ((t2 == "Checked") && (indexChecked == 1))

        sum_ch1 = sum_ch1 + 1;

    if ((t2 == "Checked") && (indexChecked == 2))

        sum_ch2 = sum_ch2 + 1;

    if ((t2 == "Checked") && (indexChecked == 3))

        sum_ch3 = sum_ch3 + 1;

}

foreach (Control item in groupBox1.Controls)
{

    if (item as RadioButton != null)
    {

        RadioButton r = (RadioButton)item;

        if ((r.Checked == true) && (r.Text == "1"))
        {

            sum_gr1_r1 = sum_gr1_r1 + 1;

        }

        if ((r.Checked == true) && (r.Text == "2"))
        {

            sum_gr1_r2 = sum_gr1_r2 + 1;

        }

        if ((r.Checked == true) && (r.Text == "3"))
        {

            sum_gr1_r3 = sum_gr1_r3 + 1;

        }

        if ((r.Checked == true) && (r.Text == "4"))
        {

            sum_gr1_r4 = sum_gr1_r4 + 1;

        }

        if ((r.Checked == true) && (r.Text == "5"))
        {

            sum_gr1_r5 = sum_gr1_r5 + 1;

        }

        if ((r.Checked == true) && (r.Text == "6"))
        {

            sum_gr1_r6 = sum_gr1_r6 + 1;

        }

    }

}

            foreach (Control item in groupBox2.Controls)

{

if (item as RadioButton != null)

{

RadioButton r = (RadioButton)item;

if ((r.Checked == true) && (r.Text == "1"))

{

sum_gr2_r1 = sum_gr2_r1 + 1;

}

}

}

            //TODO

        }




    }
}
