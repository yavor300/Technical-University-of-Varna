#include <iostream>
using namespace std;

double calculateAreaOnEquilateralTriangle(double sideSize);
double calculateAreaOnIsosclesTriangle(double sideSize, double height);


int main()
{
	double a, areaOnEquilateralTriangle;
	cin >> a;

	areaOnEquilateralTriangle = calculateAreaOnEquilateralTriangle(a);
	cout << "Equilateral Triangle Area: " << areaOnEquilateralTriangle << endl;

	double b, hb, areaOnIsosclesTriangle;
	cin >> b >> hb;

	areaOnIsosclesTriangle = calculateAreaOnIsosclesTriangle(b, hb);
	cout << "Isoscles Triangle Area: " << areaOnIsosclesTriangle;
}

double calculateAreaOnEquilateralTriangle(double sideSize) {
	return (sqrt(3) / 4) * pow(sideSize, 2);
}

double calculateAreaOnIsosclesTriangle(double sideSize, double height)
{
	return (sideSize * height) / 2;
}