#include <iostream>
#include <Windows.h>
using namespace std;

struct CustomDeque {
    int value;
    CustomDeque* next;
} *l = NULL, *r = NULL;

void push_left(int element)
{
    CustomDeque* p = l;
    l = new CustomDeque;
    l->value = element;
    l->next = p;
    if (r == NULL)
    {
        r = l;
    }
}

void push_right(int element)
{
    CustomDeque* p = r;
    r = new CustomDeque;
    r->value = element;
    r->next = NULL;
    if (l == NULL)
    {
        l = r;
    }
    else
    {
        p->next = r;
    }
}

int pop_left(int& n)
{
    CustomDeque* p;
    if (l)
    {
        n = l->value;
        p = l;
        l = l->next;
        if (l == NULL) r = NULL;
        delete p;
        return 1;
    }
    return 0;
}

int pop_right(int& n)
{
    CustomDeque* p;
    if (r)
    {
        n = r->value;
        if (l == r)
        {
            delete r;
            l = r = NULL;
        }
        else
        {
            p = l;
            while (p->next != r)
            {
                p++;
            }
            p->next = NULL;
            delete r;
            r = p;
        }
        return 1;
    }
    return 0;
}


int main()
{
    setlocale(LC_ALL, "BG");
    SetConsoleOutputCP(1251);
    SetConsoleCP(1251);

    int ch;
    do
    {
        int  num;
        cout << "Меню:\n";
        cout << "1 - Добавяне отляво\n";
        cout << "2 - Добавяне отдясно\n";
        cout << "3 - Извличане отляво\n";
        cout << "4 - Извличане отдясно\n";
        cout << "5 - Край на работата\n";
        cout << "Избор: ";
        cin >> ch;

        switch (ch)
        {
        case 1:
        case 2:
            cout << "Въведете число: ";
            cin >> num;
            if (ch == 1) push_left(num);
            else push_right(num);
            break;

        case 3:
            if (pop_left(num)) cout << num << "\n";
            else cout << "Празна структура!\n";
            break;
         
        case 4:
            if (pop_right(num)) cout << num << "\n";
            else cout << "Празна структура!\n";
            break;

        }
    } while (ch != 5);
}
