#include <iostream>
#include <string>
#include <Windows.h>
using namespace std;

/*
* Задача 5
*
* Съставете програма, която с помощта на функции проверява дали даден символен низ, обърнат наобратно,
* запазва същата подредба на символите си (включвайки  интервалите и различавайки малки и главни букви).
* Да се изведе низът в обратен ред, като при пълно съвпадение да извежда „Yes”, в противен случай – “No”. 
*/

const int SYMBOLS_COUNT = 59;

string reverseString(string input);

int main()
{
	setlocale(LC_ALL, "BG");
	SetConsoleOutputCP(1251);
	SetConsoleCP(1251);

	string input;
	getline(cin, input);

	string reversed = reverseString(input);
	cout << reversed << endl;
	
	if (input.compare(reversed) == 0) cout << "Yes";
	else cout << "No";
}

string reverseString(string input)
{
	string result = input;
	for (int i = 0; i < input.length(); i++)
	{
		result[i] = input[input.length() - i - 1];
	}
	return result;
}
