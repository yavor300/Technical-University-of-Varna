#include <iostream>
using namespace std;

/*
* Задача 10
*
* Съставете програма с функция която намира корените на квадратно уравнение от вида ax2+bx+c=0,
* където a, b и с се въвеждат от клавиатурата. 
*/

double inputNumber();
void solveQuadraticEquation(double a, double b, double c);

void main()
{
	setlocale(LC_ALL, "BG");

	double a = inputNumber();
	double b = inputNumber();
	double c = inputNumber();
	
	solveQuadraticEquation(a, b, c);
}

void solveQuadraticEquation(double a, double b, double c)
{
	cout.setf(ios::fixed);
	cout.precision(2);

	if (a == 0)
	{
		if (b == 0)
		{
			if (c == 0)
			{
				cout << "Всяко реално число е решение на уравнението.";
			}
			else
			{
				cout << "Уравнението няма реални корени.";
			}
		}
		else
		{
			cout << "Коренът на уравнението е " << -c / b;
		}
	}
	else
	{
		double discriminant = pow(b, 2) - 4 * a * c;

		if (discriminant < 0)
		{
			cout << "Уравнението няма реални корени.";
		}
		else if (discriminant > 0)
		{
			double firstAnswer = (-b + sqrt(discriminant)) / (2 * a);
			double secondAnswer = (-b - sqrt(discriminant)) / (2 * a);

			cout << "Корените на уравнението са: " << firstAnswer << " и " << secondAnswer;
		}
		else
		{
			cout << "Коренът на уравнението е " << -b / (2 * a);
		}
	}
}

double inputNumber()
{
	double number;
	cin >> number;
	return number;
}
