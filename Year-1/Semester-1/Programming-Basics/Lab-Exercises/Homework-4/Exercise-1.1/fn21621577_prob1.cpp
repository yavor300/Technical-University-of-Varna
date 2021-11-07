#include <iostream>
#include <string>
using namespace std;

/*
* Задача 1
*
* Съставете програма, която да приема от клавиатурата реално число:
*   ако е по-голямо от 0 и четно, намалява стойността му 2 пъти;
*   ако е по-голямо от 0 и нечетно, увеличава стойността му 2 пъти;
*   ако е по-малко от 0 и четно, увеличава стойността му с 3;
*   ако е по-малко от 0 и нечетно, намалява стоинността му с 3,5;
*   ако е нула, заменете стойността му с 1000.
* Реализирайте всеки от посочените сценарии с функции
*/

const double timesToReduce = 2;
const double timesToIncrease = 2;
const double valueToIncreaseWith = 3;
const double valueToReduceWith = 3.5;
const double changeValue = 1000;

void processNumber(double &number);
void reduceNumberNTimes(double &number, double timesToReduce);
void increaseNumberNTimes(double &number, double timesToIncrease);
void increaseNumberValue(double &number, double valueToIncreaseWith);
void reduceNumberValue(double &number, double valueToReduceWith);
bool isEven(string number);

void main()
{
    cout << "Enter number to change its value: ";

    double number;
    cin >> number;

    processNumber(number);
    cout << "The number changed its value to: " << number;
}

void processNumber(double &number)
{
    if (number > 0)
    {
        if (isEven(to_string(number))) reduceNumberNTimes(number, timesToReduce);
        else increaseNumberNTimes(number, timesToIncrease);
    }
    else if (number < 0)
    {
        if (isEven(to_string(number))) increaseNumberValue(number, valueToIncreaseWith);
        else reduceNumberValue(number, valueToReduceWith);
    }
    else
    {
        number = changeValue;
    }
}

void reduceNumberNTimes(double &number, double timesToReduce)
{
    number /= timesToReduce;
}

void increaseNumberNTimes(double &number, double timesToIncrease)
{
    number *= timesToIncrease;
}

void increaseNumberValue(double &number, double valueToIncreaseWith)
{
    number += valueToIncreaseWith;
}

void reduceNumberValue(double &number, double valueToReduceWith)
{
    number -= valueToReduceWith;
}

bool isEven(string number)
{
    bool isFloatingPointReached = false;

    for (int i = number.length() - 1; i >= 0; i--)
    {
        if (number[i] == '0' && !isFloatingPointReached) continue;

        if (number[i] == '.')
        {
            isFloatingPointReached = true;
            continue;
        }

        if ((number[i] - '0') % 2 == 0) return true;

        return false;
    }
}
