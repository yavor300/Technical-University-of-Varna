#include <iostream>
using namespace std;

const int queue_size = 10;
int queue[queue_size];
int first = 0, last = 0;

void push(int element)
{
    queue[last++] = element;
}

int pop()
{
    return queue[first++];
}

int main()
{
    int num;
    while (cin >> num && last < queue_size)
    {
        push(num);
    }
    while (first < last)
    {
        cout << pop() << " ";
    }
}
