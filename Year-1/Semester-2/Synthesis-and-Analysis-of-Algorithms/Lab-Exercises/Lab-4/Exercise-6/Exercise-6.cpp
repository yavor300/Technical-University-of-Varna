#include <iostream>
using namespace std;

/*
* Задача 6
*
* Сито на Ератостен с динамично заделяне на памет
*/

void main()
{
    int i, j, n;
    char c;

	do
	{
		cout << "Input n:";
		cin >> n;
		int* a = new int[n];
		if (a == NULL)
		{
			cout << "Insufficient memory!";
		}
		else
		{
			for (i = 2; i < n; i++)
			{
				*(a + i) = 1;
			}
			for (i = 2; i < n; i++)
			{
				if (*(a + i))
				{
					for (j = i; i * j < n; j++)
					{
						*(a + i * j) = 0;
					}
				}
			}
			for (i = 2; i < n; i++)
			{
				if (*(a + i)) cout << i << ' ';
			}
		}
		delete[]a;
		cout << "\nAnother n? (Y/N) ";
		cin >> c;
	} while (c == 'Y' || c == 'y');
}
