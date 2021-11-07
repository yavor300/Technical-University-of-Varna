#include <iostream>
#include <math.h>
using namespace std;

/*
* Задача 4
*
* Създайте програма, в която се въвеждат от клавиатурата три целочислени положителни числа (x, y и z).
* Съставете функция която променя техните стойности, ако първото въведено число x е кратно на 3, у да
* удвои стойността си два пъти (2*y). Ако x не е кратно на 3, z да преизчисли своята стойност по формулата z = z^3+z^2+1.
*/

void changeValues(int x, int &y, int &z);

void main()
{
	int x, y, z;
	cin >> x >> y >> z;

	changeValues(x, y, z);

	cout << "x = " << x << endl << "y = " << y << endl << "z = " << z;
}

void changeValues(int x, int& y, int& z)
{
	if (x % 3 == 0) y *= 2;
	else z = pow(z, 3) + pow(z, 2) + 1;
}
