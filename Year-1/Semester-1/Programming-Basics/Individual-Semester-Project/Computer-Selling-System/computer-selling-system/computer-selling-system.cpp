#include <iostream>
#include <string>
#define NOMINMAX
#include <Windows.h>
using namespace std;

/*
* Required structures of the program
*/
struct Processor
{
    string manufacturer;
    string model;
    double frequency;
    int cores;
};

struct Computer
{
    string id;
    string brand;
    string model;
    Processor processor;
    double ram;
    double price;
    string status;
};

/*
* Constants
*/
const int MAX_NUMBER_OF_CONFIGURATIONS = 100;
const int INITIAL_CONFIGURATIONS_COUNT = 0;
const int ADD_NEW_CONFIGURATION_CHOICE = 1;
const int PRINT_ALL_CONFIGURATIONS_CHOICE = 2;
const int PRINT_SORTED_CONFIGURATIONS_BY_PROCESSOR_FREQUENCY_DESC_CHOICE = 3;
const int PRINT_CONFIGURATIONS_BY_BRAND_CHOICE = 4;
const int EDIT_CONFIGURATION_CHOICE = 5;
const int EXIT_FROM_MENU_CHOICE = 6;

int main();

/*
* Prototypes
*/
void process_add_configuration_request(int& present_configurations_count, Computer configurations[]);
bool is_configurations_count_valid(int& configurations_count, int& present_configurations_count);
void read_precessor_data(string& manufacturer, string& model, double& frequency, int& cores);
void read_computer_data(string& id, string& brand, string& model, double& ram, double& price, string& status);
void print_configurations(Computer configurations[], int& present_configurations_count, int& configurations_count_to_print);
void sort_configurations_by_processor_frequency_desc(Computer configurations[], int& present_configurations_count, Computer sorted_configurations[]);
void print_configurations_by_brand(Computer configurations[], int& present_configurations_count, string brand);
void read_valid_integer_value(int& value);
void read_valid_double_value(double& value);
void process_print_configurations_by_brand_request(Computer configurations[], int& present_configurations_count);
bool configuration_exists_by_id(string id, Computer configurations[], int& present_configurations_count);
void update_configuration(Computer configurations[], int& present_configurations_count);
void update_configuration_id(string old_id, Computer configurations[], int& present_configurations_count);

int main()
{
    setlocale(LC_ALL, "BG");
    SetConsoleOutputCP(1251);
    SetConsoleCP(1251);

    int choice, present_configurations_count(INITIAL_CONFIGURATIONS_COUNT);
    Computer configurations[MAX_NUMBER_OF_CONFIGURATIONS];
    Computer sorted_configurations[MAX_NUMBER_OF_CONFIGURATIONS];

    do
    {
        printf("Въведете %d, за да добавите нова конфигурация.\nВъведете %d, за да изведете всички конфигурации.\nВъведете %d, за да изведете желан брой конфигурации с най-голяма тактова честота на процесора.\nВъведете %d, за да изведете конфигурации от избрана марка.\nВъведете %d, за да редактирате конфигурация.\nВъведете %d, за да спрете програмата.\n",
            ADD_NEW_CONFIGURATION_CHOICE, PRINT_ALL_CONFIGURATIONS_CHOICE, PRINT_SORTED_CONFIGURATIONS_BY_PROCESSOR_FREQUENCY_DESC_CHOICE, PRINT_CONFIGURATIONS_BY_BRAND_CHOICE, EDIT_CONFIGURATION_CHOICE, EXIT_FROM_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", ADD_NEW_CONFIGURATION_CHOICE, EXIT_FROM_MENU_CHOICE);
            read_valid_integer_value(choice);
        } while (choice < ADD_NEW_CONFIGURATION_CHOICE || choice > EXIT_FROM_MENU_CHOICE);

        switch (choice)
        {
        case ADD_NEW_CONFIGURATION_CHOICE:
            cout << endl;
            process_add_configuration_request(present_configurations_count, configurations);
            break;

        case PRINT_ALL_CONFIGURATIONS_CHOICE:
            cout << endl;
            print_configurations(configurations, present_configurations_count, present_configurations_count);
            break;

        case PRINT_CONFIGURATIONS_BY_BRAND_CHOICE:
            cout << endl;
            cin.ignore();
            process_print_configurations_by_brand_request(configurations, present_configurations_count);
            break;

        case PRINT_SORTED_CONFIGURATIONS_BY_PROCESSOR_FREQUENCY_DESC_CHOICE:
            cout << endl;

            int configurations_to_print;

            sort_configurations_by_processor_frequency_desc(configurations, present_configurations_count, sorted_configurations);
            cout << "Въведете брой конфигурации за извеждане: ";
            read_valid_integer_value(configurations_to_print);
            print_configurations(sorted_configurations, present_configurations_count, configurations_to_print);

            break;

        case EDIT_CONFIGURATION_CHOICE:
            cout << endl;
            update_configuration(configurations, present_configurations_count);
            cout << endl;
            break;
        }
        cout << endl;
    } while (choice != EXIT_FROM_MENU_CHOICE);
}

void process_add_configuration_request(int& present_configurations_count, Computer configurations[])
{
    printf("Въведете брой конфигурации: ");
    int configurations_count;
    read_valid_integer_value(configurations_count);
    cin.ignore();
    if (is_configurations_count_valid(configurations_count, present_configurations_count))
    {
        string processor_manufacturer, processor_model, computer_id, computer_brand, computer_model, computer_availability_status;
        double processor_frequency, computer_ram, computer_price;
        int processor_cores;

        for (int i = 0; i < configurations_count; i++)
        {
            cout << endl << "--- ИНФОРМАЦИЯ ЗА ПРОЦЕСОР ---" << endl;
            read_precessor_data(processor_manufacturer, processor_model, processor_frequency, processor_cores);
            Processor processor = { processor_manufacturer, processor_model, processor_frequency, processor_cores };

            cout << endl << "--- ИНФОРМАЦИЯ ЗА КОМПЮТЪР ---" << endl;
            cin.ignore();
            read_computer_data(computer_id, computer_brand, computer_model, computer_ram, computer_price, computer_availability_status);
            if (configuration_exists_by_id(computer_id, configurations, present_configurations_count))
            {
                cout << "\nВече съществува конфигурация с този сериен номер!\nКОНФИГУРАЦИЯТА НЕ Е ДОБАВЕНА УСПЕШНО!\n";
                return;
            }
            Computer computer = { computer_id, computer_brand, computer_model, processor, computer_ram, computer_price, computer_availability_status };

            configurations[present_configurations_count++] = computer;

            cout << endl << "КОНФИГУРАЦИЯТА Е ДОБАВЕНА УСПЕШНО!" << endl;
        }
    }
    else {
        cout << "Въведеният брой конфигурации не е валиден.";
        int remaining_slots = MAX_NUMBER_OF_CONFIGURATIONS - present_configurations_count;
        if (remaining_slots < configurations_count) cout << " Няма място за толкова конфигурации." << endl;
        else printf("Броят конфигурации трябва да е в интервала: [%d - %d]\n", 1, remaining_slots);
    }
}

void process_print_configurations_by_brand_request(Computer configurations[], int& present_configurations_count) {
    cout << "Въведете марка, за която искате да намерите конфигурации: ";
    string brand;
    getline(cin, brand);
    print_configurations_by_brand(configurations, present_configurations_count, brand);
}

bool is_configurations_count_valid(int& configurations_count, int& present_configurations_count)
{
    return configurations_count > 0 &&
        MAX_NUMBER_OF_CONFIGURATIONS - present_configurations_count >= configurations_count;
}

void read_precessor_data(string& manufacturer, string& model, double& frequency, int& cores)
{   
    cout << "Въведете производител на процесора: ";
    getline(cin, manufacturer);
    cout << "Въведете модел на процесора: ";
    getline(cin, model);
    cout << "Въведете честота на процесора: ";
    read_valid_double_value(frequency);
    cout << "Въведете брой ядра на процесора: ";
    read_valid_integer_value(cores);
}

void read_computer_data(string& id, string& brand, string& model, double& ram, double& price, string& status)
{
    cout << "Въведете сериен номер на компютъра: ";
    getline(cin, id);
    cout << "Въведете марка на компютъра: ";
    getline(cin, brand);
    cout << "Въведете модел на компютъра: ";
    getline(cin, model);
    cout << "Въведете RAM на компютъра: ";
    read_valid_double_value(ram);
    cout << "Въведете цена на компютъра: ";
    read_valid_double_value(price);
    cin.ignore();
    cout << "Въведете скаладова наличност на компютъра: ";
    getline(cin, status);
}

void print_configurations(Computer configurations[], int& present_configurations_count, int& configurations_count_to_print)
{
    if (present_configurations_count == 0)
    {
        cout << "Няма запазени конфигурации." << endl;
        return;
    }
    if (configurations_count_to_print > present_configurations_count)
    {
        cout << "Въведенения брой конфигурации надвишава броят на запазените конфигурации." << endl;
        return;
    }

    cout << "--- ИНФОРМАЦИЯ ЗА " << configurations_count_to_print << " КОНФИГУРАЦИИ--- " << endl;
    if (present_configurations_count == 0)
    {
        cout << "Няма запазени конфигурации." << endl;
        return;
    }
    for (int i = 0; i < configurations_count_to_print; i++)
    {  
        Computer computer = configurations[i];
        Processor processor = computer.processor;
        printf("\nКонфигурация номер: %d\nСериен номер: %s\nМарка: %s\nМодел: %s\nRAM памет: %.2f\nПроцесор:\n\tПроизводител: %s\n\tМодел: %s\n\tТактова честота: %.2f\n\tБрой ядра: %d\nЦена: %.2f лв.\nСтатус: %s\n",
            i + 1, computer.id.c_str(), computer.brand.c_str(), computer.model.c_str(), computer.ram, processor.manufacturer.c_str(), processor.model.c_str(), processor.frequency, processor.cores, computer.price, computer.status.c_str());
    }
}

void print_configurations_by_brand(Computer configurations[], int& present_configurations_count, string brand)
{
    bool is_brand_existing = false;
    for (int i = 0; i < present_configurations_count; i++)
    {
        Computer computer = configurations[i];
        if (computer.brand.compare(brand) == 0)
        {
            Processor processor = computer.processor;
            printf("\nКонфигурация номер: %d\nСериен номер: %s\nМарка: %s\nМодел: %s\nRAM памет: %.2f\nПроцесор:\n\tПроизводител: %s\n\tМодел: %s\n\tТактова честота: %.2f\n\tБрой ядра: %d\nЦена: %.2f лв.\nСтатус: %s\n",
                i + 1, computer.id.c_str(), computer.brand.c_str(), computer.model.c_str(), computer.ram, processor.manufacturer.c_str(), processor.model.c_str(), processor.frequency, processor.cores, computer.price, computer.status.c_str());
            is_brand_existing = true;
        }
    }
    if (!is_brand_existing) printf("Конфигурации с марката %s не са намерени.\n", brand.c_str());
}

void sort_configurations_by_processor_frequency_desc(Computer configurations[], int& present_configurations_count, Computer sorted_configurations[])
{
    for (int i = 0; i < present_configurations_count; i++) sorted_configurations[i] = configurations[i];

    for (int i = 0; i < present_configurations_count - 1; i++)
    {
        bool swapped = false;
        for (int j = 0; j < present_configurations_count - 1 - i; j++)
        {
            if (sorted_configurations[j].processor.frequency < sorted_configurations[j + 1].processor.frequency)
            {
                Computer temp = sorted_configurations[j];
                sorted_configurations[j] = configurations[j + 1];
                sorted_configurations[j + 1] = temp;
                swapped = true;
            }
        }
        if (!swapped) break;
    }
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

void read_valid_double_value(double& value)
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

bool configuration_exists_by_id(string id, Computer configurations[], int& present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0) return true;
    }
    return false;
}

void update_configuration(Computer configurations[], int& present_configurations_count)
{
    cout << "--- АКТУАЛИЗИРАНЕ НА КОНФИГУРАЦИЯ ---\n";
    /*struct Processor
  {
      string manufacturer;
      string model;
      double frequency;
      int cores;
  };

  struct Computer
  {
      string id;
      string brand;
      string model;
      Processor processor;
      double ram;
      double price;
      string status;
  };*/

    cout << "Въведете сериен номер на компютъра: ";
    cin.ignore();
    string id;
    getline(cin, id);

    

    if (!configuration_exists_by_id(id, configurations, present_configurations_count))
    {
        cout << "\nНе съществува конфигурация с този сериен номер!";
        return;
    }

    printf("\nКонфигурация със сериен номер %s е намерена успешно!\n", id.c_str());
    int update_option;
    do
    {
        printf("\nВъведете % d, за да промените сериен номер.\nВъведете % d, за да проемините марката.\nВъведете % d, за да промените модела.\nВъведете % d, за да промените RAM стойността.\nВъведете % d, за да промените цената.\nВъведете % d, за да промемите наличния статус.\nВъведете % d, за да направите промяна по данните на процесора.\nВъведете % d, за да прекратите корекцията на данни.\n",
            1, 2, 3, 4, 5, 6, 7, 8);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", 1, 8);
            read_valid_integer_value(update_option);
        } while (update_option < 1 || update_option > 8);

        switch (update_option)
        {
        case 1:
            cout << endl;
            update_configuration_id(id, configurations, present_configurations_count);
            cout << endl;
            break;
        }
    } while (update_option != 8);

}


void update_configuration_id(string old_id, Computer configurations[], int& present_configurations_count)
{
    cout << "--- ПРОМЯНА НА СЕРИЕН НОМЕР ---\nВъведете нов сериен номер: ";
    string updated_id;
    cin.ignore();
    getline(cin, updated_id);
    if (configuration_exists_by_id(updated_id, configurations, present_configurations_count))
    {
        cout << "\nВече съществува конфигурация с този сериен номер.";
        return;
    }

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(old_id) == 0) 
        {
            configurations[i].id = updated_id;
            cout << "\nСерийният номер е обновен успешно!";
            return;
        }
    }
}