#include <iostream>
#include <math.h>
using namespace std;

/*
* Задача 3
*
* Съставете програма с функция, която намира сумата на квадратите на естествените числа от 1 до N,
* където N се въвежда от клавиатурата. Ако N е по-малко от 1, да се изведе съобщение „Невалидна редица“.
*/
const int startInterval = 1;

bool isNumberValid(int& number);
int calculateExpression(int start, int& endInterval);

int main()
{
    setlocale(LC_ALL, "BG");

    int n;
    cin >> n;
    if (!isNumberValid(n))
    {
        cout << "Невалидна редица";
        return 0;
    }

    cout << calculateExpression(startInterval, n);

    return 0;
}

int calculateExpression(int start, int& endInterval)
{
    int result = 0;

    for (int i = start; i <= endInterval; i++)
    {
        result += pow(i, 2);
    }

    return result;
}

bool isNumberValid(int& number)
{
    return number >= 1;
}
