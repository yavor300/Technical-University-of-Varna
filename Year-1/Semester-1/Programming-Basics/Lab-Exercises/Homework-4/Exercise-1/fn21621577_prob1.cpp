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

double processNumber(string number);
double reduceNumberNTimes(double number, double timesToReduce);
double increaseNumberNTimes(double number, double timesToIncrease);
double increaseNumberValue(double number, double valueToIncreaseWith);
double reduceNumberValue(double number, double valueToReduceWith);
bool isEven(string number);

void main()
{
    cout << "Enter number to change its value: ";
    string number;
    cin >> number;

    double newNumber = processNumber(number);
    cout << "The number changed its value to: " << newNumber;
}

double processNumber(string number)
{
    double value = stod(number);
    
    if (value > 0)
    {
        if (isEven(number)) return reduceNumberNTimes(value, timesToReduce);
        else return increaseNumberNTimes(value, timesToIncrease);
    }
    else if (value < 0)
    {
        if (isEven(number)) return increaseNumberValue(value, valueToIncreaseWith);
        else return reduceNumberValue(value, valueToReduceWith);
    }
    
    return changeValue;
}

double reduceNumberNTimes(double number, double timesToReduce)
{
    return number / timesToReduce;
}

double increaseNumberNTimes(double number, double timesToIncrease)
{
    return number * timesToIncrease;
}

double increaseNumberValue(double number, double valueToIncreaseWith)
{
    return number + valueToIncreaseWith;
}

double reduceNumberValue(double number, double valueToReduceWith)
{
    return number - valueToReduceWith;
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
