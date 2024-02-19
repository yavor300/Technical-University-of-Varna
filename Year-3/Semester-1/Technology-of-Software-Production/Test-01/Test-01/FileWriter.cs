using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Windows.Forms;

namespace Test_01
{
    class FileWriter
    {

        public static void writeToFile(string fileName, string data)
        {
            try
            {
                File.WriteAllText(fileName, data);
                MessageBox.Show("Written data to file sucessfully!");
            }
            catch (Exception ex)
            {
                MessageBox.Show("An error occurred while writing data to file: ", ex.Message);
            }
        }
    }
}
