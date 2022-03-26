#include <iostream>
using namespace std;

/*
* Задача 3
*
* Инкрементиране и декрементиране към обекта,
* сочен от указателя
*/
int main()
{
    int q, * pq;
    pq = &q;
    *pq = 20;
    (*pq)++;
    cout << "q=" << q << endl;
    cout << "*pq=" << *pq << endl;
    *pq += 1;
    cout << "q=" << q << endl;
    cout << "*pq=" << *pq << endl;
    ++* pq;
    cout << "q=" << q << endl;
    cout << "*pq=" << *pq << endl;
}
