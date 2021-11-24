#include <iostream>
using namespace std;

const int ARRAY_LENGTH = 10;

void inputArray(int numbers[]);
void printArray(int numbers[]);
void printArray(int numbers[], int length);
void separatePositiveAndNegative(int numbers[], int positive[], int negative[], int& positiveCount, int& negativeCount);

int main()
{
	setlocale(LC_ALL, "BG");

	int numbers[ARRAY_LENGTH];
	int positive[ARRAY_LENGTH];
	int negative[ARRAY_LENGTH];

	int positiveCount = 0;
	int negativeCount = 0;

	cout << "Въвеждане на " << ARRAY_LENGTH << " числа:" << endl;
	inputArray(numbers);

	separatePositiveAndNegative(numbers, positive, negative, positiveCount, negativeCount);

	printArray(positive, positiveCount);
	cout << endl;
	printArray(negative, negativeCount);

}

void inputArray(int numbers[])
{
	for (int i = 0; i < ARRAY_LENGTH; i++) cin >> numbers[i];
}

void printArray(int numbers[])
{
	for (int i = 0; i < ARRAY_LENGTH; i++) cout << numbers[i] << " ";
}

void printArray(int numbers[], int length)
{
	for (int i = 0; i < length; i++) cout << numbers[i] << " ";
}

void separatePositiveAndNegative(int numbers[], int positive[], int negative[], int& positiveCount, int& negativeCount)
{
	for (int i = 0; i < ARRAY_LENGTH; i++)
	{
		if (numbers[i] >= 0)
		{
			positive[positiveCount] = numbers[i];
			positiveCount++;
		}
		else
		{
			negative[negativeCount] = numbers[i];
			negativeCount++;
		}
	}
}

