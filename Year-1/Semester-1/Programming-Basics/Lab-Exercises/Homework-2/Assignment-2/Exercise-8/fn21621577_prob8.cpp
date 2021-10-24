#include <iostream>
using namespace std;

/*
* Задача 8
* Създайте програма, която проверява дали въведено от клавиатурата цяло число  е  положително  и  четно.
* Като  резултат  да  се  извежда  подходящо съобщение:
* 
* „Числото  е  положително  и  четно“,
* „Числото  е  положително,  но нечетно“,
* „Числото  е  четно,  но  не  е  положително“,
* „Числото  не  е  нито положително, нито четно“.
*/

int main()
{
	int num;
	cin >> num;

	if (num > 0)
	{
		if (num % 2 == 0) printf("%d is positive and even", num);
		else printf("%d is positive, but odd", num);
	}
	else
	{
		if (num % 2 == 0) printf("%d is even, but not positive", num);
		else printf("%d is not positive and not even", num);
	}
}