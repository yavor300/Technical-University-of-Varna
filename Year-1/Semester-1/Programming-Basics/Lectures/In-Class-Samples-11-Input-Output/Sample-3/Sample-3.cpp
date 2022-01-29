#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <stdio.h>
#include <iomanip>
using namespace std;

int main()
{
	double price = 78.50;
	cout.setf(ios::fixed);
	cout.setf(ios::showpoint);
	cout.precision(2);
	cout << price << endl;
	cout.unsetf(ios::fixed);
	cout << price << endl;
	cout << "Start Now" << endl;
	cout.width(4);
	cout << 7 << endl;
	cout << "Start" << setw(4) << 10
		<< setw(4) << 20 << setw(6) << 30 << endl;
}
