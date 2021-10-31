#include <iostream>
using namespace std;

/*
* Задача 8
*
* Влак се движи от гара А до гара В, който са на разстояние 987 километра.
* Съставете програма с функции за:
*	Изчисление на необходимата скорост на влака.
*		Функцията приема като входен параметър времето за което влака трябва да измине
*		разстоянието от гара А до гара В.
*		Връща като резултат скоростта с която трябва да се движи влака.
*	
*	Изчисления на времето за пътуване.
*		Функцията приема като входен параметър скоростта с която се движи влака и връща като резултат
*		времето за което ще измине разстоянието от гара А до гара В.
*
* Във функция main въведете време за пътуване в цели часове и изведете скоростта на влака
* и въведете скорост в цяло число и изведете времето за пътуване.
*/

#define distance 987.0

double inputNumber();
double calculateVelocity(int hours);
double calculateTime(int velocity);

void main()
{
	cout << "Hours: ";;
	int hours = inputNumber();
	cout << "Velocity: " << calculateVelocity(hours) << " km/h" << endl;

	cout << "Velocity : ";
	int velocity = inputNumber();
	cout << "Hours: " << calculateTime(velocity) << " h";
}

double inputNumber()
{
	double number;
	cin >> number;
	return number;
}

double calculateVelocity(int hours)
{
	return distance / hours;
}

double calculateTime(int velocity)
{
	return distance / velocity;
}

