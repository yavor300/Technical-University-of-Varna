#include <iostream>
using namespace std;

/*
* ������ 11
* ��������� ��������, �������������� ������������ ��������, � ������������ �� ��������� get() � put().
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