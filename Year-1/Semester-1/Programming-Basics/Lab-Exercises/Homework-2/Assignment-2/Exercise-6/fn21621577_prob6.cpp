#include <iostream>
using namespace std;

/*
* ������ 6
* �������� ��������, ����� �������� ��� �������� �� ������������ ����� � ������� ���-������� �� ���.
* �� ��������� �������� ������� � ��������.
*/

int main()
{
	double a, b, c;
	cin >> a >> b >> c;

	double min = a;
	if (b < min) min = b;
	if (c < min) min = c;

	printf("Min: %.2f", min);
}