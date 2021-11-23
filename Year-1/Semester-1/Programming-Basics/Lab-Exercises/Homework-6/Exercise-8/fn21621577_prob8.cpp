#include <iostream>
#include <string>
using namespace std;

/*
* Задача 8
*
* Съставете програма с функция за изчисляване произведението от елементите на масив от реални числа.
* Функцията да приема параметри масив и брой на елементите в масива и да връща като резултат
* разликата от елементите на масива. В main се въвежда броя на елементите на масива (от 1 до 10),
* като програмата да продължи, след като се въведе валиден размер на масив.
* Въвежда се масива и се извежда разликата от елементите на масива.
*/

void fillArray(double arr[], int length);
double caclulateAverage(double arr[], int length);
bool isLengthValid(int lenght);

int main()
{
	setlocale(LC_ALL, "BG");

	cout << "Въведете размер на масива от 1 до 10: ";
	double lenght;
	cin >> lenght;
	if (!isLengthValid(lenght))
	{
		cout << "Невалиден размер на масива.";
		return 0;
	}
	cout << "Въвеждане на масив от: " << lenght << " елемента:" << endl;

	double numbers[10];
	fillArray(numbers, lenght);
	cout << caclulateAverage(numbers, lenght);
}


void fillArray(double arr[], int length)
{
	for (int i = 0; i < length; i++)
	{
		cin >> arr[i];
	}
}

double caclulateAverage(double arr[], int length)
{
	double result = 0;

	for (int i = 0; i < length; i++)
	{
		result += arr[i];
	}

	return result /= length;
}

bool isLengthValid(int lenght)
{
	return lenght >= 1 && lenght <= 10;
}
