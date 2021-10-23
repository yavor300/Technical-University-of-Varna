#include <iostream>
#include <iomanip>
using namespace std;

/*
* ������ 15
* ��������� C/C++ ��������,����� �������� ����������� ������� � ������� �������� �����������������:
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