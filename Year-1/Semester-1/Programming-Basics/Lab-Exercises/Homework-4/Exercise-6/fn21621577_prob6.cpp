#include <iostream>
using namespace std;

/*
* Задача 6
*
* Създайте програма, която изисква въвеждането на три реални числа (a, b, c).
* С помощта на функция установете кое е най-голямото и най-малкото от тях
* и разменете стойностите им така че стойноста на променливата „а“ да е най-голяма,
* стойността на променливата „c“ да е най-малка, а “b” да е с третата въведена стойност,
* изведете променливите със първоначалните им стойности и стойностите им след размяната.
* 
*/

void changeValues(double& a, double& b, double& c);
double findMax(double a, double b, double c);
double findMin(double a, double b, double c);

void main()
{
	double a, b, c;
	cin >> a >> b >> c;

	changeValues(a, b, c);

	cout << "Numbers after change: " << endl << "a = " << a << endl << "b = " << b << endl << "c = " << c;
}

void changeValues(double& a, double& b, double& c)
{
	double max = findMax(a, b, c);
	double min = findMin(a, b, c);

	a = max;
	b = c;
	c = min;
}

double findMax(double a, double b, double c)
{
	double max = a;
	if (b > max) max = b;
	if (c > max) max = c;

	return max;
}

double findMin(double a, double b, double c)
{
	double min = a;
	if (b < min) min = b;
	if (c < min) min = c;

	return min;
}
