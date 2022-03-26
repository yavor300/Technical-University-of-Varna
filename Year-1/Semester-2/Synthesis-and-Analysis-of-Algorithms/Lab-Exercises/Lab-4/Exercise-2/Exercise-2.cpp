#include <iostream>
using namespace std;

/*
* Задача 2
* 
* Аритметични операции с указатели
*/
int main()
{
    int q, * pq;
    pq = &q;
    cout << "pq=" << pq << endl;
    pq++;
    cout << "pq=" << pq << endl;

    float qf, * qpf;
    qpf = &qf;
    cout << "qpf=" << qpf << endl;
    qpf++;
    cout << "qpf=" << qpf << endl;

    double qd, * pqd;
    pqd = &qd;
    cout << "pqd=" << pqd << endl;
    pqd++;
    cout << "pqd=" << pqd << endl;
}

