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
    bool is_available;
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
const int SELL_CONFIGURATION_CHOICE = 6;
const int EXIT_FROM_MENU_CHOICE = 7;

const int UPDATE_CONFIGURATION_ID_CHOICE = 1;
const int UPDATE_CONFIGURATION_BRAND_CHOICE = 2;
const int UPDATE_CONFIGURATION_MODEL_CHOICE = 3;
const int UPDATE_CONFIGURATION_RAM_CHOICE = 4;
const int UPDATE_CONFIGURATION_PRICE_CHOICE = 5;
const int UPDATE_CONFIGURATION_AVAILABILITY_STATUS_CHOICE = 6;
const int UPDATE_CONFIGURATION_PROCESSOR_CHOICE = 7;
const int EXIT_FROM_CONFIGURATION_UPDATE_MENU_CHOICE = 8;

const int UPDATE_PROCESSOR_MANUFACTURER_CHOICE = 1;
const int UPDATE_PROCESSOR_MODEL_CHOICE = 2;
const int UPDATE_PROCESSOR_FREQUENCY_CHOICE = 3;
const int UPDATE_PROCESSOR_CORES_COUNT_CHOICE = 4;
const int EXIT_FROM_UPDATE_PROCESSOR_MENU_CHOICE = 8;

/*
* Prototypes
*/
void process_add_configuration_request(int& present_configurations_count, Computer configurations[]);
bool is_configurations_count_valid(int& configurations_count, int& present_configurations_count);
void read_precessor_data(string& manufacturer, string& model, double& frequency, int& cores);
void read_computer_data(string& id, string& brand, string& model, double& ram, double& price, bool& is_available);
void print_configurations(Computer configurations[], int& present_configurations_count, int& configurations_count_to_print);
void sort_configurations_by_processor_frequency_desc(Computer configurations[], int& present_configurations_count, Computer sorted_configurations[]);
void print_configurations_by_brand(Computer configurations[], int& present_configurations_count, string& brand);
void read_valid_integer_value(int& value);
void read_valid_double_value(double& value);
void process_print_configurations_by_brand_request(Computer configurations[], int& present_configurations_count);
bool configuration_exists_by_id(string& id, Computer configurations[], int& present_configurations_count);
void update_configuration(Computer configurations[], int& present_configurations_count);
void update_configuration_id(string& old_id, Computer configurations[], int& present_configurations_count);
void update_configuration_brand(string& id, Computer configurations[], int& present_configurations_count);
void update_configuration_model(string& id, Computer configurations[], int& present_configurations_count);
void update_configuration_ram(string& id, Computer configurations[], int& present_configurations_count);
void update_configuration_price(string& id, Computer configurations[], int& present_configurations_count);
void update_configuration_status(string& id, Computer configurations[], int& present_configurations_count);
void update_processor(string& configuration_id, Computer configurations[], int& present_configurations_count);
void update_processor_manufacturer(string& id, Computer configurations[], int& present_configurations_count);
void update_processor_model(string& id, Computer configurations[], int& present_configurations_count);
void update_configuration_frequency(string& id, Computer configurations[], int& present_configurations_count);
void update_configuration_cores(string& id, Computer configurations[], int& present_configurations_count);
bool is_configuration_available(string& id, Computer configurations[], int& present_configurations_count);
void print_sorted_configurations_by_processor_frequency_desc(Computer configurations[], int& present_configurations_count, Computer sorted_configurations[]);
void sell_configuration(Computer configurations[], int& present_configurations_count);
void sell_configuration_by_id(Computer configurations[], int& present_configurations_count);
void print_configuration(Computer computer);
Computer get_configuration_by_id(string& id, Computer configurations[], int& present_configurations_count);
void change_configuration_availability_status(string& id, Computer configurations[], int& present_configurations_count);
void sell_configuration_by_requirements(Computer configurations[], int& present_configurations_count);
void read_precessor_selling_data(string& manufacturer, string& model, string& frequency, string& cores);
void read_computer_selling_data(string& brand, string& model, string& ram, string& price);
bool compare_strings_case_insensitive(string first, string second);


int main()
{
    setlocale(LC_ALL, "BG");
    SetConsoleOutputCP(1251);
    SetConsoleCP(1251);

    int choice, present_configurations_count(INITIAL_CONFIGURATIONS_COUNT);
    Computer configurations[MAX_NUMBER_OF_CONFIGURATIONS];
    Computer sorted_configurations[MAX_NUMBER_OF_CONFIGURATIONS];

    Processor processor = { "Intel", "i7", 3.60, 6 };
    Computer computer = { "a1", "Acer", "Nitro", processor, 16.0, 2200, 1};
    configurations[present_configurations_count++] = computer;
    
    do
    {
        printf("Въведете %d, за да добавите нова конфигурация.\nВъведете %d, за да изведете всички конфигурации.\nВъведете %d, за да изведете желан брой конфигурации с най-голяма тактова честота на процесора.\nВъведете %d, за да изведете конфигурации от избрана марка.\nВъведете %d, за да редактирате конфигурация.\nВъведете %d, за да осъществите продажба.\nВъведете %d, за да спрете програмата.\n",
            ADD_NEW_CONFIGURATION_CHOICE, PRINT_ALL_CONFIGURATIONS_CHOICE, PRINT_SORTED_CONFIGURATIONS_BY_PROCESSOR_FREQUENCY_DESC_CHOICE, PRINT_CONFIGURATIONS_BY_BRAND_CHOICE, EDIT_CONFIGURATION_CHOICE, SELL_CONFIGURATION_CHOICE, EXIT_FROM_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", ADD_NEW_CONFIGURATION_CHOICE, EXIT_FROM_MENU_CHOICE);
            read_valid_integer_value(choice);
        } while (choice < ADD_NEW_CONFIGURATION_CHOICE || choice > EXIT_FROM_MENU_CHOICE);

        switch (choice)
        {
        case ADD_NEW_CONFIGURATION_CHOICE:
            process_add_configuration_request(present_configurations_count, configurations);
            break;

        case PRINT_ALL_CONFIGURATIONS_CHOICE:
            print_configurations(configurations, present_configurations_count, present_configurations_count);
            break;

        case PRINT_CONFIGURATIONS_BY_BRAND_CHOICE:
            process_print_configurations_by_brand_request(configurations, present_configurations_count);
            break;

        case PRINT_SORTED_CONFIGURATIONS_BY_PROCESSOR_FREQUENCY_DESC_CHOICE:
            print_sorted_configurations_by_processor_frequency_desc(configurations, present_configurations_count, sorted_configurations);
            break;

        case EDIT_CONFIGURATION_CHOICE:
            update_configuration(configurations, present_configurations_count);
            cout << endl;
            break;

        case SELL_CONFIGURATION_CHOICE:
            cout << endl;
            sell_configuration(configurations, present_configurations_count);
            break;
        }
        cout << endl;
    } while (choice != EXIT_FROM_MENU_CHOICE);
}

void process_add_configuration_request(int& present_configurations_count, Computer configurations[])
{
    printf("\nВъведете брой конфигурации: ");
    int configurations_count;
    read_valid_integer_value(configurations_count);
    cin.ignore();
    if (is_configurations_count_valid(configurations_count, present_configurations_count))
    {
        string processor_manufacturer, processor_model, computer_id, computer_brand, computer_model;
        bool is_configuration_available;
        double processor_frequency, computer_ram, computer_price;
        int processor_cores;

        for (int i = 0; i < configurations_count; i++)
        {
            cout << endl << "--- ИНФОРМАЦИЯ ЗА ПРОЦЕСОР ---" << endl;
            read_precessor_data(processor_manufacturer, processor_model, processor_frequency, processor_cores);
            Processor processor = { processor_manufacturer, processor_model, processor_frequency, processor_cores };

            cout << endl << "--- ИНФОРМАЦИЯ ЗА КОМПЮТЪР ---" << endl;
            cin.ignore();
            read_computer_data(computer_id, computer_brand, computer_model, computer_ram, computer_price, is_configuration_available);
            if (configuration_exists_by_id(computer_id, configurations, present_configurations_count))
            {
                cout << "\nВече съществува конфигурация с този сериен номер!\nКОНФИГУРАЦИЯТА НЕ Е ДОБАВЕНА УСПЕШНО!\n";
                return;
            }
            Computer computer = { computer_id, computer_brand, computer_model, processor, computer_ram, computer_price, is_configuration_available };

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
    cout << "\nВъведете марка, за която искате да намерите конфигурации: ";
    string brand;
    cin.ignore();
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

void read_computer_data(string& id, string& brand, string& model, double& ram, double& price, bool& is_available)
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
    cout << "Налична ли е конфигурацията? (true/false): ";
    cin.ignore();
    cin >> boolalpha >> is_available;
}

void print_configurations(Computer configurations[], int& present_configurations_count, int& configurations_count_to_print)
{
    if (present_configurations_count == 0)
    {
        cout << "\nНяма запазени конфигурации." << endl;
        return;
    }
    if (configurations_count_to_print > present_configurations_count)
    {
        cout << "\nВъведенения брой конфигурации надвишава броят на запазените конфигурации." << endl;
        return;
    }

    cout << "\n--- ИНФОРМАЦИЯ ЗА " << configurations_count_to_print << " КОНФИГУРАЦИИ--- " << endl;
    for (int i = 0; i < configurations_count_to_print; i++)
    {  
        Computer computer = configurations[i];
        Processor processor = computer.processor;
        printf("\nКонфигурация номер: %d\nСериен номер: %s\nМарка: %s\nМодел: %s\nRAM памет: %.2f\nПроцесор:\n\tПроизводител: %s\n\tМодел: %s\n\tТактова честота: %.2f\n\tБрой ядра: %d\nЦена: %.2f лв.\nНаличен статус: %s\n",
            i + 1, computer.id.c_str(), computer.brand.c_str(), computer.model.c_str(), computer.ram, processor.manufacturer.c_str(), processor.model.c_str(), processor.frequency, processor.cores, computer.price, computer.is_available ? "true" : "false");
    }
}

void print_configurations_by_brand(Computer configurations[], int& present_configurations_count, string& brand)
{
    bool is_brand_existing = false;
    for (int i = 0; i < present_configurations_count; i++)
    {
        Computer computer = configurations[i];
        if (computer.brand.compare(brand) == 0)
        {
            Processor processor = computer.processor;
            printf("\nКонфигурация номер: %d\nСериен номер: %s\nМарка: %s\nМодел: %s\nRAM памет: %.2f\nПроцесор:\n\tПроизводител: %s\n\tМодел: %s\n\tТактова честота: %.2f\n\tБрой ядра: %d\nЦена: %.2f лв.\nНаличен статус: %s\n",
                i + 1, computer.id.c_str(), computer.brand.c_str(), computer.model.c_str(), computer.ram, processor.manufacturer.c_str(), processor.model.c_str(), processor.frequency, processor.cores, computer.price, computer.is_available ? "true" : "false");
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

bool configuration_exists_by_id(string& id, Computer configurations[], int& present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0) return true;
    }
    return false;
}

void print_sorted_configurations_by_processor_frequency_desc(Computer configurations[], int& present_configurations_count, Computer sorted_configurations[])
{
    int configurations_to_print;
    sort_configurations_by_processor_frequency_desc(configurations, present_configurations_count, sorted_configurations);
    cout << "\nВъведете брой конфигурации за извеждане: ";
    read_valid_integer_value(configurations_to_print);
    print_configurations(sorted_configurations, present_configurations_count, configurations_to_print);
}

void update_configuration(Computer configurations[], int& present_configurations_count)
{
    cout << "\n--- АКТУАЛИЗИРАНЕ НА КОНФИГУРАЦИЯ ---\nВъведете сериен номер на компютъра: ";
    cin.ignore();
    string id;
    getline(cin, id);

    if (!configuration_exists_by_id(id, configurations, present_configurations_count))
    {
        cout << "\nНе съществува конфигурация с този сериен номер!";
        return;
    }

    if (!is_configuration_available(id, configurations, present_configurations_count))
    {
        cout << "\nКонфигурацията е продадена и не може да бъде редактирана!";
        return;
    }

    printf("\nКонфигурация със сериен номер %s е намерена успешно!\n", id.c_str());
    int update_option;
    do
    {
        printf("\nВъведете %d, за да промените сериен номер.\nВъведете %d, за да проемините марката.\nВъведете %d, за да промените модела.\nВъведете %d, за да промените RAM стойността.\nВъведете %d, за да промените цената.\nВъведете %d, за да промемите наличния статус.\nВъведете %d, за да направите промяна по данните на процесора.\nВъведете %d, за да прекратите корекцията на данни.\n",
            UPDATE_CONFIGURATION_ID_CHOICE, UPDATE_CONFIGURATION_BRAND_CHOICE, UPDATE_CONFIGURATION_MODEL_CHOICE, UPDATE_CONFIGURATION_RAM_CHOICE, UPDATE_CONFIGURATION_PRICE_CHOICE, UPDATE_CONFIGURATION_AVAILABILITY_STATUS_CHOICE, UPDATE_CONFIGURATION_PROCESSOR_CHOICE, EXIT_FROM_CONFIGURATION_UPDATE_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", UPDATE_CONFIGURATION_ID_CHOICE, EXIT_FROM_CONFIGURATION_UPDATE_MENU_CHOICE);
            read_valid_integer_value(update_option);
        } while (update_option < UPDATE_CONFIGURATION_ID_CHOICE || update_option > EXIT_FROM_CONFIGURATION_UPDATE_MENU_CHOICE);

        switch (update_option)
        {
        case UPDATE_CONFIGURATION_ID_CHOICE:
            cout << endl;
            update_configuration_id(id, configurations, present_configurations_count);
            cout << endl;
            break;
        case UPDATE_CONFIGURATION_BRAND_CHOICE:
            cout << endl;
            update_configuration_brand(id, configurations, present_configurations_count);
            cout << endl;
            break;
        case UPDATE_CONFIGURATION_MODEL_CHOICE:
            cout << endl;
            update_configuration_model(id, configurations, present_configurations_count);
            cout << endl;
            break;
        case UPDATE_CONFIGURATION_RAM_CHOICE:
            cout << endl;
            update_configuration_ram(id, configurations, present_configurations_count);
            cout << endl;
            break;
        case UPDATE_CONFIGURATION_PRICE_CHOICE:
            cout << endl;
            update_configuration_price(id, configurations, present_configurations_count);
            cout << endl;
            break;
        case UPDATE_CONFIGURATION_AVAILABILITY_STATUS_CHOICE:
            cout << endl;
            update_configuration_status(id, configurations, present_configurations_count);
            cout << endl;
            break;
        case UPDATE_CONFIGURATION_PROCESSOR_CHOICE:
            cout << endl;
            update_processor(id, configurations, present_configurations_count);
            cout << endl;
            break;
        }
    } while (update_option != EXIT_FROM_CONFIGURATION_UPDATE_MENU_CHOICE);
}


void update_configuration_id(string& old_id, Computer configurations[], int& present_configurations_count)
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

void update_configuration_brand(string& id, Computer configurations[], int& present_configurations_count)
{
    cout << "--- ПРОМЯНА НА МАРКА ---\nВъведете нова марка: ";
    string new_brand;
    cin.ignore();
    getline(cin, new_brand);

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].brand = new_brand;
            cout << "\nМарката е обновена успешно!";
            return;
        }
    }
}

void update_configuration_model(string& id, Computer configurations[], int& present_configurations_count)
{
    cout << "--- ПРОМЯНА НА МОДЕЛ ---\nВъведете нов модел: ";
    string new_model;
    cin.ignore();
    getline(cin, new_model);

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].model = new_model;
            cout << "\nМоделът е обновен успешно!";
            return;
        }
    }
}

void update_configuration_ram(string& id, Computer configurations[], int& present_configurations_count)
{
    cout << "--- ПРОМЯНА НА RAM СТОЙНОСТ ---\nВъведете нова RAM стойност: ";
    double new_ram;
    read_valid_double_value(new_ram);

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].ram = new_ram;
            cout << "\nRAM стойността е обновена успешно!";
            return;
        }
    }
}

void update_configuration_price(string& id, Computer configurations[], int& present_configurations_count)
{
    cout << "--- ПРОМЯНА НА ЦЕНА ---\nВъведете нова цена: ";
    double new_price;
    read_valid_double_value(new_price);

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].price = new_price;
            cout << "\nЦената е обновена успешно!";
            return;
        }
    }
}

void update_configuration_status(string& id, Computer configurations[], int& present_configurations_count)
{
    cout << "--- ПРОМЯНА НА НАЛИЧЕН СТАТУС ---\nВъведете нов наличен статус: ";
    bool new_available_status;
    cin >> boolalpha >> new_available_status;

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].is_available = new_available_status;
            cout << "\nНаличният статус е обновен успешно!";
            return;
        }
    }
}

void update_processor(string& configuration_id, Computer configurations[], int& present_configurations_count)
{
    cout << "--- АКТУАЛИЗИРАНЕ НА ПРОЦЕСОР ---\n";

    int update_option;
    do
    {
        printf("\nВъведете %d, за да промените производителя.\nВъведете %d, за да проемините модела.\nВъведете %d, за да промените честотата.\nВъведете %d, за да промените броят ядра.\nВъведете %d, за да прекратите корекцията на данни.\n",
            UPDATE_PROCESSOR_MANUFACTURER_CHOICE, UPDATE_PROCESSOR_MODEL_CHOICE, UPDATE_PROCESSOR_FREQUENCY_CHOICE, UPDATE_PROCESSOR_CORES_COUNT_CHOICE, EXIT_FROM_UPDATE_PROCESSOR_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", UPDATE_PROCESSOR_MANUFACTURER_CHOICE, EXIT_FROM_UPDATE_PROCESSOR_MENU_CHOICE);
            read_valid_integer_value(update_option);
        } while (update_option < UPDATE_PROCESSOR_MANUFACTURER_CHOICE || update_option > EXIT_FROM_UPDATE_PROCESSOR_MENU_CHOICE);

        switch (update_option)
        {
        case UPDATE_PROCESSOR_MANUFACTURER_CHOICE:
            cout << endl;
            update_processor_manufacturer(configuration_id, configurations, present_configurations_count);
            cout << endl;
            break;
        case UPDATE_PROCESSOR_MODEL_CHOICE:
            cout << endl;
            update_processor_model(configuration_id, configurations, present_configurations_count);
            cout << endl;
            break;
        case UPDATE_PROCESSOR_FREQUENCY_CHOICE:
            cout << endl;
            update_configuration_frequency(configuration_id, configurations, present_configurations_count);
            cout << endl;
            break;
        case UPDATE_PROCESSOR_CORES_COUNT_CHOICE:
            cout << endl;
            update_configuration_cores(configuration_id, configurations, present_configurations_count);
            cout << endl;
            break;
        }
    } while (update_option != EXIT_FROM_UPDATE_PROCESSOR_MENU_CHOICE);
}

void update_processor_manufacturer(string& id, Computer configurations[], int& present_configurations_count)
{
    cout << "--- ПРОМЯНА НА ПРОИЗВОДИТЕЛ ---\nВъведете нов производител: ";
    string new_manufacturer;
    cin.ignore();
    getline(cin, new_manufacturer);

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].processor.manufacturer = new_manufacturer;
            cout << "\nПроизводителят е обновен успешно!";
            return;
        }
    }
}

void update_processor_model(string& id, Computer configurations[], int& present_configurations_count)
{
    cout << "--- ПРОМЯНА НА МОДЕЛ ---\nВъведете нов модел: ";
    string new_model;
    cin.ignore();
    getline(cin, new_model);

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].processor.model = new_model;
            cout << "\nМоделът е обновен успешно!";
            return;
        }
    }
}

void update_configuration_frequency(string& id, Computer configurations[], int& present_configurations_count)
{
    cout << "--- ПРОМЯНА НА ЧЕСТОТА ---\nВъведете нова честота: ";
    double new_frequency;
    read_valid_double_value(new_frequency);

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].processor.frequency = new_frequency;
            cout << "\nЧестотата е обновена успешно!";
            return;
        }
    }
}

void update_configuration_cores(string& id, Computer configurations[], int& present_configurations_count)
{
    cout << "--- ПРОМЯНА НА БРОЙ ЯДРА ---\nВъведете нов брой ядра: ";
    int new_cores;
    read_valid_integer_value(new_cores);

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].processor.cores = new_cores;
            cout << "\nБроят ядра е обновен успешно!";
            return;
        }
    }
}

bool is_configuration_available(string& id, Computer configurations[], int& present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0) return configurations[i].is_available;
    }
    return false;
}

void sell_configuration(Computer configurations[], int& present_configurations_count)
{
    cout << "--- ПРОДАЖБА НА КОНФИГУРАЦИЯ ---\n";
    int menu_choice;
    do
    {
        printf("\nВъведете %d, за да осъществите продажба по сериен номер.\nВъведете %d, за да осъществите продажба по определени характеристики.\nВъведете %d, за да прекратите продажбата.\n",
            1, 2, 3);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", 1, 3);
            read_valid_integer_value(menu_choice);
        } while (menu_choice < 1 || menu_choice > 3);

        switch (menu_choice)
        {
        case 1:
            cout << endl;
            sell_configuration_by_id(configurations, present_configurations_count);
            cout << endl;
            break;

        case 2:
            cout << endl;
            sell_configuration_by_requirements(configurations, present_configurations_count);
            cout << endl;
            break;
        }
    } while (menu_choice != 3);
}

void sell_configuration_by_id(Computer configurations[], int& present_configurations_count)
{
    cout << "Въведете сериен номер на компютъра: ";
    cin.ignore();
    string id;
    getline(cin, id);

    if (!configuration_exists_by_id(id, configurations, present_configurations_count))
    {
        cout << "\nНе съществува конфигурация с този сериен номер!";
        return;
    }

    if (!is_configuration_available(id, configurations, present_configurations_count))
    {
        cout << "\nКонфигурацията не е налична за продажба!";
        return;
    }

    printf("\nКонфигурация със сериен номер %s е намерена успешно!\n\nДетайли за конфигурация:\n", id.c_str());
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);

    cout << "\nВъведете цена: ";
    double price_to_pay;
    read_valid_double_value(price_to_pay);

    if (price_to_pay < computer.price)
    {
        cout << "\nНедостатъчна сума пари!";
        return;
    }

    change_configuration_availability_status(id, configurations, present_configurations_count);
    double money_change = price_to_pay - computer.price;
    printf("Конфигурацията е успешно продадена!\nРесто: %.2f лв.", money_change);
}

Computer get_configuration_by_id(string& id, Computer configurations[], int& present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0) return configurations[i];
    }
}

void print_configuration(Computer computer)
{
    Processor processor = computer.processor;
    printf("\nСериен номер: %s\nМарка: %s\nМодел: %s\nRAM памет: %.2f\nПроцесор:\n\tПроизводител: %s\n\tМодел: %s\n\tТактова честота: %.2f\n\tБрой ядра: %d\nЦена: %.2f лв.\nНаличен статус: %s\n", 
        computer.id.c_str(), computer.brand.c_str(), computer.model.c_str(), computer.ram, processor.manufacturer.c_str(), processor.model.c_str(), processor.frequency, processor.cores, computer.price, computer.is_available ? "true" : "false");
}

void change_configuration_availability_status(string& id, Computer configurations[], int& present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0) configurations[i].is_available = false;
    }
}

void sell_configuration_by_requirements(Computer configurations[], int& present_configurations_count)
{
    string processor_manufacturer, processor_model, processor_frequency, processor_cores, computer_brand, computer_model, computer_ram, computer_price;

    cin.ignore();
    read_precessor_selling_data(processor_manufacturer, processor_model, processor_frequency, processor_cores);
    read_computer_selling_data(computer_brand, computer_model, computer_ram, computer_price);

    Computer found_configurations[MAX_NUMBER_OF_CONFIGURATIONS];
    int found_configurations_count = 0;

    int selected_features = 0;
    int actual_features = 0;
    //find_computers_with_requirements;
    for (int i = 0; i < present_configurations_count; i++)
    {
        selected_features = 0;
        actual_features = 0;
        Computer computer = configurations[i];

        if (!processor_manufacturer.empty())
        {
            selected_features++;
            if (compare_strings_case_insensitive(computer.processor.manufacturer, processor_manufacturer)) actual_features++;
        }

        if (!processor_model.empty())
        {
            selected_features++;
            if (compare_strings_case_insensitive(computer.processor.model, processor_model)) actual_features++;
        }

        if (!processor_frequency.empty())
        {
            selected_features++;
            if (computer.processor.frequency == stod(processor_frequency)) actual_features++;
        }

        if (!processor_cores.empty())
        {
            selected_features++;
            if (computer.processor.cores == stoi(processor_cores)) actual_features++;
        }

        if (!computer_brand.empty())
        {
            selected_features++;
            if (compare_strings_case_insensitive(computer.brand, computer_brand)) actual_features++;
        }

        if (!computer_model.empty())
        {
            selected_features++;
            if (compare_strings_case_insensitive(computer.model, computer_model)) actual_features++;
        }

        if (!computer_ram.empty())
        {
            selected_features++;
            if (computer.ram == stod(computer_ram)) actual_features++;
        }

        if (!computer_price.empty())
        {
            selected_features++;
            if (computer.price == stod(computer_price)) actual_features++;
        }

        if (selected_features == actual_features) found_configurations[found_configurations_count++] = computer;
    }

    if (found_configurations_count > 0)
    {
        cout << "\nПодходящи конфигурации:\n\n";
        print_configurations(found_configurations, found_configurations_count, found_configurations_count);
    }

    cout << "\nЖелаете ли да осъществите продажба? (Y/N): ";
    char confirm;
    cin >> confirm;

    if (tolower(confirm) == 'y') sell_configuration_by_id(configurations, present_configurations_count);
}

void read_precessor_selling_data(string& manufacturer, string& model, string& frequency, string& cores)
{
    cout << "Въведете желан производител на процесора: ";
    getline(cin, manufacturer);
    cout << "Въведете желан модел на процесора: ";
    getline(cin, model);
    cout << "Въведете желана честота на процесора: ";
    getline(cin, frequency);
    cout << "Въведете брой ядра на процесора: ";
    getline(cin, cores);
}

void read_computer_selling_data(string& brand, string& model, string& ram, string& price)
{
    cout << "Въведете желана марка на компютъра: ";
    getline(cin, brand);
    cout << "Въведете желан модел на компютъра: ";
    getline(cin, model);
    cout << "Въведете RAM на компютъра: ";
    getline(cin, ram);
    cout << "Въведете цена на компютъра: ";
    getline(cin, price);
}

bool compare_strings_case_insensitive(string first, string second)
{
    if (first.length() != second.length()) return false;
    
    for (int i = 0; i < first.length(); i++)
    {
        if (tolower(first[i]) != tolower(second[i])) return false;
    }

    return true;
}