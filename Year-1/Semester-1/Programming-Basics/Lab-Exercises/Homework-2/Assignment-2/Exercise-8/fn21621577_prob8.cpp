#include <iostream>
using namespace std;

/*
* ������ 8
* �������� ��������, ����� ��������� ���� �������� �� ������������ ���� �����  �  �����������  �  �����.
* ����  ��������  ��  ��  �������  ��������� ���������:
* 
* ��������  �  �����������  �  �����,
* ��������  �  �����������,  �� �������,
* ��������  �  �����,  ��  ��  �  �����������,
* ��������  ��  �  ���� �����������, ���� �����.
*/

int main()
{
	int num;
	cin >> num;

	if (num > 0)
	{
		if (num % 2 == 0) printf("%d is positive and even", num);
		else printf("%d is positive, but odd", num);
	}
	else
	{
		if (num % 2 == 0) printf("%d is even, but not positive", num);
		else printf("%d is not positive and not even", num);
	}
}