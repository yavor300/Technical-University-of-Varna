#define _USE_MATH_DEFINES
#include <iostream>
#include <math.h>
using namespace std;

/*
* Задача 4
*
* Съставете програма с функции, която да намира обема и лицето на повърхнината
* на цилиндър при въведени от клавиатурата радиус и височина.
*/

double calculateVolume(double radius, double height);
double calculateArea(double radius);
int main()
{
	double radius, height;
	cin >> radius >> height;

	cout << "Volume: " << calculateVolume(radius, height) << endl;
	cout << "Area: " << calculateArea(radius);
}

double calculateVolume(double radius, double height)
{
	return calculateArea(radius) * height;
}

double calculateArea(double radius)
{
	return M_PI * pow(radius, 2);
}
