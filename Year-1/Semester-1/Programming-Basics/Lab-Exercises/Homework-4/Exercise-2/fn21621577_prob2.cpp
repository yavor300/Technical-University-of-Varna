#include <iostream>
#include <iomanip>
using namespace std;

/*
* ������ 2
*
* ��������� ��������, � ����� �� ���������� ��� ���������� �� ��� int, double � char.
* ��������  ���������  ���  �����  ��  ���.  ��������  �������  �  �������  ��  ������
* ���������� ��������  ������,  �������  ��-����.
* ��  ������������  ��  ������  �����������  �������� �������������.
*/

int main()
{
	int a = 42;
	double b = 3.14;
	char c = 'K';

	int* pa = &a;
	double* pb = &b;
	char* pc = &c;

	cout.setf(ios::left);
	cout << setw(10) << "Variable" << setw(10) << "size" << setw(10) << "Pointer" << setw(10) << "size" << endl;
	cout << setw(10) << "a" << setw(10) << sizeof(a) << setw(10) << "pa" << setw(10) << sizeof(pa) << endl;
	cout << setw(10) << "b" << setw(10) << sizeof(b) << setw(10) << "pb" << setw(10) << sizeof(pb) << endl;
	cout << setw(10) << "c" << setw(10) << sizeof(c) << setw(10) << "pc" << setw(10) << sizeof(pc) << endl;
}
