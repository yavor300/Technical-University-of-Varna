#include <iostream>
#include <conio.h>
using namespace std;

void main()
{
	char ch;
	//ch = _getche(); input with "echo"
	ch = _getch(); // input without "echo"
	_putch(ch); // see the char after typing it
}
