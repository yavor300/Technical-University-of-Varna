#include <iostream>
#include <Windows.h>
using namespace std;

const int queue_size = 15;
int queue[queue_size], first = 0, last = 0, counter = 0;

void push(int element)
{
    queue[last] = element;
    last = (last + 1) % queue_size;
}

int pop()
{
    int result = queue[first];
    first = (first + 1) % queue_size;
    return result;
}

int main()
{
    setlocale(LC_ALL, "BG");
    SetConsoleOutputCP(1251);
    SetConsoleCP(1251);

    int num, rep;
    do
    {
        cout << "1 - Добавяне на стойност\n";
        cout << "2 - Извеждане на стойност\n";
        cout << "3 - Край на работа\n";
        cout << "Изберете:";
        cin >> rep;
        switch (rep)
        {
        case 1:
            if (counter < queue_size)
            {
                cout << "Въведете число:";
                cin >> num;
                push(num);
                counter++;
            }
            else
            {
                cout << "Буферът е препълнен!\n";
            }
            break;
            
        case 2:
            if (counter)
            {
                cout << "num=" << pop() << '\n';
                counter--;
            }
            else
            {
                cout << "Буферът е празен!\n";
            }
            break;
        }
    } while (rep != 3);
}
