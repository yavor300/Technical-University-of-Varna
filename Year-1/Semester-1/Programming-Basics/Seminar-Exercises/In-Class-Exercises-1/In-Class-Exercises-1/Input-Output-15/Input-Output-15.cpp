#include <iostream>
#include <iomanip>
using namespace std;

/*
* Задача 10
* Напишете програма, извеждаща ASCII кодовете на всеки въведен символ (като десетично и шестнадесетично число). 
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