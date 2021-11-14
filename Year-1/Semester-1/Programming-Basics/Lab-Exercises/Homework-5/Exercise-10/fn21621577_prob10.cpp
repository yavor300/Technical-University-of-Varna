#include <iostream>
using namespace std;

/*
* Задача 10
*
* Съставете програма,която да намира сумата от цифрата на единиците,
* десетиците и стотиците на цели четирицифрени числа в интервала [-1234;1234].
* Ако се въведе число извън интервала, да се подкани за повторно въвеждане.
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
