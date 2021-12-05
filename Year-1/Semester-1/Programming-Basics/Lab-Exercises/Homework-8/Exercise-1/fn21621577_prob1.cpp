#include <iostream>
using namespace std;

/*
* Задача 1
*
* Да се напише програма, която декларира масив от 5 числа тип int,
* въвежда ги от клавиатурата, сортира ги и ги извежда в низходящ ред.
*/

const int ARRAY_SIZE = 5;

void sort_descending(int arr[], int k, int sortarr[]);
void inputArray(int numbers[]);
void printArray(int numbers[]);

int main()
{
	int numbers[ARRAY_SIZE];
	int sorted_numbers[ARRAY_SIZE];
	inputArray(numbers);
	sort_descending(numbers, ARRAY_SIZE, sorted_numbers);
	printArray(sorted_numbers);
}

void sort_descending(int arr[], int k, int sortarr[])
{
	for (int i = 0; i < k; i++) sortarr[i] = arr[i];

	for (int i = 0; i < k - 1; i++)
	{
		bool swapped = false;
		for (int j = 0; j < k - 1 - i; j++)
		{
			if (sortarr[j] < sortarr[j + 1])
			{
				int temp = sortarr[j];
				sortarr[j] = sortarr[j + 1];
				sortarr[j + 1] = temp;
				swapped = true;
			}
		}
		if (!swapped) break;
	}
}

void inputArray(int numbers[])
{
	for (int i = 0; i < ARRAY_SIZE; i++)
	{
		cin >> numbers[i];
	}
}

void printArray(int numbers[])
{
	for (int i = 0; i < ARRAY_SIZE; i++) cout << numbers[i] << " ";
}
