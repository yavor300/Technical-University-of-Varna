#include <iostream>
#include <iomanip>
using namespace std;

/*
* ������ 2
* ��������� ��������, ����� �� ������� � �������� �������������, �������� ���� ���� �����
*/

int main()
{
    cout.setf(ios::left);
    cout << setw(10) << string(5,'%') << setw(10) << string(1,'%') << setw(10) << string(1,'%') << setw(10) << string(10,'%') << endl;
    cout << setw(10) << string(4,'%') << setw(10) << string(2,'%') << setw(10) << string(2,'%') << setw(10) << string(1,' ').append(string(8,'%')).append(string(1,' ')) << endl;
    cout << setw(10) << string(3,'%') << setw(10) << string(3,'%') << setw(10) << string(3,'%') << setw(10) << string(2,' ').append(string(6,'%')).append(string(2,' ')) << endl;
    cout << setw(10) << string(2,'%') << setw(10) << string(2,'%') << setw(10) << string(4,'%') << setw(10) << string(3,' ').append(string(4,'%')).append(string(3,' ')) << endl;
    cout << setw(10) << string(1,'%') << setw(10) << string(1,'%') << setw(10) << string(5,'%') << setw(10) << string(4,' ').append(string(2,'%')).append(string(4,' ')) << endl;
}