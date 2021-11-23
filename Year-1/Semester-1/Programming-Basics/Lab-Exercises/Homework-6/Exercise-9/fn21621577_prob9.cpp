#include <iostream>
#include <string>
using namespace std;

/*
* Задача 9
*
* Съставете  програма  с  функция  за  преброяване  на  четните  и  нечетните  числа  от елементите
* на  масив  от  цели  числа.  Функцията  да  приема  параметри  масив  и  броя  на елементите в масива
* и да връща резултата в параметри, предадени по адрес. В main се въвежда брой  елементи  на  масива  (от  1  до  10),
* като  програмата  да  продължи  след  като  се  въведе валиден размер на масив.
* Въвежда се масива и се извежда броя на четните и нечетните числа. 
*/

void fillArray(int arr[], int length);
int countEven(int arr[], int length);
int countOdd(int arr[], int length);
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
	cout << "Четни: " << countEven(numbers, lenght) << endl;
	cout << "Нечетни: " << countOdd(numbers, lenght) << endl;
}


void fillArray(int arr[], int length)
{
	for (int i = 0; i < length; i++)
	{
		cin >> arr[i];
	}
}

int countEven(int arr[], int length)
{
	int result = 0;
	for (int i = 0; i < length; i++)
	{
		if (arr[i] % 2 == 0) result++;
	}
	return result;
}

int countOdd(int arr[], int length)
{
	int result = 0;
	for (int i = 0; i < length; i++)
	{
		if (arr[i] % 2 != 0) result++;
	}
	return result;
}


bool isLengthValid(int lenght)
{
	return lenght >= 1 && lenght <= 10;
}
