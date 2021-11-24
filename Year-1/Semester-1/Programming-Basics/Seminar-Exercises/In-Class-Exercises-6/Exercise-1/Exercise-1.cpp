#include <iostream>
using namespace std;

const int ARRAY_LENGTH = 10;

void inputArray(int numbers[]);
void printArray(int numbers[]);
int countElementsInInterval(int numbers[], int& startInterval, int& endInterval);

int main()
{
	setlocale(LC_ALL, "BG");

	int numbers[ARRAY_LENGTH];
	cout << "Въвеждане на " << ARRAY_LENGTH << " числа:" << endl;
	inputArray(numbers);

	cout << "Отпечатване елементите на един ред: ";
	printArray(numbers);
	
	cout << endl << "Въвеждане на начална и крайна граница: " << endl;
	int limit1, limit2;
	cin >> limit1 >> limit2;

	int count = countElementsInInterval(numbers, limit1, limit2);
	cout << "Брой елементи в интрвала: " << count;
}

void inputArray(int numbers[])
{
	for (int i = 0; i < ARRAY_LENGTH; i++) cin >> numbers[i];
}

void printArray(int numbers[])
{
	for (int i = 0; i < ARRAY_LENGTH; i++) cout << numbers[i] << " ";
}

int countElementsInInterval(int numbers[], int& startInterval, int& endInterval)
{
	int count = 0;
	for (int i = 0; i < ARRAY_LENGTH; i++)
	{
		if (numbers[i] >= startInterval && numbers[i] <= endInterval) count++;
	}
	return count;
}

