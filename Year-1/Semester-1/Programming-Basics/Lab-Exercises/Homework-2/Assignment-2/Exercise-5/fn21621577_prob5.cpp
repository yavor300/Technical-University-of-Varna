#include <iostream>
using namespace std;

/*
* ������ 5
* ��������� �������� �� �������� �� ��-�������� �� 2 �����.
* ��� ������� main �� ��������� 2 ���������� �� ������ ���, �������� �� �� ������������ � �� ������� ��-�������� �� �����.
* �� ��������� �������� ������� � ��������.
*/

int main()
{
	double x, y;
	cin >> x >> y;

	if (x > y) printf("%.2f is greater than %.2f", x, y);
	else if (x < y) printf("%.2f is greater than %.2f", y, x);
	else cout << "Equal numbers";
}