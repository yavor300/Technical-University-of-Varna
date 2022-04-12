#include <iostream>
#include <Windows.h>
using namespace std;

/*
* Задача 2
* 
* Моделиране на играта "Броеница" с единично-свързан
* кръгов списък
*/

struct Node
{
    int value;
    Node* next;
} *start = NULL;

void add(int value)
{
    Node* p = start;
    start = new Node;
    start->value = value;
    start->next = p;
}

int main()
{
    setlocale(LC_ALL, "BG");
    SetConsoleOutputCP(1251);
    SetConsoleCP(1251);

    int i, n, m;
    cout << "Въведете броя на децата n: ";
    cin >> n;
    
    for (int i = n; i > 0; i--) add(i);
    
    Node* p = start;
    while (p->next != NULL)
    {
        p = p->next;
    }
    p->next = start;

    cout << "\nВъведете m: ";
    cin >> m;

    p = start;
    Node* q = NULL;
    do
    {
        for (int i = 1; i < m; i++)
        {
            q = p;
            p = p->next;
        }
        q->next = p->next;
        cout << "\nНомер за изтриване " << p->value;
        delete(p);
        p = q->next;
    } while (p != p->next);
    cout << "\nОстава дете с номер " << p->value;
}
