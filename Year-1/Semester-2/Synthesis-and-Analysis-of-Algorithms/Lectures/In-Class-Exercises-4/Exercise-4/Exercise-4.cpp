#include <iostream>
using namespace std;

struct CustomQueue {
    int value;
    CustomQueue* next;
} *first = NULL, *last = NULL;

void push(int element)
{
    CustomQueue* p = last;
    last = new CustomQueue;
    last->value = element;
    last->next = NULL;
    if (p != NULL) p->next = last;
    if (first == NULL) first = last;
}

int pop(int& element)
{
    if (first)
    {
        element = first->value;
        CustomQueue* p = first;
        first = first->next;
        delete p;
        return 1;
    }
    return 0;
}

int main()
{
    int num;
    while (cin >> num)
    {
        push(num);
    }
    while (pop(num))
    {
        cout << num << " ";
    }
}
