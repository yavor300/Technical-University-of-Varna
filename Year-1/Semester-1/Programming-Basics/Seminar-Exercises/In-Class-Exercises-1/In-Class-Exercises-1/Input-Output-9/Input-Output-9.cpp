#include <iostream>
using namespace std;

/*
* ������ 3.10
* ��������� �������� �� ��������� �� �����, � ��������� �� ���������� �� ������� (yes/no) ���� ���������� 2 ������������� ���������� �������:
* newLine() �� ���������� �� ������ ��������� � ���� �� ���� � getNumber(int &number) � �� ��������� �� ����������� ����� � ������ �� �������� ����������.
*/
void newLine();
void getInt(int &number);

void main()
{
    int n;
    getInt(n);
    cout << "The final value equals " << n << endl << "End of program";
}

void newLine()
{
    char symbol;
    do
    {
        cin.get(symbol);
    } while (symbol != '\n');
}

void getInt(int &number)
{
    char ans;
    do
    {
        cout << "Enter a number: ";
        cin >> number;
        cout << "You entered " << number << ". Is that a correct number? (yes/no): ";
        cin >> ans;
        newLine();
    } while ((ans == 'N') || (ans == 'n'));
}
