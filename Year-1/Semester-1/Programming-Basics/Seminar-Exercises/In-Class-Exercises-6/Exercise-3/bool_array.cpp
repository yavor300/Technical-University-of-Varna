#include <iostream>
using namespace std;

const int ARRAY_LENGTH = 10;

void createNewBoolArray(int numbers[], bool result[], int max);
int findMaxElement(int numbers[]);
void inputArray(int numbers[]);
void printArray(bool result[]);

int main()
{
	setlocale(LC_ALL, "BG");

	int numbers[ARRAY_LENGTH];
	bool result[ARRAY_LENGTH];

	cout << "Въвеждане на " << ARRAY_LENGTH << " числа:" << endl;
	inputArray(numbers);

	createNewBoolArray(numbers, result, findMaxElement(numbers));
	
	printArray(result);
}

void createNewBoolArray(int numbers[], bool result[], int max)
{
	for (int i = 0; i < ARRAY_LENGTH; i++)
	{
		if (max - numbers[i] == 2) result[i] = true;
		else result[i] = false;
	}
}

int findMaxElement(int numbers[])
{
	int max = numbers[0];
	for (int i = 1; i < ARRAY_LENGTH; i++)
	{
		if (numbers[i] > max) max = numbers[i];
	}
	return max;
}

void inputArray(int numbers[])
{
	for (int i = 0; i < ARRAY_LENGTH; i++) cin >> numbers[i];
}

void printArray(bool result[])
{
	for (int i = 0; i < ARRAY_LENGTH; i++) cout << boolalpha << result[i] << " ";
}
