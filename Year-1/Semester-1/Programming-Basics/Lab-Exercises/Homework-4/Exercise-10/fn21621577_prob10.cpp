#include <iostream>
#include <math.h>
using namespace std;

/*
* ������ 10
*
* ��������� �������� � ������� ����� ������ �������� �� ��������� ��������� �� ���� ax2+bx+c=0,
* ������ a, b � � �� �������� �� ������������, ��������� ����� ���� �������� true ���  false,
* ���������  ���  �����������  ���  �������  ���  ����  �������.
* ��������  �� ����������� �� �� ���������� �� ��� ����������, ��������� �� ����� ��� ��������� 
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
		cout << "����������� ���� ������ ������.";
		return 0;
	}

	if (everyRealNumberIsSolution) cout << "����� ������ ����� � ������� �� �����������.";
	else if (hasSingleRoot) cout << "������� �� ����������� � " << firstRoot;
	else cout << "�������� �� ����������� ��: " << firstRoot << " � " << secondRoot;
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
