#include <iostream>
#include <string>
using namespace std;

/*
* Задача 1
*
* Напишете програма, която на всяко цяло положително число съпоставя записа му в римската система.
* Римските числа са съставени от цифрите I-1, V-5, X-10, L-50, C-100, D-500, M-1000.
* Хилядите, стотици, десетици, и единици се представят с отделни символи. I-1, II-2, III-3, IV-4, V-5, VI-6, VII-7, VIII-8, IX-9.
* Записват се числа до 3999. Например 1978 - MCMLXXVIII.
* 
*/

int decimalNumbers[] = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };
string romanSymbols[] = { "I", "IV", "V","IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M" };
const int lastDecimalNumbersIndex = *(&decimalNumbers + 1) - decimalNumbers - 1;

string convertDecimalToRoman(int decimalNumber);
bool isValidDecimalInputNumber(int inputNumber);

int main()
{
    int inputNumber;
    cin >> inputNumber;

    if (!isValidDecimalInputNumber(inputNumber))
    {
        cout << "Invalid number! Number must be between 1 and 3999!";
        return 0;
    }

    string romanNumber = convertDecimalToRoman(inputNumber);
    cout << romanNumber;
}

string convertDecimalToRoman(int decimalNumber)
{
    string result;

    for (int i = lastDecimalNumbersIndex; i >= 0; i--)
    {
        if (decimalNumber <= 0) break;

        if (decimalNumbers[i] <= decimalNumber)
        {
            result += romanSymbols[i];
            decimalNumber -= decimalNumbers[i];
            i++;
        }
    }

    return result;
}

bool isValidDecimalInputNumber(int inputNumber)
{
    return inputNumber > 0 && inputNumber <= 3999;
}
