#include <iostream>
using namespace std;

/*
* Задача 4
*
* Съставете програма с функция за извеждане на масива от символи в обратен ред на въвеждането.
* Функцията да приема параметри масив и броя на елементите в масива.
* В main се въвеждат и извеждат стойностите на масива.
*/

const int initialArrayLength = 10;

void printArray(char arr[], int symbolsCount);

int main()
{
	int symbolsCount;
	cin >> symbolsCount;

	char symbols[initialArrayLength];
	cin >> symbols;

	printArray(symbols, symbolsCount + 1);
}

void printArray(char arr[], int symbolsCount)
{
	for (int i = symbolsCount - 1; i >= 0; i--)
	{
		cout << arr[i];
	}
}
