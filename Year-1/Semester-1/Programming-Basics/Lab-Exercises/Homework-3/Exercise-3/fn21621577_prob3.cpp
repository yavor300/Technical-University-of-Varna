#include <iostream>
using namespace std;

/*
* Задача 3
*
* Съставете програма функции за:
* 
*	Въвеждане  на  реално  число.
*		Приема  като  входни  параметри  символа  на променливата,  която  ще  се  въвежда.
*		Връща  като  резултат  стойността  на въведеното число.
* 
*	Изчисление на средната стойност на четири числа.
*		Във функция main въведете четири цели числа, изведете средната им стойност.
*/
double getNumber(double number);
double calculateAverage(double num1, double num2, double num3, double num4);
int main()
{
	double a, b, c, d;
	cin >> a >> b >> c >> d;

	cout << "Average value: " << calculateAverage(a, b, c, d);
}

double getNumber(double number)
{
	return number;
}

double calculateAverage(double num1, double num2, double num3, double num4)
{
	return (getNumber(num1) + getNumber(num2) + getNumber(num3) + getNumber(4)) / 4;
}