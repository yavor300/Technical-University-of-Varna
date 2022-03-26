#include <iostream>
using namespace std;

/*
* Задача 5
*
* Сито на Ератостен –намиране на прости числа 
*/

#define K 1000

void main()
{
    int i, j, n, a[K];
    cout << "n=";
    cin >> n;
    for (i = 2; i < n; i++)
    {
        a[i] = 1;
    }
    for (i = 2; i < n; i++)
    {
        if (a[i])
        {
            for (j=i; i * j < n; j++)
            {
                a[i * j] = 0;
            }
        }
    }
    for (i = 2; i < n; i++)
    {
        if (a[i]) cout << i << ' ';
    }
}
