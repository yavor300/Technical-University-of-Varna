#include <iostream>
#include <string>
using namespace std;

/*
* Задача 10
*
* Съставете програма с функция за преброяване на числата, кратни на Х, от елементите на масив от цели числа.
* Функцията да приема параметри масив и броя на елементите в масива и да връща като резултата броя на числата, кратни на Х.
* В main се въвежда брой елементи на масива (от 1 до 10) и стойност за Х, като програмата да продължи,
* след като се въведе валиден размер на масива.  Въвежда се масива и извежда броя на числата, кратни на Х.
*/

void fillArray(int arr[], int length);
int countDivision(int arr[], int length, int divideBy);
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
	int numbers[10];
	fillArray(numbers, lenght);

	cout << "Делител: ";
	int divideBy;
	cin >> divideBy;

	int devisionCounter = countDivision(numbers, lenght, divideBy);
	cout << "Кратни на " << divideBy << ": " << devisionCounter << endl;
	cout << "Не са кратни на " << divideBy << ": " << lenght - devisionCounter;

	
}


void fillArray(int arr[], int length)
{
	for (int i = 0; i < length; i++)
	{
		cin >> arr[i];
	}
}

int countDivision(int arr[], int length, int divideBy)
{
	int result = 0;
	for (int i = 0; i < length; i++)
	{
		if (arr[i] % divideBy == 0) result++;
	}
	return result;
}

bool isLengthValid(int lenght)
{
	return lenght >= 1 && lenght <= 10;
}
