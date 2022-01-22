#include <iostream>
#include <fstream>
using namespace std;

const char file_name[9] = "data.txt";

void main()
{
    cout << "Opening data.txt for appending.\n";
    ofstream fout;
    
    fout.open(file_name, ios::app);
    if (fout.fail())
    {
        cout << "Input file opening failed.\n";
        exit(1);
    }

    fout << "5 6 pick up sticks.\n7 8 ain't C++ great!\n";

    fout.close();
    cout << "End of appending to file.\n";
}
