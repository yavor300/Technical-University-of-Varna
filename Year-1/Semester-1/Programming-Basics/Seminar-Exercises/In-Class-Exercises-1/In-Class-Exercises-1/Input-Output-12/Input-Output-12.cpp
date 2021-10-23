#include <iostream>
using namespace std;

/*
* Задача 3.12
* Съставете С/C++ програма, която за всеки въведен символ, извежда точка.
*/

void main()
{
	char symbol;
	printf("Enter a sequence of symbols: ");
	do
	{
		symbol = getchar();
		putchar('.');
	} while (symbol != '\n');

	/* 
	* При въведени символи ('a', 'b', 'c', '\n'), броят изведени точки ще бъде равен на 4. 
	*/
}