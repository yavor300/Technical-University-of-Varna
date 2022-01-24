#include <iostream>
#include <fstream>
using namespace std;

const char file_name[9] = "data.txt";

void add_plus_plus(ifstream& in_stream, ofstream& out_stream);

void main()
{
	ifstream fin;
	ofstream fout;

	cout << "Begin editing files.\n";
	fin.open("cad.dat");
	if (fin.fail())
	{
		cout << "Input file opening failed.\n";
		exit(1);
	}

	fout.open("cplusad.dat");
	if (fout.fail())
	{
		cout << "Output file opening failed.\n";
		exit(1);
	}

	add_plus_plus(fin, fout);
	
	fin.close();
	fout.close();
	cout << "End of editing files.\n";
}

void add_plus_plus(ifstream& in_stream, ofstream& out_stream)
{
	char next;
	in_stream.get(next);
	while (!in_stream.eof())
	{
		if (next == 'C') out_stream << "C++";
		else out_stream << next;

		in_stream.get(next);
	}
}
