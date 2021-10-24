#include <iostream>
using namespace std;

/*
* Задача 5
* Съставете програма за намиране на по-голямото от 2 числа.
* Във функция main се дефинират 2 променливи от реален тип, въвеждат се от клавиатурата и се извежда по-голямото от двете.
* Не ползвайте вградени функции и програми.
*/

int main()
{
	double x, y;
	cin >> x >> y;

	if (x > y) printf("%.2f is greater than %.2f", x, y);
	else if (x < y) printf("%.2f is greater than %.2f", y, x);
	else cout << "Equal numbers";
}