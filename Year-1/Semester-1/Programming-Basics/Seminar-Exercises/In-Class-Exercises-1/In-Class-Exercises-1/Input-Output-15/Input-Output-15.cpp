#include <iostream>
#include <iomanip>
using namespace std;

/*
* ������ 10
* �������� ��������, ��������� ASCII �������� �� ����� ������� ������ (���� ��������� � ��������������� �����). 
*/

void main()
{
	char symbol;

	cin.get(symbol);
	while (symbol != '\n')
	{
		cout << setw(10) << "Symbol" << setw(10) << "Dec" << setw(10) << "Hex" << endl;
		cout << setw(10) << symbol << setw(10) << dec << (int)symbol << setw(10) << hex << (int)symbol << endl;

		cin.get(symbol);
	}
}