#include <iostream>
#include <string>
#include <Windows.h>
using namespace std;

/*
* Задача 7
*
* Съставете програма с функции за:
*	Въвеждане на масив от 5 числа в интервала от [5, 50]
*	Извеждане стойностите на масива
*	Намиране на най-малкия четен елемент в масива.
* Във функцията main съставете меню за избор на функция 2 и функция 3.
*/

const int NUMBERS_COUNT = 5;

void inputArray(int numbers[]);
void printArray(int numbers[]);
int findLowestEvenNumber(int numbers[]);

int main()
{
	setlocale(LC_ALL, "BG");

	int numbers[NUMBERS_COUNT];
	inputArray(numbers);

	cout << "Извеждане стойностите на масива: 1" << endl;
	cout << "Намиране на най-малкия четен елемент в масива: 2" << endl;

	int option;
	cin >> option;
	if (option == 1) printArray(numbers);
	else if (option == 2)
	{
		int lowestEven = findLowestEvenNumber(numbers);
		if (lowestEven == -1) cout << "В масива няма четни стойности";
		else cout << lowestEven;
	}
}

int findLowestEvenNumber(int numbers[])
{
	int result = -1;
	bool containsPositive = false;
	for (int i = 0; i < NUMBERS_COUNT; i++)
	{
		if (numbers[i] % 2 == 0)
		{
			result = numbers[i];
			containsPositive = true;
			break;
		}
	}

	if (!containsPositive) return -1;

	for (int i = 0; i < NUMBERS_COUNT; i++)
	{
		if (numbers[i] % 2 == 0 && numbers[i] < result) result = numbers[i];
	}
	return result;
}

void printArray(int numbers[])
{
	for (int i = 0; i < NUMBERS_COUNT; i++) cout << numbers[i] << " ";
}

void inputArray(int numbers[])
{
	for (int i = 0; i < NUMBERS_COUNT; i++)
	{
		cin >> numbers[i];
	}
}
