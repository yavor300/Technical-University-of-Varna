#include <iostream>
using namespace std;

/*
* ������ 8
* ����� � ������������ �� cin.getline() ���� cin >> ��� ��������� �� ������?
*/

void main()
{
	char fullNameWithGetLine[25];
	cin.getline(fullNameWithGetLine, 25); // Yavor Yordanov Chamov
	cout << fullNameWithGetLine << endl; // Yavor

	char fullNameWithCin[25];
	cin >> fullNameWithCin; // Yavor Yordanov Chamov
	cout << fullNameWithCin; // Yavor
}