#include <iostream>
using namespace std;

/*
* ������ 1
*
* ��������� �������� � ������� �� ��������� �� ������ �����.
* ��������� �� ����� ��������.
* ��������� ������ ���� ������ ��������� ������ �� ���������� �� ������ ��� �����.
* �� ���������� ���������� ���� ������ ��������� �� ��������� ������ �����, �������� �� ������������.
* Main ��������� �������� ���������� �� ������ ��� � ������� �������� 0, ������� ���������� �� ������������ ����� � ���� ����������� �� ���������
*/

void assignValueToAddress(double& address);

void main()
{
	setlocale(LC_ALL, "BG");

	double a = 0;
	cout << a << endl;

	assignValueToAddress(a);
	cout << a;
}

void assignValueToAddress(double& address)
{
	double number;
	cin >> number;

	address = number;
}
