#include <iostream>
#define NOMINMAX
#include <Windows.h>
#include <string>
using namespace std;

/*
* Задача 10
* 
* Създайте програма с меню за нуждите на хотел.
* Предвидете структура, с помощта на която да се съхраняват данните на всяка от наличните в него 7 стаи:
* номер на стаята, капацитет (брой души, които могат да се настанят в тях), цена на нощувка, дали в нея има климатик.
* Да се предвидят функции, посредством които:
* 1.Да се въведат данните на стаите;
* 2.Да се изведе списък с наличните стаи –номер, капацитет, цена;
* 3.Да се изведат всички стаи, които отговарят на въведен от клавиатурата капацитет;
* 4.При въведен номер на стая да се извежда дали в нея има климатик;
* 5.При  въведен  номер  на  стаяда  се  предостави  възможностцената  ѝ  да  бъде коригирана след потвърждение от потребителя.
* 6.Наличните стаи да се сортират възходящо по признак цена.
*/

struct Room
{
    string id;
    int capacity;
    double price;
    bool has_air_conditioner;
};

const int ARRAY_LENGTH = 2;
const int ADD_NEW_ROOMS_CHOICE = 1;
const int PRINT_ALL_ROOMS_CHOICE = 2;
const int PRINT_ROOMS_WITH_GIVEN_CAPACITY_CHOICE = 3;
const int CHECK_ROOM_FOR_AIR_CONDITIONER_CHOICE = 4;
const int CHANGE_ROOM_PRICE_CHOICE = 5;
const int SORT_ROOMS_CHOICE = 6;
const int EXIT_FROM_MENU_CHOICE = 7;

void read_valid_integer_value(int& value);
void add_new_rooms(Room rooms[], bool& are_rooms_present);
void print_all_rooms(Room rooms[], bool& are_rooms_present);
void print_rooms_with_capacity(Room rooms[], bool& are_rooms_present, int capacity);
bool has_room_air_conditioner(Room rooms[], string id);
void change_room_price(Room rooms[], string id, double price);
void sort_rooms(Room rooms[], Room sorted_rooms[]);

void main()
{
    setlocale(LC_ALL, "BG");
    SetConsoleOutputCP(1251);
    SetConsoleCP(1251);

    Room rooms[ARRAY_LENGTH];
    Room sorted_rooms[ARRAY_LENGTH];
    bool are_rooms_present = false;

    int choice;
    do
    {
        printf("Въведете %d, за да добавите нови стаи.\nВъведете %d, за да изведете всички стаи.\nВъведете %d, за да изведете стаи с определен капацитет.\nВъведете %d, за да проверите дали стаята има климатик.\nВъведете %d, за да промените цената на стаята.\nВъведете %d, за да сортирате стаите по цена.\nВъведете %d, за да спрете програмата.\n",
            ADD_NEW_ROOMS_CHOICE, PRINT_ALL_ROOMS_CHOICE, PRINT_ROOMS_WITH_GIVEN_CAPACITY_CHOICE, CHECK_ROOM_FOR_AIR_CONDITIONER_CHOICE, CHANGE_ROOM_PRICE_CHOICE, SORT_ROOMS_CHOICE, EXIT_FROM_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", ADD_NEW_ROOMS_CHOICE, EXIT_FROM_MENU_CHOICE);
            read_valid_integer_value(choice);
        } while (choice < ADD_NEW_ROOMS_CHOICE || choice > EXIT_FROM_MENU_CHOICE);

        string id;
        switch (choice)
        {
        case ADD_NEW_ROOMS_CHOICE:
            cout << endl;
            add_new_rooms(rooms, are_rooms_present);
            break;

        case PRINT_ALL_ROOMS_CHOICE:
            cout << endl;
            print_all_rooms(rooms, are_rooms_present);
            break;

        case PRINT_ROOMS_WITH_GIVEN_CAPACITY_CHOICE:
            cout << endl << "Капацитет: ";
            int capacity;
            cin >> capacity;
            print_rooms_with_capacity(rooms, are_rooms_present, capacity);
            break;

        case CHECK_ROOM_FOR_AIR_CONDITIONER_CHOICE:
            cout << endl << "Номер на стая: ";
            cin.ignore();
            getline(cin, id);
            if (has_room_air_conditioner(rooms, id)) cout << "Има наличен климатик\n";
            else cout << "Няма наличен климатик\n";
            break;

        case CHANGE_ROOM_PRICE_CHOICE:
            cout << endl << "Номер на стая: ";
            cin.ignore();
            getline(cin, id);
            cout << "Нова цена: ";
            double price;
            cin >> price;
            change_room_price(rooms, id, price);
            break;

        case SORT_ROOMS_CHOICE:
            cout << endl;
            sort_rooms(rooms, sorted_rooms);
            print_all_rooms(sorted_rooms, are_rooms_present);
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

void add_new_rooms(Room rooms[], bool& are_rooms_present)
{
    cout << "--- ДОБАВЯНЕ НА НОВИ СТАИ ---\n";
    Room room;
    for (int i = 0; i < ARRAY_LENGTH; i++)
    {
        cout << "Въведете номер на стая: ";
        cin.ignore();
        getline(cin, room.id);
        cout << "Въведете капацитет: ";
        cin >> room.capacity;
        cout << "Въведете цена: ";
        cin >> room.price;
        cout << "Има ли наличен климатик: ";
        cin >> room.has_air_conditioner;
        rooms[i] = room;
        printf("Стая с номер %s е добавенa успешно.\n", rooms[i].id.c_str());
    }
    are_rooms_present = true;
}

void print_all_rooms(Room rooms[], bool& are_rooms_present)
{
    cout << "--- ИЗВЕЖДАНЕ НА ВСИЧКИ СТАИ ---\n";

    if (!are_rooms_present)
    {
        cout << "Няма намерени стаи.\n";
        return;
    }

    for (int i = 0; i < ARRAY_LENGTH; i++)
    {
        cout << "Номер: ";
        cout << rooms[i].id << endl;
        cout << "Капацитет: ";
        cout << rooms[i].capacity << endl;
        cout << "Цена: ";
        cout << rooms[i].price << endl;
        cout << "Климатик: ";
        cout << rooms[i].has_air_conditioner << endl;
    }
}

void sort_rooms(Room rooms[], Room sorted_rooms[])
{
    for (int i = 0; i < ARRAY_LENGTH; i++) sorted_rooms[i] = rooms[i];

    for (int i = 0; i < ARRAY_LENGTH - 1; i++)
    {
        bool swapped = false;
        for (int j = 0; j < ARRAY_LENGTH - 1 - i; j++)
        {
            double first = sorted_rooms[i].price;
            double second = sorted_rooms[i + 1].price;
            if (first > second)
            {
                Room temp = sorted_rooms[j];
                sorted_rooms[j] = sorted_rooms[j + 1];
                sorted_rooms[j + 1] = temp;
                swapped = true;
            }
        }
        if (!swapped) break;
    }
}

void print_rooms_with_capacity(Room rooms[], bool& are_rooms_present, int capacity)
{
    cout << "--- ИЗВЕЖДАНЕ НА СТАИ С ОПРЕДЕЛЕН КАПАЦИТЕТ ---\n";

    if (!are_rooms_present)
    {
        cout << "Няма намерени стаи.\n";
        return;
    }

    int count = 0;
    for (int i = 0; i < ARRAY_LENGTH; i++)
    {
        if (rooms[i].capacity >= capacity)
        {
            count++;
            cout << "Номер: ";
            cout << rooms[i].id << endl;
            cout << "Капацитет: ";
            cout << rooms[i].capacity << endl;
            cout << "Цена: ";
            cout << rooms[i].price << endl;
            cout << "Климатик: ";
            cout << rooms[i].has_air_conditioner << endl;
        }
    }

    if (count == 0) cout << "Няма намерени стаи.\n";
}

bool has_room_air_conditioner(Room rooms[], string id)
{
    cout << "--- ПРОВЕРКА ЗА НАЛИЧНОСТ НА КЛИМАТИК ---\n";

    for (int i = 0; i < ARRAY_LENGTH; i++)
    {
        if (rooms[i].id == id)
        {
            if (rooms[i].has_air_conditioner == 1)
            {
                return true;
            }
            else 
            {
                return false;
            }
        }
    }
    return false;
}

void change_room_price(Room rooms[], string id, double price)
{
    cout << "--- ПРОМЯНА НА ЦЕНА ---\n";

    for (int i = 0; i < ARRAY_LENGTH; i++)
    {
        if (rooms[i].id == id)
        {
            cout << "Сигурни ли сте, че искате да промените цената? [Y/N]: ";
            char choice;
            cin >> choice;
            if (choice == 'Y')
            {
                rooms[i].price = price;
                cout << "Цената е обновена успешно.\n";
                return;
            }
            else {
                cout << "Цената не е обновена успешно.\n";
                return;
            }
        }
    }

    cout << "Няма стая с този номер!\n";
}
