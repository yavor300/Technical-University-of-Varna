#include <iostream>
using namespace std;

void main()
{
    int broj, ed_cena, total;
    
    cout << "������ ���� �������� ��������\n";
    cin >> broj;
    
    cout << endl << "������ �������� ����";
    cin >> ed_cena;

    total = broj * ed_cena;
    
    cout << endl << "��� ��� ��� �������� " << broj << " �������� � �� ������� " << ed_cena << "����" << endl
        << "�� ��� ��� ������� " << total << " ����";
}
