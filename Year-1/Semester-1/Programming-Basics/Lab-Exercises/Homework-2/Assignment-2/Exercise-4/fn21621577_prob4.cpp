#include <iostream>
using namespace std;

/*
* Задача 4
* Съставете  програма  за  намиране  на  четни  и  нечетни  числа.
* От клавиатурата се въвежда цяло число.
* В конзолата се извежда Even, ако числото е четно и Odd, ако числото е нечетно. 
*/

int main()
{
	int number;
	cin >> number;

	if (number % 2 == 0) cout << "Even";
	else cout << "Odd";
}