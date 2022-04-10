#include <iostream>
using namespace std;

/*
* Имплементация на двусвързан списък
*/

struct Node
{
    int value;
    Node* previous;
    Node* next;
} *start = NULL;

bool is_list_empty()
{
    return start == NULL;
}

/*
* Включване на елемент в началото на двусвързан списък
*/
void add_beginning(int value)
{
    if (is_list_empty())
    {
        start = new Node;
        start->previous = NULL;
        start->value = value;
        start->next = NULL;
    }
    else
    {
        Node* new_node = new Node;
        new_node->value = value;
        new_node->previous = NULL;
        new_node->next = start;

        start->previous = new_node;
        start = new_node;
    }
}

/*
* Включване на междинен елемент в двусвързан списък преди
* друг елемент с посочена ключова стойност
*/
void add_before(int value, int add_before_value)
{
    Node* new_node = new Node;
    Node* add_before_node = start;

    while (add_before_node->value != add_before_value) add_before_node = add_before_node->next;

    new_node->value = value;
    new_node->next = add_before_node;
    new_node->previous = add_before_node->previous;

    add_before_node->previous->next = new_node;
    add_before_node->previous = new_node;
}

/*
* Включване на междинен елемент в двусвързан списък след
* друг елемент с посочена ключова стойност
*/
void add_after(int value, int add_after_value)
{
    Node* new_node = new Node;
    Node* add_after_node = start;

    while (add_after_node->value != add_after_value) add_after_node = add_after_node->next;

    new_node->value = value;
    new_node->previous = add_after_node;
    new_node->next = add_after_node->next;

    add_after_node->next = new_node;
    
    if (new_node->next != NULL) new_node->next->previous = new_node;
}


/*
* Включване на последен елемент в двусвързан списък
*/
void add_last(int value)
{
    if (is_list_empty())
    {
        start = new Node;
        start->previous = NULL;
        start->value = value;
        start->next = NULL;
    }
    else 
    {
        Node* last_node = start;
        while (last_node->next) last_node = last_node->next;

        Node* new_node = new Node;
        new_node->previous = last_node;
        new_node->value = value;
        new_node->next = NULL;

        last_node->next = new_node;
    }
}

/*
* Изтриване на пъривя елемент в двусвързан списък
*/
void delete_first(int& value)
{
    value = start->value;

    if (start->next == NULL && start->previous == NULL) {
        delete start;
    } 
    else
    {
        Node* node_to_delete = start;

        start = start->next;
        start->previous = NULL;

        delete node_to_delete;
    }
}

/*
* Изтриване на междинен елемент в двусвързан списък
*/
void delete_any(int value)
{
    Node* node_to_delete = start;
    while (node_to_delete->value != value) node_to_delete = node_to_delete->next;

    node_to_delete->previous->next = node_to_delete->next;
    node_to_delete->next->previous = node_to_delete->previous;

    delete node_to_delete;
}

/*
* Изтриване на последен елемент в двусвързан списък
*/
void delete_last(int& value)
{
    Node* node_to_delete = start;
    while (node_to_delete->next) node_to_delete = node_to_delete->next;

    value = node_to_delete->value;

    node_to_delete->previous->next = NULL;

    delete node_to_delete;
}


int main()
{
    add_beginning(42);
    add_last(10);
    add_before(5, 10);
    add_after(15, 42);
    int deleted_first, deleted_last;
    delete_first(deleted_first);
    delete_any(5);
    delete_last(deleted_last);
    cout << "Deleted first: " << deleted_first << "\n";
    cout << "Deleted last: " << deleted_last << "\n";

    while (start->next)
    {
        cout << start->value << " ";
        start = start->next;
    }
    cout << start->value;
}
