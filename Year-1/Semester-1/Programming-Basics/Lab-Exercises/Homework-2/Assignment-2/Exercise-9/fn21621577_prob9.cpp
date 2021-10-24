#include <iostream>
#include <math.h>
using namespace std;

/*
* Задача 9
* Съставете програма за решаване на уравненията
* 
* x = y ^ 3 + (y ^ 4 + 2y) ако y e <= 1
* x = 2(2y + 5)/(14 – y/3) ако y > 1 
*/

int main()
{
	double x1, y1, x2, y2;
	cin >> y1 >> y2;

	/* 
	* Решаване на уравнението x = y ^ 3 + (y ^ 4 + 2y) ако y e <= 1,
	*  където x = x1 и y = y1
	*/
	if (y1 > 1)
	{
		printf("%.2f is greater than 1\n", y1);
	}
	else
	{
		x1 = pow(y1, 3) + (pow(y1, 4) + 2 * y1);
		printf("y ^ 3 + (y ^ 4 + 2y) = %.2f    y = %.2f [y <= 1]\n", x1, y1);
	}

	/*
	* Решаване на уравнението x = 2(2y + 5)/(14 – y/3) ако y > 1,
	* където x = x2 и y = y2
	*/
	if (y2 <= 1)
	{
		printf("%.2f is lower than or equal to 1\n", y2);
	}
	else
	{
		x2 = 2 * (2 * y2 + 5) / (14 - y2 / 3);
		printf("2(2y + 5)/(14 - y/3) = %.2f    y = %2.f [y > 1]", x2, y2);
	}
}