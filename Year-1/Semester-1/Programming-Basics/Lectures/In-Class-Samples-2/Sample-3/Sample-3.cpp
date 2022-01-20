#include <iostream>
#include <Windows.h>
using namespace std;

const int ARR_SIZE = 10;
int sum(int index, int arr[]);

int main()
{
	setlocale(LC_ALL, "BG");
	SetConsoleOutputCP(1251);
	SetConsoleCP(1251);
	int arr[ARR_SIZE];
	cout << "Начални стойности на масива: ";
	for (int i = 0; i < ARR_SIZE; i++)
	{
		arr[i] = rand() % 50;
		cout << arr[i] << " ";
	}
	cout << "Сума: " << sum(ARR_SIZE - 1, arr) << endl;
}

int sum(int index, int arr[]) {
	if (index > 0) return (sum(index - 1, arr) + arr[index]);
	else return arr[0];
}
