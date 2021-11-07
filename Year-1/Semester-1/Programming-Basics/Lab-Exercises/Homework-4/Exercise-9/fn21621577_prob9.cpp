#include <iostream>
using namespace std;

/*
* Задача 9
*
* Създайте програма в която да се въведат от клавиятурата три страни на триъгалник от реален тип.
* С помоща на функция проверете дали въведените числа могат да са страни на триъгалник:
*	-ако са страни на триъгалник функцията връща като резултат true ако не са връща false
*	-функцията  също  така  изчислява  обиколката  и  лицено  на  триъгалника,
*	които присвоява на две променливи, предавани по адрес към функцията
*
*/

void calculatePerimeter(double a, double b, double c, double& perimeter);
void calculatArea(double a, double heightA, double& area);
bool isTriangleValid(double a, double b, double c);

int main()
{
	cout << "Enter values for sides: ";
	double a, b, c;
	cin >> a >> b >> c;

	if (!isTriangleValid(a, b, c))
	{
		cout << "Invalid triangle";
		return 0;
	}

	cout << "Triangle is valid." << endl << "Enter value for height of the side - a: ";
	double heightA, perimeter, area;
	cin >> heightA;

	calculatePerimeter(a, b, c, perimeter);
	calculatArea(a, heightA, area);

	cout << "P: " << perimeter << endl;
	cout << "S: " << area;
}

void calculatePerimeter(double a, double b, double c, double& perimeter)
{
	perimeter = a + b + c;
}

void calculatArea(double a, double heightA, double& area)
{
	area = (a * heightA) / 2;
}

bool isTriangleValid(double a, double b, double c)
{
	return a + b > c && b + c > a && a + c > b;
}
