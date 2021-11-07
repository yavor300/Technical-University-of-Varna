#include <iostream>
#include <math.h>
using namespace std;

/*
* Задача 10
*
* Съставете програма с функция която намира корените на квадратно уравнение от вида ax2+bx+c=0,
* където a, b и с се въвеждат от клавиатурата, функцията връща като резултат true или  false,
* съответно  ако  уравнението  има  решение  или  няма  решение.
* Корените  на уравнението да се присвояват на две променливи, предавани по адрес към функцията 
*
*/

double inputNumber();
bool solveQuadraticEquation(double a, double b, double c, double& firstRoot, double& secondRoot, bool& everyRealNumberIsSolution, bool& hasSingleRoot);

int main()
{
	setlocale(LC_ALL, "BG");

	double a = inputNumber();
	double b = inputNumber();
	double c = inputNumber();

	double firstRoot, secondRoot;

	bool everyRealNumberIsSolution = false;
	bool hasSingleRoot = false;

	if (!solveQuadraticEquation(a, b, c, firstRoot, secondRoot, everyRealNumberIsSolution, hasSingleRoot))
	{
		cout << "Уравнението няма реални корени.";
		return 0;
	}

	if (everyRealNumberIsSolution) cout << "Всяко реално число е решение на уравнението.";
	else if (hasSingleRoot) cout << "Коренът на уравнението е " << firstRoot;
	else cout << "Корените на уравнението са: " << firstRoot << " и " << secondRoot;
}

bool solveQuadraticEquation(double a, double b, double c, double& firstRoot, double& secondRoot, bool& everyRealNumberIsSolution, bool& hasSingleRoot)
{
	cout.setf(ios::fixed);
	cout.precision(2);

	if (a == 0)
	{
		if (b == 0)
		{
			if (c == 0)
			{
				everyRealNumberIsSolution = true;
				return true;
			}
			else
			{	
				return false;
			}
		}
		else
		{
			hasSingleRoot = true;
			firstRoot = -c / b;
			return true;
		}
	}
	else
	{
		double discriminant = pow(b, 2) - 4 * a * c;

		if (discriminant < 0)
		{
			return false;
		}
		else if (discriminant > 0)
		{
			firstRoot = (-b + sqrt(discriminant)) / (2 * a);
			secondRoot = (-b - sqrt(discriminant)) / (2 * a);		
			return true;
		}
		else
		{
			hasSingleRoot = true;
			firstRoot = -b / (2 * a);
			return true;
		}
	}
}

double inputNumber()
{
	double number;
	cin >> number;
	return number;
}
