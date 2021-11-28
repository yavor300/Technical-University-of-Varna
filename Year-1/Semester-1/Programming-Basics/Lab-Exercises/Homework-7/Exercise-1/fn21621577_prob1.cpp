#include <iostream>
using namespace std;

/*
* Задача 1
*
* Съставете програма, в която се декларира масив от 5 числа тип int, които се въвеждат от клавиатурата с функция.
* Въвежда се още едно допълнително число. Да се състави функция която да изведе индекса (индексите) на съответния елемент,
* ако допълнителното число се среща в масива, в противен случай - „Не се среща“. 
*/

const int NUMBERS_COUNT = 5;

void inputArray(int numbers[]);
int findIndex(int numbers[], int numberSearch);

int main()
{
	setlocale(LC_ALL, "BG");

	int numbers[NUMBERS_COUNT];
	inputArray(numbers);

	int numberSearch;
	cin >> numberSearch;

	int foundIndex = findIndex(numbers, numberSearch);
	if (foundIndex != -1) cout << foundIndex;
	else cout << "Не се среща";

}

void inputArray(int numbers[])
{
	for (int i = 0; i < NUMBERS_COUNT; i++)
	{
		cin >> numbers[i];
	}
}

int findIndex(int numbers[], int numberSearch)
{
	for (int i = 0; i < NUMBERS_COUNT; i++)
	{
		if (numbers[i] == numberSearch) return i;
	}
	return -1;
}
