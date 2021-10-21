#include <iostream>
#include <iomanip>
using namespace std;

int main()
{
	cout << 10 << 15 << endl; //1015
	cout << 10 << setw(10) << 15 << endl; //10        15
	cout << 10 << hex << setw(10) << 15 << endl; //10         f
	cout << 10 << dec << setiosflags(ios::left) << 15 << endl; //a15
	cout << 10 << setw(10) << hex << 15 << endl; //10f     

	cout << setfill('-') <<  endl;
	cout << dec << 10 << 15 << endl; //1015
	cout << 10 << setw(10) << setiosflags(ios::right) << 15 << endl; //10--------15
	cout << 10 << hex << setw(10) << 15 << endl; //10---------f
	cout << 10 << dec << setiosflags(ios::left) << 15 << endl; //a15
	cout << 10 << setw(10) << hex << 15 << endl; //10f
}

