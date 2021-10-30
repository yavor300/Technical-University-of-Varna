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
double inputNumber();
double calculateAverage(double num1, double num2, double num3, double num4);

int main()
{
	double a = inputNumber();
	double b = inputNumber();
	double c = inputNumber();
	double d = inputNumber();

	cout << "Average value: " << calculateAverage(a, b, c, d);
}

double inputNumber()
{
	double number;
	cin >> number;
	return number;
}

double calculateAverage(double num1, double num2, double num3, double num4)
{
	return (num1 + num2 + num3 + num4) / 4;
}