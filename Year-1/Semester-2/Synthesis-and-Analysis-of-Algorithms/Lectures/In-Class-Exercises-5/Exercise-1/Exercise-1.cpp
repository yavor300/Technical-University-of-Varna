#include <iostream>
using namespace std;

/*
* Реализация на структурата списък
*/

struct CustomList {
    int value;
    CustomList* next;
} *start;

void init()
{
    start = NULL;
}

void add_first(int value_to_add)
{
    CustomList* p = start;
    start = new CustomList;
    start->value = value_to_add;
    start->next = p;
}

void add_middle_before(int value_to_add, int insert_before_value)
{
    CustomList* q;
    CustomList* p = start;

    while (p->value != insert_before_value)
    {
        p = p->next;
    }
    q = new CustomList;
    q->next = p->next;
    q->value = p->value;
    p->next = q;
    p->value = value_to_add;
}

void add_middle_after(int value_to_add, int insert_after_value)
{
    CustomList* q;
    CustomList* p = start;

    while (p->value != insert_after_value)
    {
        p = p->next;
    }
    q = new CustomList;
    q->value = value_to_add;
    q->next = p->next;
    p->next = q;
}

void add_last(int value_to_add)
{
    CustomList* p = start, * q;
    q = new CustomList;
    q->value = value_to_add;
    q->next = NULL;
    while (p->next)
    {
        p = p->next;
    }
    p->next = q;
}

int remove_first(int& value_to_return)
{
    CustomList* p = start;
    if (start)
    {
        value_to_return = start->value;
        start = start->next;
        delete p;
        return 1;
    }
    return 0;
}

void remove_middle_before(int& value_to_return, int remove_before_value)
{
    CustomList* q = NULL;
    CustomList* p = start;

    while (p->value != remove_before_value)
    {
        q = p;
        p = p->next;
    }
    value_to_return = q->value;
    q->next = p->next;
    delete p;
}

void remove_middle_after(int& value_to_return, int remove_after_value)
{
    CustomList* q;
    CustomList* p = start;

    while (p->value != remove_after_value)
    {
        p = p->next;
    }
    q = p->next;
    value_to_return = q->value;
    p->next = q->next;
    delete q;
}

void remove_last(int& value_to_return)
{
    CustomList* p = start, * q = NULL;
    while (p->next)
    {
        q = p;
        p = p->next;
    }
    value_to_return = p->value;
    q->next = NULL;
    delete p;
}

int search_iter(int search_value)
{
    CustomList* p = start;
    if (start)
    {
        while ((p->value != search_value) && (p->next))
        {
            p = p->next;
        }
        if (p->value == search_value)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    return 0;
}

int search_recursive(struct CustomList* p, int n)
{
    CustomList* q = NULL;
    if (start == NULL)
    {
        start = new CustomList;
        start->value = n;
        start->next = NULL;
        return 0;
    }
    else
    {
        if ((p->value < n) && (p->next == NULL))
        {
            q = new CustomList;
            q->value = n;
            q->next = NULL;
            p->next = q;
            return 0;
        }
        else
        {
            if (p->value == n) return 1;
            else
            {
                if (p->value < n)
                {
                    search_recursive(p->next, n);
                }
                else
                {
                    q = new CustomList;
                    q->next = p->next;
                    q->value = p->value;
                    p->value = n;
                    p->next = q;
                    return 0;
                }
            }
        }
    }
}

int main()
{
    add_first(1);
    add_last(4);
    add_last(5);
    add_middle_after(2, 1);
    add_middle_before(3, 4);

    int n;
    while (start->next)
    {
        remove_last(n);
        cout << n << " ";
    }

    remove_first(n);
    cout << n << endl;

    add_first(1);
    add_last(4);
    add_last(5);
    add_middle_after(2, 1);
    add_middle_before(3, 4);

    remove_middle_after(n, 3);
    cout << n << " ";

    remove_middle_before(n, 3);
    cout << n << endl;

    cout << (search_recursive(start, 5) ? "true" : "false");
}
