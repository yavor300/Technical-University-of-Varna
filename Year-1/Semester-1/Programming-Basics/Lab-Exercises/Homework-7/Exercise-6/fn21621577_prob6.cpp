#include <iostream>
#include <string>
#include <Windows.h>
using namespace std;

/*
* Задача 6
*
* Съставете програма, която декларира масив от 5 числа тип int който се въвеждат от клавиатурата
* с помощта на функции. С функция се проверява дали масивът е симетричен (например 1 3 2 3 1),
* при което да се изведе „Да” или “Не”.
*/

const int NUMBERS_COUNT = 5;

void inputArray(int numbers[]);
bool isArraySymmetric(int numbers[]);

int main()
{
	setlocale(LC_ALL, "BG");
	SetConsoleOutputCP(1251);
	SetConsoleCP(1251);

	int numbers[NUMBERS_COUNT];
	inputArray(numbers);

	if (isArraySymmetric(numbers)) cout << "Да";
	else cout << "Не";
}

bool isArraySymmetric(int numbers[])
{
	for (int i = 0; i < NUMBERS_COUNT / 2; i++)
	{
		if (numbers[i] != numbers[NUMBERS_COUNT - i - 1]) return false;
	}
	return true;
}

void inputArray(int numbers[])
{
	for (int i = 0; i < NUMBERS_COUNT; i++)
	{
		cin >> numbers[i];
	}
}
