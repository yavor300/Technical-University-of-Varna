#include <iostream>
#include <conio.h>
#include <fstream>
using namespace std;

/*
* ������ 3.11
* ��������� �/C++ �������� ��: ��������� �� 10 ������� ��� ����, ����������� �� � ������ bukwi,
* ��������� �� ������ �� ������ � ��������� �� ��������� �� �������
* (��� ��������� �� ����� �� ������� �� ������� ��������� ���� fstream.h).
* �� ����������� ��������� �� ������ ����������� �������� �� ����� for 
*/

void main()
{
    char bukwi[10];
	cout << "Enter 10 letters :" << endl;

	// cin.get(bukwi, 11); // buffered input (Enter must be clicked)

	ofstream prn("PRN");
	for (int counter = 0; counter < sizeof(bukwi); counter++)
	{
		bukwi[counter] = _getch();
		_putch(bukwi[counter]);
		prn.put(bukwi[counter]);
	}
}