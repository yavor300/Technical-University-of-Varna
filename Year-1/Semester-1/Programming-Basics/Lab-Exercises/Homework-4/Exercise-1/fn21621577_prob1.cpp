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

double processNumber(string number);
double reduceNumberTwoTimes(double number);
double increaseNumberTwoTimes(double number);
double increaseNumberValueByThree(double number);
double reduceNumberValueByThree(double number);
void changeValueWithThousand(double number);
bool isEven(string number);

void main()
{
    string number;
    cin >> number;
    cout << processNumber(number);
}

double processNumber(string number)
{
    double value = stod(number);
    
    if (value > 0)
    {
        if (isEven(number)) return reduceNumberTwoTimes(value);
        else return increaseNumberTwoTimes(value);
    }
}

double reduceNumberTwoTimes(double number)
{
    return number / 2;
}

double increaseNumberTwoTimes(double number)
{
    return number * 2;
}

bool isEven(string number)
{
    bool isFloatingPointReached = false;

    for (int i = number.length() - 1; i >= 0; i--)
    {
        if (number[i] == '0' && !isFloatingPointReached)
        {
            continue;
        }
        
        if (number[i] == '.')
        {
            isFloatingPointReached = true;
            continue;
        }

        if ((number[i] - '0') % 2 == 0)
        {
            return true;
        }
        
        return false;
    }
}
