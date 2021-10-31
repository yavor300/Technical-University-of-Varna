#include <iostream>
#include <math.h>
using namespace std;

/*
* Задача 7
*
* Съставете програма с функции за:
*	Въвеждане  на  реално  число.
*		Приема  като  входни  параметри  символа  на променливата,  която  ще  се  въвежда.
*		Връща  като  резултат  стойността  на въведеното число.
*
*	Изчисление на x = (a+b)³ + (a² + 2ba – 2ab + b²)
*		Приема като входни параметри променливите a и b и връща като резултат отговора на уравнението.
* 
* Във функция main въведете две променливи и изведете резултата от изчислението на формулата.
*/

double inputNumber();
double calculateExpression(double a, double b);

void main()
{
	cout << "Enter two numbers:" << endl;

	double a = inputNumber();
	double b = inputNumber();

	cout << "(a+b) ^ 3 + (a ^ 2 + 2ba - 2ab + b ^ 2) = " << calculateExpression(a, b);
}

double inputNumber()
{
	double number;
	cin >> number;
	return number;
}

double calculateExpression(double a, double b)
{
	return pow((a + b), 3) + (pow(a, 2) + pow(b, 2));
}

