#include <iostream>
using namespace std;

int main()
{
    int sumEvenPositive = 0;
    int multiplicationOddPositive = 1;

    int number;
    cin >> number;

    while (number >= 0) {
        if (number % 2 == 0) sumEvenPositive += number;
        else multiplicationOddPositive *= number;
    
        cin >> number;
    }

    cout << sumEvenPositive << endl << multiplicationOddPositive;
}

