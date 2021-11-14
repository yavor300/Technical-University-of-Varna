#include <iostream>
using namespace std;

/*
* Задача 7
*
* Съставете програма, в която последователно да се въвеждат 10 числа.
* Да се преброят положителните и да се изведе техният брой.
*/

const int numbersCount = 10;

int countPositiveNumbers(int numbersCount);

void main()
{
	setlocale(LC_ALL, "BG");

	int result = countPositiveNumbers(numbersCount);
	printf("Положителните числа са: %d", result);
}

int countPositiveNumbers(int numbersCount)
{
	int positiveCounter = 0;

	for (int i = 1; i <= numbersCount; i++)
	{
		int number;
		cin >> number;
		if (number > 0) positiveCounter++;
	}

	return positiveCounter;
}
