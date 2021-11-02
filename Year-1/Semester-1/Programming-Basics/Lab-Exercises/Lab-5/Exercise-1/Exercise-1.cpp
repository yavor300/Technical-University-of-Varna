#include <iostream>
using namespace std;

/*
* Задача 1
*
* Съставете програма с функция за въвеждане на реално число.
* Функцията не връща стойност.
* Функцията приема като входен параметър адреса на променлива от реален тип данни.
* На подадената променлива като входен параметър се присвоява реално число, въведено от клавиатурата.
* Main функцията дефинира променлива от реален тип с начална стойност 0, извежда стойността на променливата преди и след извикването на функцията
*/

void assignValueToAddress(double& address);

void main()
{
	setlocale(LC_ALL, "BG");

	double a = 0;
	cout << a << endl;

	assignValueToAddress(a);
	cout << a;
}

void assignValueToAddress(double& address)
{
	double number;
	cin >> number;

	address = number;
}
