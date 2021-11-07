#include <iostream>
using namespace std;

/*
* ������ 5
*
* ��������  ��������,  �����  ��  �����  ���������  ����������  ��  �������  ���������  � ������� �� �����.
* ���������� �� �������� ��� �� �� ������������ ��� ������ �� 5000 ��. (��������� � �������� �� ������).
* �������� ��� �������: �� ���������� �� ������� ������� salary(), ����� �� ��������� ������ � 3000 ��.
* � �� ���������� �� ����������� ������� costs(), ����� �� �������� ������ � 500 ��. (��������, �� � ������ ��� 4 �������).
* ���������� �� ������ ��������� ��������� � ���� �� ������, ������� ��������� � �������� ������� ������.
*/

const double salaryValue = 3000;
const double costsValue = 500;
const int weeks = 4;

void salary(double& money);
void costs(double& money);

void main()
{
	double initialMoney = 5000;

	for (int i = 0; i < weeks; i++)
	{
		costs(initialMoney);
	}

	salary(initialMoney);

	cout << "Money at the end of the month: " << initialMoney;
}

void salary(double& money)
{
	money += salaryValue;
}

void costs(double& money)
{
	money -= costsValue;
}
