#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
using namespace std;

int main()
{
	cout << "Enter line of input and I will echo it:\n";
	char symbol;
	do
	{
		cin.get(symbol);
		cout.put(symbol);
	} while (symbol != '\n');
}
