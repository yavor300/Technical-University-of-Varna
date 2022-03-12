#include <iostream>
using namespace std;

/*
* Задача 4
* 
* Динамична реализация на програма за изчисляване на числата на Фибоначи
*/

const int n = 10;

int fib(int i)
{
    static int known_fib[n];
    if (known_fib[i] != 0) return known_fib[i];

    int t = i;
    if (i < 0) return 0;
    if (i > 1) t = fib(i - 1) + fib(i - 2);

    return known_fib[i] = t;
}

int main()
{
    for (int i = 0; i < 10; i++)
    {
        cout << fib(i) << " ";
    }
}

