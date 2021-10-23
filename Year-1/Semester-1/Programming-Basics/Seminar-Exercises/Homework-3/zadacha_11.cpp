#include <iostream>
using namespace std;

/*
* Задача 11
* Съставете програма, представляваща “машинописна машинка”, с използването на функциите get() и put().
*/

void main()
{
	char symbol;

	cin.get(symbol);
	while (symbol != '\n')
	{
		cout.put(symbol);
		cin.get(symbol);
	}
}