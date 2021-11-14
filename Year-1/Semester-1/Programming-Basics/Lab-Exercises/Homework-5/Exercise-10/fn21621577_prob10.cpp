#include <iostream>
using namespace std;

/*
* Задача 9
*
* Съставете  програма,  вкоято  последователно  да  се  въвеждат  числа.
* Да се  преброят положителните и при въвеждане на 0 да се изведе техния брой.
*/

int sumDigits(int number);
bool isNumberValid(int number);

void main()
{
	setlocale(LC_ALL, "BG");

	int number;
	cin >> number;
	while (!isNumberValid(number))
	{
		system("cls");
		cout << "Невалидно число! Въведи повторно число: ";
		cin >> number;
	}

	int result = sumDigits(number);
	cout << result;
}

int sumDigits(int number)
{
	int sum = 0;
	while (number > 0)
	{
		sum += number % 10;
		number /= 10;
	}
	return sum;
}

bool isNumberValid(int number)
{
	return number >= -1234 && number <= 1234;
}
