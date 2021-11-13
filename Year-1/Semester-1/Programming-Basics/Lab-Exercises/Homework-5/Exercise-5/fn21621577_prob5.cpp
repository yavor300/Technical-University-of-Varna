#include <iostream>
#include <math.h>
#include <iomanip>
using namespace std;

/*
* Задача 5
*
* Съставете програма с функция, която отпечатва часа и минутите по формулата:
* h = x / 3600 % 24
* m = x / 60 % 60 Където h е часът, m минутите, а x милисекунди.
* Ако изгревът днес е бил в 1636260720 милисекунди, да се отпечата в колко часа
* ще бъде изгревът в следващите 7 дни, ако всеки ден милисекундите намаляват с 300.
*/

const int days = 7;

void printSunriseTime(int& miliseconds, int days);

void main()
{
    setlocale(LC_ALL, "BG");

    int initialMiliseconds = 1636260720;
	printSunriseTime(initialMiliseconds, days);
}

void printSunriseTime(int& miliseconds, int days)
{
	for (int i = 1; i <= days; i++)
	{
		miliseconds -= 300;

		int hours = (miliseconds / 3600) % 24;
		int minutes = (miliseconds / 60) % 60;

		printf("Ден %d %dч %dм\n", i, hours, minutes);
	}
}
