#include <iostream>
using namespace std;

/*
������ 3
��������� ��������, � ����� �� �� �������� ��� ������ ����� �� �������� �� ������������
� �� �� �������� ���������� � ������ ��.
*/

int main()
{
	// ����������� �� ���������� �� �������� �� �������������
	double width, length;
	cin >> width >> length;

	// ���������� �� ���������� �� ������������� � ��������� � ����������
	double perimeter = 2 * (width + length);

	// ���������� �� ������ �� ������������� � ��������� � ����������
	double area = width * length;

	// ����������� ���������� � ������ �� �������������
	cout << "Perimeter: " << perimeter << endl << "Area: " << area;
}