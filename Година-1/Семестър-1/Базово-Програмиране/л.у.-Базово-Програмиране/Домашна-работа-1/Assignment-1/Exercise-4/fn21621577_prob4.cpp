#include <iostream>
// Използване на библиотека, която съдържа най-голлемите и най-малките стойности на типовете данни
#include <climits>
using namespace std;

/*
Задача 4
За всеки от посочените типове данни изведете най-голямата и най-малката стойност,
които могат да приемат и размера им:
а) char
б) int
в) long long
*/

int main()
{
	/**
		Отпечатване на търсените стойности
		Стойностите се достъпват от добавената библиотека <climits>
	*/
	cout << "Lowest char value: " << CHAR_MIN << endl << "Highest char value: " << CHAR_MAX << endl
		<< "Lowest int value: " << INT_MIN << endl << "Highest int value: " << INT_MAX << endl
		<< "Lowest long long value: " << LLONG_MIN << endl << "Highest long long value: " << LLONG_MAX << endl;
}
