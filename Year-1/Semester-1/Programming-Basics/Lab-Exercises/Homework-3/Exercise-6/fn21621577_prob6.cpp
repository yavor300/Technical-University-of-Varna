#include <iostream>
using namespace std;

/*
* Задача 6
*
* Съставете програма с функции за:
*	Въвеждане  на  реално  число.
*		Приема  като  входни  параметри  символа  на променливата,  която  ще  се  въвежда.
*		Връща  като  резултат  стойността  на въведеното число.
*
*	Изчисление на x = 2((a−b)/(b−a)).
*		Приема като входни параметри променливите a и b и връща като резултат отговора на уравнението.
*		Във функция main въведете две променливи и изведете резултата от изчислението на формулата.
*/

double inputNumber();
double calculateExpression(double a, double b);

void main()
{
	cout << "Enter two numbers:" << endl;

	double a = inputNumber();
	double b = inputNumber();

	cout << "2((a-b)/(b-a)) = " << calculateExpression(a, b);
}

double inputNumber()
{
	double number;
	cin >> number;
	return number;
}

double calculateExpression(double a, double b)
{
	return 2 * ((a - b) / (b - a));
}

