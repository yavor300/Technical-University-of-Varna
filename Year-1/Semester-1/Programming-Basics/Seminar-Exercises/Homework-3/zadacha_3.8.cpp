#include <iostream>
using namespace std;

/*
* ������ 3.8
* ��������� �/C++ ��������, ����� ���� ���������� �� ��������� �� ��� �� ������� � ����������� �� ���� ���� ������ �� ������� Enter.
*/
void main()
{
    cout << "Enter symbols to be repeated." << endl;
    char symbol;
    do
    {
        cin.get(symbol);
        cout.put(symbol);
    } while (symbol != '\n');
}
