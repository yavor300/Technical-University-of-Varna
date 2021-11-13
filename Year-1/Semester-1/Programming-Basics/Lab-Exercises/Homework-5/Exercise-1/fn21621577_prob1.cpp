#include <iostream>
using namespace std;

/*
* Задача 1
*
* Съставете програма с функция, която да извежда числата, кратни на 8, в интервала от 1 до N.
* N е естествено число, което се въвежда от клавиатурата и се подава като параметър на функцията.
* Ако числото е по-малко от 8, да се изведе съобщение „Няма числа в интервала, кратни на 8“.
*/

bool isNumberDivisibleBy(int number, int divisor);
bool hasQuotientInRange(int number, int divisor);

int main()
{
    const int divisor = 8;

    int n;
    cin >> n;

    if (!hasQuotientInRange(n, divisor))
    {
        printf("There aren't any numbers divisible by %d", divisor);
        return  0;
    }

    for (int i = 1; i <= n; i++)
    {
        if (isNumberDivisibleBy(i, divisor)) printf("%d ", i);
    }

    return 0;
}

bool isNumberDivisibleBy(int number, int divisor)
{
    return number % divisor == 0;
}

bool hasQuotientInRange(int number, int divisor)
{
    return number >= divisor;
}
