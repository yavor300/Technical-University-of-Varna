#include <iostream>
using namespace std;

/*
* ������ 4
* ���������  ��������  ��  ��������  ��  �����  �  �������  �����.
* �� ������������ �� ������� ���� �����.
* � ��������� �� ������� Even, ��� ������� � ����� � Odd, ��� ������� � �������. 
*/

int main()
{
	int number;
	cin >> number;

	if (number % 2 == 0) cout << "Even";
	else cout << "Odd";
}