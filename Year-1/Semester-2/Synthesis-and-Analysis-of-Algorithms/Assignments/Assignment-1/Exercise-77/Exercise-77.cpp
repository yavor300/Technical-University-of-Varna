#include <iostream>
#include <time.h>
#include <Windows.h>
using namespace std;

/*
* Задача 77
* 
* Да се състави програма за извличане от дадена опашка от цели числа на най-малкия елемент.
*/

struct Element {
    int value;
    Element* next;
};

const int NUMBERS_COUNT = 10;
const int MIN_NUMBER = -20;
const int MAX_NUMBER = 20;

void push(Element*& first, Element*& last, int value_to_add);
bool pop(Element*& first, int& value_to_return);
int get_min_number(Element*& first);
void fill_in_queue(Element*& first, Element*& last, int count_of_elements);
void print_elements(Element* first);

int main()
{
    setlocale(LC_ALL, "BG");
    SetConsoleOutputCP(1251);
    SetConsoleCP(1251);

    Element* first_element = NULL;
    Element* last_element = NULL;

    fill_in_queue(first_element, last_element, NUMBERS_COUNT);
    printf("Елементи в опашката, преди извличането на най-малкия елемент: \n\t");
    print_elements(first_element);

    try
    {
        printf("\n\nНай-малък елемент в опашката: %d\n\n", get_min_number(first_element));
    }
    catch (const exception& error)
    {
        printf(error.what());
    }

    printf("Елементи в опашката, след извличането на най-малкия елемент: \n\t");
    print_elements(first_element);
}

void push(Element*& first, Element*& last, int value_to_add)
{
    Element* new_element = new Element;
    new_element->value = value_to_add;
    new_element->next = NULL;

    if (last) {
        last->next = new_element;
        last = last->next;
    }
    else first = last = new_element;
}

bool pop(Element*& first, int& value_to_return)
{
    if (!first) return false;

    else {
        value_to_return = first->value;

        Element* element_to_delete = first;
        first = first->next;
        delete element_to_delete;
        return true;
    }
}

int get_min_number(Element*& first)
{
    if (!first) throw invalid_argument("Грешка: Опашката е празна!");

    Element* first_element_copy = NULL;
    Element* last_element_copy = NULL;

    int min_number = INT_MAX, value;
    while (first && pop(first, value))
    {
        if (value < min_number) min_number = value;
        push(first_element_copy, last_element_copy, value);
    }

    first = first_element_copy;
    return min_number;
}

void fill_in_queue(Element*& first, Element*& last, int count_of_elements)
{
    srand(time(NULL));
    int min = MIN_NUMBER;
    int max = MAX_NUMBER;
    for (int i = 0; i < count_of_elements; i++)
    {
        int random_number = (min + (rand() % (max - min + 1)));
        push(first, last, random_number);
    }
}

void print_elements(Element* first)
{
    while (first)
    {
        printf("%d ", first->value);
        first = first->next;
    }
}
