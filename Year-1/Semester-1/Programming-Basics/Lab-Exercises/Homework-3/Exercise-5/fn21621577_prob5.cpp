#include <iostream>
using namespace std;

/*
* Задача 5
*
* Съставете програма с функция, която да установява дали точка с дадени координати
* (въведени от клавиатурата) попада в първи квадрант на координатната система.
*/

bool isPointInFirstQuadrant(double x, double y);

int main()
{
	double x, y;
	cin >> x >> y;

	if (isPointInFirstQuadrant(x, y)) cout << "The point lies in the first quadrant";
	else cout << "The point does not lie in the first quadrant";
}

bool isPointInFirstQuadrant(double x, double y)
{
	return x > 0 && y > 0;
}
