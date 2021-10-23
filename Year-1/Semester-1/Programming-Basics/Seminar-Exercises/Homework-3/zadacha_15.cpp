#include <iostream>
#include <iomanip>
using namespace std;

/*
* Задача 15
* Съставете C/C++ програма,която използва форматиращи функции и извежда следните последователности:
* 
* Hello
* %%%%%Hello
* Hello%%%%%
* 123.234567
* 123.235%%%
*/

void main()
{
	string text = "Hello";
	double number(123.234567);

	char fillSymbol = '%';
	cout.fill(fillSymbol);

	cout << text << endl;
	cout << setw(10) << right << text << endl;
	cout << setw(10) << left << text << endl;

	cout << setiosflags(ios::fixed) << setprecision(6) << number << endl;
	cout << setw(10) << left << setprecision(3) << number;
}