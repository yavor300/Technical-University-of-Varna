#include <iostream>
#include <fstream>
using namespace std;

const char file_name_input[11] = "infile.dat";
const char file_name_output[12] = "outfile.dat";

void main()
{
    ifstream in_stream;
    ofstream out_stream;

    in_stream.open(file_name_input);
    if (in_stream.fail())
    {
        cout << "Input file opening failed.\n";
        exit(1);
    }

    out_stream.open(file_name_output);
    if (out_stream.fail())
    {
        cout << "Output file opening failed.\n";
        exit(1);
    }

    int first, second, third;
    in_stream >> first >> second >> third;

    out_stream << "The sum of the first 3\nnumbers in infile.dat\nis "
        << (first + second + third) << endl;
    in_stream.close();
    out_stream.close();
}
