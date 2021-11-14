#include <iostream>
using namespace std;

/*
* Задача 8
*
* Съставете  програма,  вкоято  последователно  да  се  въвеждат  числа.
* Да се  преброят положителните и при въвеждане на 0 да се изведе техния брой.
*/

const int numbersCount = 10;

int countPositiveNumbers();

void main()
{
	setlocale(LC_ALL, "BG");

	int result = countPositiveNumbers();
	printf("Положителните числа са: %d", result);
}

int countPositiveNumbers()
{
	int positiveCounter = 0;
	
	int number;
	cin >> number;
	
	while (number)
	{
		if (number > 0) positiveCounter++;
		cin >> number;
	}

	return positiveCounter;
}
