#include <iostream>
#include <math.h>
#include <iomanip>
using namespace std;

/*
* Задача 4
*
* Съставете програма с функция, която намира сумата на редицата: (x ^ n + 1) / (2 * n + 1).
* Където x се въвеждат от клавиатурата, а n се изменя от 1 до 20.
* Резултатът се извежда в конзолата с 5 знака след десетичната запетая.
*/

const int startInterval = 1;
const int endInterval = 20;

double calculateExpression(int startInterval, int endInterval, double& number);

void main()
{
    double x;
    cin >> x;

    cout.setf(ios::fixed);
    cout.precision(5);
    cout << calculateExpression(startInterval, endInterval, x);
}

double calculateExpression(int startInterval, int endInterval, double& number)
{
    double result = 0;
    for (int i = startInterval; i <= endInterval; i++)
    {
        result += ((pow(number, i) + 1) / (2.0 * i + 1));
    }
    return result;
}
