#include <iostream>
using namespace std;

/*
* ������ 1
* 
* �������� � ���������
*/

void main()
{
    int q, * pq;
    pq = &q;
    cout << "pq=" << pq << endl;
    cout << "&q=" << &q << endl;

    q = 10;
    cout << "q=" << q << endl;
    cout << "*pq=" << *pq << endl;

    *pq = 20;
    cout << "q=" << q << endl;
    cout << "*pq=" << *pq << endl;
    cout << "&pq=" << &pq << endl;
}
