#include <iostream>
#define NOMINMAX
#include <Windows.h>
using namespace std;

/*
* Задача 1
* Съставете програма, която да съдържа каталог на продаваните от фирма Х мобилни телефони.
* Предвидете структура, чрез която да се съхраняват модела на телефона и неговата цена.
* В магазина се продават пет модела телефони, за нуждите на които създайте масив от пет елемента
* от типа на структурата. Създайте меню с функции за:1)Въвеждане на данните за всеки телефон;
* 2)Извеждане на списък на всички предлагани телефони с техните цени;
* 3)Сортиране на предлаганите телефони във възходящ ред, съобразно тяхната цена.
*/

struct Phone
{
    string model;
    double price;
};

const int ARRAY_LENGTH = 5;
const int ADD_NEW_PHONE_CHOICE = 1;
const int PRINT_ALL_PHONES_CHOICE = 2;
const int PRINT_SORTED_PHONES_BY_PRCICE_ASC_CHOICE = 3;
const int EXIT_FROM_MENU_CHOICE = 4;

void read_valid_integer_value(int& value);
void add_new_phone(Phone phones[], bool& are_phones_present);
void print_all_phones(Phone phones[], bool& are_phones_present);
void sort_array(Phone phones[], Phone sorted_phones[]);

void main()
{
    setlocale(LC_ALL, "BG");
    SetConsoleOutputCP(1251);
    SetConsoleCP(1251);

    Phone phones[ARRAY_LENGTH];
    Phone sorted_phones[ARRAY_LENGTH];
    bool are_phones_present = false;

    int choice;
    do
    {
        printf("Въведете %d, за да добавите нов телефон.\nВъведете %d, за да изведете всички модели телефони и цените им.\nВъведете %d, за да изведете телефоните сортитани по цена.\nВъведете %d, за да спрете програмата.\n",
            ADD_NEW_PHONE_CHOICE, PRINT_ALL_PHONES_CHOICE, PRINT_SORTED_PHONES_BY_PRCICE_ASC_CHOICE, EXIT_FROM_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", ADD_NEW_PHONE_CHOICE, EXIT_FROM_MENU_CHOICE);
            read_valid_integer_value(choice);
        } while (choice < ADD_NEW_PHONE_CHOICE || choice > EXIT_FROM_MENU_CHOICE);

        switch (choice)
        {
        case ADD_NEW_PHONE_CHOICE:
            cout << endl;
            add_new_phone(phones, are_phones_present);
            break;

        case PRINT_ALL_PHONES_CHOICE:
            cout << endl;
            print_all_phones(phones, are_phones_present);
            break;

        case PRINT_SORTED_PHONES_BY_PRCICE_ASC_CHOICE:
            cout << endl;
            sort_array(phones, sorted_phones);
            print_all_phones(sorted_phones, are_phones_present);
            break;
        }
        cout << endl;
    } while (choice != EXIT_FROM_MENU_CHOICE);
}

void read_valid_integer_value(int& value)
{
    while (true) {
        if (cin >> value) {
            break;
        }
        else {
            cout << "Въведете валидна числена стойност!\n";
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
        }
    }
}

void add_new_phone(Phone phones[], bool& are_phones_present)
{
    cout << "--- ДОБАВЯНЕ НА НОВИ ТЕЛЕФОНИ ---\n";
    Phone phone;
    for (int i = 0; i < ARRAY_LENGTH; i++)
    {
        cout << "Въведете модел телефон: ";
        cin >> phone.model;
        cout << "Въведете цена: ";
        cin >> phone.price;
        phones[i] = phone;
        printf("Телефон %d е добавен успешно.\n", i + 1);
    }
    are_phones_present = true;
}

void print_all_phones(Phone phones[], bool& are_phones_present)
{
    cout << "--- ИЗВЕЖДАНЕ НА ВСИЧКИ ТЕЛЕФОНИ ---\n";

    if (!are_phones_present)
    {
        cout << "Няма намерени телефони.\n";
        return;
    }

    for (int i = 0; i < ARRAY_LENGTH; i++)
    {
        cout << "Модел телефон: ";
        cout << phones[i].model << endl;
        cout << "Цена: ";
        cout << phones[i].price << endl;
    }
}

void sort_array(Phone phones[], Phone sorted_phones[])
{
    for (int i = 0; i < ARRAY_LENGTH; i++) sorted_phones[i] = phones[i];

    for (int i = 0; i < ARRAY_LENGTH - 1; i++)
    {
        bool swapped = false;
        for (int j = 0; j < ARRAY_LENGTH - 1 - i; j++)
        {
            double first = phones[i].price;
            double second = phones[i + 1].price;
            if (first > second)
            {
                Phone temp = sorted_phones[j];
                sorted_phones[j] = sorted_phones[j + 1];
                sorted_phones[j + 1] = temp;
                swapped = true;
            }
        }
        if (!swapped) break;
    }
}
