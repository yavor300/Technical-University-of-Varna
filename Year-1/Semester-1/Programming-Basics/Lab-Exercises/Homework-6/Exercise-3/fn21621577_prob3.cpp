#include <iostream>
using namespace std;

/*
* Задача 3
* 
* Съставете програма с функция за въвеждане на масив от реални числа и функция за извеждане на масива
* от реални числа в обратен ред на въвеждането. Функциите да приемат параметри масив и броя на елементите
* в масива. В main се въвежда брой елементи на масива (от 1 до 10), като програмата да продължи, след
* като се въведе валиден размер на масива. След това да се въвеждат и изведат стойностите на масивите.
*/

void fillArray(double arr[], int length);
void printArray(double arr[], int length);
bool isLengthValid(int lenght);

int main()
{
	setlocale(LC_ALL, "BG");

	cout << "Въведете размер на масива от 1 до 10: ";
	double lenght;
	cin >> lenght;
	if (!isLengthValid(lenght))
	{
		cout << "Невалиден размер на масива.";
		return 0;
	}
	cout << "Въвеждане на масив от: " << lenght << " елемента:" << endl;

	double numbers[10];
	fillArray(numbers, lenght);
	printArray(numbers, lenght);
}


void fillArray(double arr[], int length)
{
	for (int i = 0; i < length; i++)
	{
		cout << "Елемент " << i + 1 << ": ";
		cin >> arr[i];
	}
}

void printArray(double arr[], int length)
{
	for (int i = length - 1; i >= 0; i--)
	{
		cout << "Елемент " << length - i << " -> " << arr[i] << endl;
	}
}

bool isLengthValid(int lenght)
{
	return lenght >= 1 && lenght <= 10;
}