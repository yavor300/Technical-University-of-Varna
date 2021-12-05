#include <iostream>
#include <string>
#include <time.h>
using namespace std;

/*
* Задача 7
*
* Съставете програма – генератор на пароли.
*	1. С  помощта  на  функция,  символен  масив  от  10  елемента  да  се  запълни
*	със случайни малки букви на латиница. Изведете резултата извън функцията.
*	
*	2. С помощта на функция подредете в азбучен ред, буквите в паролата.
*	
*	Изведете резултата извън функцията. 
*/
const int ARRAY_SIZE = 10;

void generate_password(char password[]);
void sort_ascending(char password[], char sorted[]);
void print_password(char password[]);

int main()
{
	setlocale(LC_ALL, "BG");
	char password[ARRAY_SIZE];
	char sorted_password[ARRAY_SIZE];
	generate_password(password);
	print_password(password);
	cout << endl;
	sort_ascending(password, sorted_password);
	print_password(sorted_password);
}

void generate_password(char password[])
{
	srand(time(0));
	for (int i = 0; i < ARRAY_SIZE; i++)
	{
		int min = 97;
		int max = 122;
		password[i] = (min + (rand() % (max - min + 1)));
	}
}


void sort_ascending(char password[], char sorted[])
{
	for (int i = 0; i < ARRAY_SIZE; i++) sorted[i] = password[i];

	for (int i = 0; i < ARRAY_SIZE - 1; i++)
	{
		bool swapped = false;
		for (int j = 0; j < ARRAY_SIZE - 1 - i; j++)
		{
			if (sorted[j] > sorted[j + 1])
			{
				char temp = sorted[j];
				sorted[j] = sorted[j + 1];
				sorted[j + 1] = temp;
				swapped = true;
			}
		}
		if (!swapped) break;
	}
}

void print_password(char password[])
{
	for (int i = 0; i < ARRAY_SIZE; i++) cout << password[i];
}
