#include <iostream>
using namespace std;

/*
* Задача 3
*
* Съставете програма с функция за намиране обиколката и лицето на ромб,
* която приема параметри по адрес за запазване на обиколката и лицето,
* и параметри за основата и височината по стойност, които са въведени от клавиатурата. 
*/

const int rhombusSidesCount = 4;

void calculateRhombusPerimeter(double sideSize, double &perimeter);
void calculateRhombusArea(double sideSize, double height, double &area);
void readDoubleInput(string message, double &value);

void main()
{
	double sideSize, height, perimeter, area;
	readDoubleInput("Enter the size of the rhombus side: ", sideSize);
	readDoubleInput("Enter the size of the rhombus height: ", height);

	calculateRhombusPerimeter(sideSize, perimeter);
	cout << "Rhombus perimeter: " << perimeter << endl;

	calculateRhombusArea(sideSize, height, area);
	cout << "Rhombus area: " << area;
}

void calculateRhombusPerimeter(double sideSize, double &perimeter)
{
	perimeter = sideSize * rhombusSidesCount;
}

void calculateRhombusArea(double sideSize, double height, double &area)
{
	area = sideSize * height;
}

void readDoubleInput(string message, double &value)
{
	cout << message;
	
	double readValue;
	cin >> readValue;
	value = readValue;
}