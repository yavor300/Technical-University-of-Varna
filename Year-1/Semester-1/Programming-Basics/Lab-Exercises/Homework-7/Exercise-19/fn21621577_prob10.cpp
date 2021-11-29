#include <iostream>
#include <string>
using namespace std;

/*
* Задача 10
*
* Съставете програма, дефинираща структура, описваща правоъгълник и функции:
*	Въвеждане на структурата. Функцията не приема параметри. Връща като резултат променлива от типа на структурата.
*	Извеждане лицето на правоъгълника. Приема като параметър променлива от типа на структурата и връща като резултат стойността на лицето му.
* Във функция main да се създаде променлива от типа на структурата и да се изведе лицето на правоъгълника. 
*/

const int ARRAY_LENGTH = 7;

struct Rectangle
{
	double a;
	double b;
};

Rectangle initRectangle(double a, double b)
{
	Rectangle rectagle;
	rectagle.a = a;
	rectagle.b = b;

	return rectagle;
}

double calculateArea(Rectangle rectangle)
{
	return rectangle.a * rectangle.b;
}

int main()
{
	double a, b;
	cin >> a >> b;
	Rectangle rectangle = initRectangle(a, b);
	cout << calculateArea(rectangle);
}
