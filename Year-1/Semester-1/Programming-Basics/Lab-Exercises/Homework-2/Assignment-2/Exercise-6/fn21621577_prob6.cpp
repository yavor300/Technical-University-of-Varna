#include <iostream>
using namespace std;

/*
* Задача 6
* Създайте програма, която сравнява три въведени от клавиатурата числа и извежда най-малкото от тях.
* Не ползвайте вградени функции и програми.
*/

int main()
{
	double a, b, c;
	cin >> a >> b >> c;

	double min = a;
	if (b < min) min = b;
	if (c < min) min = c;

	printf("Min: %.2f", min);
}