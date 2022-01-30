#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <stdio.h>
#include <conio.h>
#include <ctype.h>
using namespace std;

int main()
{
	/*char symbol_without_echo;
	symbol_without_echo = _getch();
	_putch(symbol_without_echo);*/

	/*char symbol_with_echo;
	symbol_with_echo = _getche();
	cout << symbol_with_echo;*/

	char symbol_with_getchar;
	symbol_with_getchar = getchar();
	putchar(symbol_with_getchar);
	putchar('\n');
	putchar(toupper(symbol_with_getchar));
	putchar('\n');
	putchar(tolower(symbol_with_getchar));
	putchar('\n');
	cout << (isupper(symbol_with_getchar) == 0 ? "false" : "true") << endl;
	cout << (isalpha(symbol_with_getchar) == 0 ? "false" : "true") << endl;
	cout << (isdigit(symbol_with_getchar) == 0 ? "false" : "true") << endl;
	cout << (isspace(symbol_with_getchar) == 0 ? "false" : "true") << endl;
}
