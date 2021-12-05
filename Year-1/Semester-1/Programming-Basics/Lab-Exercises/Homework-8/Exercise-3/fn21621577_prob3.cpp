#include <iostream>
#include <string>
#include <time.h>
using namespace std;

/*
* ������ 3
*
* ��������� �������� � ������� ��:
* 1.��������� ����� ��� �������� ��������� � ��������� �� [0, 999]
* 2.��������� ����������� �� ������
* 3.���������� ����������� �� ������ ��� �������� ���
* 4.���������� ����������� �� ������ � �������� ���
* ��� ��������� main ��������� ���� �� ����� �� ������� �� 2 �� 4.
*/
const int ARRAY_SIZE = 10;

void input_array(double numbers[], int& length);
void print_array(double numbers[], int& length);
void sort_ascending(double arr[], int k, double sortarr[]);
void sort_descending(double arr[], int k, double sortarr[]);

int main()
{
	setlocale(LC_ALL, "BG");
	cout.setf(ios::fixed);
	cout.precision(2);

	double numbers[ARRAY_SIZE];
	double sorted_numbers[ARRAY_SIZE];

	printf("�������� ������ �� ������ (����: %d): ", ARRAY_SIZE);
	int length, menu;
	cin >> length;

	input_array(numbers, length);

	do
	{
		printf("�������� 2, �� �� �������� ����������� �� ������.\n�������� 3, �� �� �������� ����������� �� ������ ��� �������� ���.\n�������� 4, �� �� �������� ����������� �� ������ � �������� ���.\n�������� 5, �� �� ������ ����������.\n���� �����: ");
		cin >> menu;
		switch (menu)
		{
		case 2:
			print_array(numbers, length);
			break;
		case 3:
			sort_ascending(numbers, length, sorted_numbers);
			print_array(sorted_numbers, length);
			break;
		case 4:
			sort_descending(numbers, length, sorted_numbers);
			print_array(sorted_numbers, length);
			break;
		}
		cout << endl;
	} while (menu != 5);
}

void input_array(double numbers[], int& length)
{
	srand(time(0));
	double min = 0;
	double max = 999;
	for (int i = 0; i < length; i++)
	{
		numbers[i] = min + (((double)rand() / RAND_MAX) * (max - min));
	}
}

void print_array(double numbers[], int& length)
{
	for (int i = 0; i < length; i++) cout << numbers[i] << " ";
}

void sort_descending(double arr[], int k, double sortarr[])
{
	for (int i = 0; i < k; i++) sortarr[i] = arr[i];

	for (int i = 0; i < k - 1; i++)
	{
		bool swapped = false;
		for (int j = 0; j < k - 1 - i; j++)
		{
			if (sortarr[j] < sortarr[j + 1])
			{
				double temp = sortarr[j];
				sortarr[j] = sortarr[j + 1];
				sortarr[j + 1] = temp;
				swapped = true;
			}
		}
		if (!swapped) break;
	}
}
void sort_ascending(double arr[], int k, double sortarr[])
{
	for (int i = 0; i < k; i++) sortarr[i] = arr[i];

	for (int i = 0; i < k - 1; i++)
	{
		bool swapped = false;
		for (int j = 0; j < k - 1 - i; j++)
		{
			if (sortarr[j] > sortarr[j + 1])
			{
				double temp = sortarr[j];
				sortarr[j] = sortarr[j + 1];
				sortarr[j + 1] = temp;
				swapped = true;
			}
		}
		if (!swapped) break;
	}
}

