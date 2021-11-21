#include <iostream>
using namespace std;

/*
* Задача 1
* 
* Съставете  програма  с  функция  за  извеждане  масив  от  реални  числа.
* Функцията  да приема два параметъра: масив и броя на елементите в масива.
* В main дефинирайте и изведете стойностите на масивите:
*/

void printArray(double arr[], int length, string arrName);
void printArray(float arr[], int length, string arrName);

int main()
{
	double first[8] = { 1, 2, 3, 4, 5, 6, 7, 8 };
	float second[10] = { 1.5, 2.6, 3.8, 4.9, 5.7, 6.8, 7.7, 8.8, 9.9, 10.10 };
	float third[5] = { 1.15, 1.26, 1.38, 1.49, 1.57 };

	printArray(first, 8, "first");
	printArray(second, 10, "second");
	printArray(third, 5, "third");
}


void printArray(double arr[], int length, string arrName)
{
	cout << arrName << ": ";
	for (int i = 0; i < length; i++)
	{
		cout << arr[i];
		if (i < length - 1) cout << ", ";
	}
	cout << endl;
}

void printArray(float arr[], int length, string arrName)
{
	cout << arrName << ": ";
	for (int i = 0; i < length; i++)
	{
		cout << arr[i];
		if (i < length - 1) cout << ", ";
	}
	cout << endl;
}
