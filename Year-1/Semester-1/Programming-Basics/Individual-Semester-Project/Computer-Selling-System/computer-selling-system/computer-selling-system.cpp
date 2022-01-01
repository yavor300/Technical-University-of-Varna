#include <iostream>
#include <string>
#include <Windows.h>
#include <fstream>
#include <iomanip>
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
    string available_status;
};

/*
* Constants
*/
const int MAX_NUMBER_OF_CONFIGURATIONS = 100;
const int INITIAL_CONFIGURATIONS_COUNT = 0;
const int DIGITS_TO_PRINT_AFTER_DECIMAL_POINT = 2;

const int ADD_NEW_CONFIGURATION_CHOICE = 1;
const int PRINT_ALL_CONFIGURATIONS_CHOICE = 2;
const int PRINT_CONFIGURATIONS_WITH_HIGHEST_PROCESSOR_FREQUENCY = 3;
const int PRINT_CONFIGURATIONS_BY_BRAND_CHOICE = 4;
const int EDIT_CONFIGURATION_CHOICE = 5;
const int SELL_CONFIGURATION_CHOICE = 6;
const int MAKE_CONFIGURATIONS_AUDIT_CHOICE = 7;
const int EXIT_FROM_MENU_CHOICE = 8;

const int UPDATE_CONFIGURATION_ID_CHOICE = 1;
const int UPDATE_CONFIGURATION_BRAND_CHOICE = 2;
const int UPDATE_CONFIGURATION_MODEL_CHOICE = 3;
const int UPDATE_CONFIGURATION_RAM_CHOICE = 4;
const int UPDATE_CONFIGURATION_PRICE_CHOICE = 5;
const int UPDATE_CONFIGURATION_AVAILABILITY_STATUS_CHOICE = 6;
const int UPDATE_CONFIGURATION_PROCESSOR_CHOICE = 7;
const int EXIT_FROM_CONFIGURATION_UPDATE_MENU_CHOICE = 8;

const int SELL_BY_SERIAL_NUMBER_CHOICE = 1;
const int SELL_BY_REQUIREMENTS_CHOICE = 2;
const int EXIT_FROM_SELLING_MENU_CHOICE = 3;

const int UPDATE_PROCESSOR_MANUFACTURER_CHOICE = 1;
const int UPDATE_PROCESSOR_MODEL_CHOICE = 2;
const int UPDATE_PROCESSOR_FREQUENCY_CHOICE = 3;
const int UPDATE_PROCESSOR_CORES_COUNT_CHOICE = 4;
const int EXIT_FROM_UPDATE_PROCESSOR_MENU_CHOICE = 5;

const int PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_ID_CHOICE = 1;
const int PRINT_CONFIGURATIONS_BY_BRAND_AND_RAM_SORTED_BY_PRICE_DESC_CHOICE = 2;
const int PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_PROCESSOR_MODEL_CHOICE = 3;
const int EXIT_FROM_AUDIT_MENU_CHOICE = 4;

const string CONFIGURATION_IN_SELL_STATUS = "в продажба";
const string CONFIGURATION_SOLD_STATUS = "продадена";

/*
* Prototypes
*/
void read_configurations_from_file(Computer configurations[], int& present_configurations_count);
void process_add_configuration_request(Computer configurations[], int& present_configurations_count);
void read_valid_integer_value(int& value);
bool is_configurations_count_valid(int configurations_count, int present_configurations_count);
void add_configuration(Computer configurations[], int& present_configurations_count, string& processor_manufacturer,
    string& processor_model, string& computer_id, string& computer_brand, string& computer_model, string& available_status,
    double& processor_frequency, double& computer_ram, double& computer_price, int& processor_cores);
void read_precessor_data(string& manufacturer, string& model, double& frequency, int& cores);
bool is_string_empty(string& input);
void read_computer_data(string& id, string& brand, string& model, double& ram, double& price, string& available_status, Computer configurations[], int present_configurations_count);
void process_print_all_configurations_request(Computer configurations[], int present_configurations_count);
void print_all_configurations(Computer configurations[], int present_configurations_count);
void print_configuration(Computer& computer);
void process_print_configurations_by_brand_request(Computer configurations[], int present_configurations_count);
void print_configurations_by_brand(Computer configurations[], int present_configurations_count, string& brand);
void print_configurations_with_highest_processor_frequency(Computer configurations[], int present_configurations_count);
double find_max_processor_frequency(Computer configurations[], int present_configurations_count);
void print_configuraions_by_processor_frequency(Computer configurations[], int present_configurations_count, double processor_frequency);
void update_configuration(Computer configurations[], int present_configurations_count);
bool configuration_exists_by_id(string& id, Computer configurations[], int present_configurations_count);
bool is_configuration_available(string& id, Computer configurations[], int present_configurations_count);
void update_configuration_id(string& old_id, Computer configurations[], int present_configurations_count);
void update_configuration_brand(string& id, Computer configurations[], int present_configurations_count);
void update_configuration_model(string& id, Computer configurations[], int present_configurations_count);
void update_configuration_ram(string& id, Computer configurations[], int present_configurations_count);
void read_valid_double_value(double& value);
void update_configuration_price(string& id, Computer configurations[], int present_configurations_count);
void update_configuration_status(string& id, Computer configurations[], int present_configurations_count);
void update_processor(string& configuration_id, Computer configurations[], int present_configurations_count);
void update_processor_manufacturer(string& id, Computer configurations[], int present_configurations_count);
void update_processor_model(string& id, Computer configurations[], int present_configurations_count);
void update_configuration_frequency(string& id, Computer configurations[], int present_configurations_count);
void update_configuration_cores(string& id, Computer configurations[], int present_configurations_count);
void sell_configuration(Computer configurations[], int present_configurations_count);
void sell_configuration_by_id(Computer configurations[], int present_configurations_count);
Computer get_configuration_by_id(string& id, Computer configurations[], int present_configurations_count);
void change_configuration_availability_status(string& id, Computer configurations[], int present_configurations_count);
void sell_configuration_by_requirements(Computer configurations[], int present_configurations_count);
void read_precessor_selling_data(string& manufacturer, string& model, string& frequency, string& cores);
void read_computer_selling_data(string& brand, string& model, string& ram, string& price);
void find_computers_with_requirements(Computer configurations[], int present_configurations_count, Computer found_configurations[], int& found_configurations_count,
    string& processor_manufacturer, string& processor_model, string& processor_frequency, string& processor_cores, string& computer_brand,
    string& computer_model, string& computer_ram, string& computer_price, int& selected_features, int& actual_features);
bool compare_strings_case_insensitive(string first, string second);
void make_configurations_audit(Computer configurations[], int present_configurations_count);
void print_available_configurations_sorted_by_id(Computer configurations[], int present_configurations_count);
void sort_configurations_by_id(Computer configurations[], int present_configurations_count, Computer sorted_configurations[]);
int find_min_number(int first, int second);
void print_configurations_by_availability_status(Computer configurations[], int present_configurations_count, string available_status);
void print_configurations_by_brand_and_ram_sorted_by_price_desc(Computer configurations[], int present_configurations_count);
void sort_configurations_by_price_desc(Computer configurations[], int present_configurations_count, Computer sorted_configurations[]);
void print_configurations_by_brand_and_ram(Computer configurations[], int present_configurations_count, string& processor_model, double ram);
void print_unavailable_configurations_sorted_by_processor_model(Computer configurations[], int present_configurations_count);
void sort_configurations_by_processor_model(Computer configurations[], int present_configurations_count, Computer sorted_configurations[]);
void store_configurations_in_file(Computer configurations[], int present_configurations_count);
bool configuration_exists_by_processor_model_and_ram(Computer configurations[], int present_configurations_count, string& processor_model, double ram);
bool search_for_configurations_by_availability_status(Computer configurations[], int present_configurations_count, string available_status);

int main()
{
    setlocale(LC_ALL, "BG");
    SetConsoleOutputCP(1251);
    SetConsoleCP(1251);

    int menu_choice, present_configurations_count(INITIAL_CONFIGURATIONS_COUNT);
    Computer configurations[MAX_NUMBER_OF_CONFIGURATIONS];

    cout << "===== ИНФОРМАЦИОННА СИСТЕМА ЗА КОМПЮТЪРНИ КОНФИГУРАЦИИ =====\nРазработчик: Явор Чамов\n\n";

    read_configurations_from_file(configurations, present_configurations_count);
    
    do
    {
        printf("===== НАЧАЛНО МЕНЮ =====\n\nВъведете %d, за да добавите нова конфигурация.\nВъведете %d, за да изведете всички конфигурации.\nВъведете %d, за да изведете конфигурациите с най-висока тактова честота на процесора.\nВъведете %d, за да изведете конфигурации от избрана марка.\nВъведете %d, за да редактирате конфигурация.\nВъведете %d, за да осъществите продажба.\nВъведете %d, за да направите одит на конфигурациите.\nВъведете %d, за да запазите съществуващите конфигурации във файл и да спрете програмата.\n",
            ADD_NEW_CONFIGURATION_CHOICE, PRINT_ALL_CONFIGURATIONS_CHOICE, PRINT_CONFIGURATIONS_WITH_HIGHEST_PROCESSOR_FREQUENCY, PRINT_CONFIGURATIONS_BY_BRAND_CHOICE, EDIT_CONFIGURATION_CHOICE, SELL_CONFIGURATION_CHOICE, MAKE_CONFIGURATIONS_AUDIT_CHOICE, EXIT_FROM_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", ADD_NEW_CONFIGURATION_CHOICE, EXIT_FROM_MENU_CHOICE);
            read_valid_integer_value(menu_choice);
        } while (menu_choice < ADD_NEW_CONFIGURATION_CHOICE || menu_choice > EXIT_FROM_MENU_CHOICE);

        system("cls");
        switch (menu_choice)
        {
        case ADD_NEW_CONFIGURATION_CHOICE:
            process_add_configuration_request(configurations, present_configurations_count);
            break;

        case PRINT_ALL_CONFIGURATIONS_CHOICE:
            process_print_all_configurations_request(configurations, present_configurations_count);
            break;

        case PRINT_CONFIGURATIONS_BY_BRAND_CHOICE:
            process_print_configurations_by_brand_request(configurations, present_configurations_count);
            break;

        case PRINT_CONFIGURATIONS_WITH_HIGHEST_PROCESSOR_FREQUENCY:
            print_configurations_with_highest_processor_frequency(configurations, present_configurations_count);
            break;

        case EDIT_CONFIGURATION_CHOICE:
            update_configuration(configurations, present_configurations_count);
            break;

        case SELL_CONFIGURATION_CHOICE:
            sell_configuration(configurations, present_configurations_count);
            break;

        case MAKE_CONFIGURATIONS_AUDIT_CHOICE:
            make_configurations_audit(configurations, present_configurations_count);
            break;
        }
        cout << endl;
    } while (menu_choice != EXIT_FROM_MENU_CHOICE);

    store_configurations_in_file(configurations, present_configurations_count);
}

void process_add_configuration_request(Computer configurations[], int& present_configurations_count)
{
    cout << "===== ДОБАВЯНЕ НА НОВИ КОНФИГУРАЦИИ =====\n\nВъведете брой конфигурации за добавяне: ";
    int configurations_count;
    read_valid_integer_value(configurations_count);

    if (!is_configurations_count_valid(configurations_count, present_configurations_count))
    {
        cout << "\nВъведеният брой конфигурации не е валиден.";
        int remaining_slots = MAX_NUMBER_OF_CONFIGURATIONS - present_configurations_count;
        if (remaining_slots < configurations_count) printf("\nИма място за %d конфигурации.\n", remaining_slots);
        else printf("\nБроят конфигурации трябва да е в интервала: [%d - %d]\n", 1, remaining_slots);
        return;
    }

    string processor_manufacturer, processor_model, computer_id, computer_brand, computer_model, available_status;
    double processor_frequency, computer_ram, computer_price;
    int processor_cores;

    for (int i = 0; i < configurations_count; i++)
    {
        add_configuration(configurations, present_configurations_count, processor_manufacturer, processor_model,
            computer_id, computer_brand, computer_model, available_status, processor_frequency, computer_ram, computer_price,
            processor_cores);

        if (i != configurations_count - 1)
        {
            cout << "\nЩе продължите ли с въвеждането на конфигурации? (Y/N): ";
            char confirm;
            cin >> confirm;
            while (tolower(confirm) != 'y' && tolower(confirm) != 'n')
            {
                cout << "Въведете Y, за да продължите с въвежането.\nВъведете N, за да прекратите въвеждането.\nВъведете избор (Y/N): ";
                cin >> confirm;
            }
            cin.ignore();

            if (tolower(confirm) == 'n')
            {
                cout << "\nПрекратяване на въвеждането на конфигурации.\n";
                break;
            }
        }
    }
}

void add_configuration(Computer configurations[], int& present_configurations_count, string& processor_manufacturer,
    string& processor_model, string& computer_id, string& computer_brand, string& computer_model, string& available_status,
    double& processor_frequency, double& computer_ram, double& computer_price, int& processor_cores)
{
    cout << endl << "===== ИНФОРМАЦИЯ ЗА ПРОЦЕСОР =====" << endl;
    read_precessor_data(processor_manufacturer, processor_model, processor_frequency, processor_cores);
    Processor processor = { processor_manufacturer, processor_model, processor_frequency, processor_cores };

    cout << endl << "===== ИНФОРМАЦИЯ ЗА КОМПЮТЪР =====" << endl;
    read_computer_data(computer_id, computer_brand, computer_model, computer_ram, computer_price, available_status, configurations, present_configurations_count);
    Computer computer = { computer_id, computer_brand, computer_model, processor, computer_ram, computer_price, available_status };

    configurations[present_configurations_count++] = computer;

    cout << "\nКонфигурацията е добавена успешно!\n";
}

void print_all_configurations(Computer configurations[], int present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        print_configuration(configurations[i]);
    }
}

void process_print_configurations_by_brand_request(Computer configurations[], int present_configurations_count)
{
    cout << "===== ИЗВЕЖДАНЕ НА КОНФИГУРАЦИИТЕ ОТ ДАДЕНА МАРКА =====\n\n";

    if (present_configurations_count == 0)
    {
        cout << "Няма запазени конфигурации.\n";
        return;
    }

    string brand;
    do
    {
        cout << "Въведете марка, за която искате да намерите конфигурации: ";
        getline(cin, brand);
    } while (is_string_empty(brand));
    
    print_configurations_by_brand(configurations, present_configurations_count, brand);
}

bool is_configurations_count_valid(int configurations_count, int present_configurations_count)
{
    return configurations_count > 0 &&
        MAX_NUMBER_OF_CONFIGURATIONS - present_configurations_count >= configurations_count;
}

void read_precessor_data(string& manufacturer, string& model, double& frequency, int& cores)
{   
    do
    {
        cout << "Въведете производител на процесора: ";
        getline(cin, manufacturer);
    } while (is_string_empty(manufacturer));
    
    do
    {
        cout << "Въведете модел на процесора: ";
        getline(cin, model);
    } while (is_string_empty(model));
    
    cout << "Въведете честота на процесора (GHz): ";
    read_valid_double_value(frequency);
    while (frequency <= 0)
    {
        cout << "Честотата трябва да е по-голяма от 0.\nВъведете честота на процесора (GHz): ";
        read_valid_double_value(frequency);
    }

    cout << "Въведете брой ядра на процесора: ";
    read_valid_integer_value(cores);
    while (cores <= 0)
    {
        cout << "Броят ядра трябва да е по-голям от 0.\nВъведете брой ядра на процесора: ";
        read_valid_integer_value(cores);
    }
}

void read_computer_data(string& id, string& brand, string& model, double& ram, double& price, string& available_status, Computer configurations[], int present_configurations_count)
{
    do
    {
        cout << "Въведете сериен номер на компютъра: ";
        getline(cin, id);
        if (configuration_exists_by_id(id, configurations, present_configurations_count))
        {
            cout << "Вече съществува конфигурация с този сериен номер!\n";
        }
    } while (is_string_empty(id) || configuration_exists_by_id(id, configurations, present_configurations_count));
    
    do
    {
        cout << "Въведете марка на компютъра: ";
        getline(cin, brand);
    } while (is_string_empty(brand));
    
    do
    {
        cout << "Въведете модел на компютъра: ";
        getline(cin, model);
    } while (is_string_empty(model));
    
    cout << "Въведете RAM на компютъра (GB): ";
    read_valid_double_value(ram);
    while (ram <= 0)
    {
        cout << "RAM стойността трябва да е по-голяма от 0.\nВъведете RAM на компютъра (GB): ";
        read_valid_double_value(ram);
    }

    cout << "Въведете цена на компютъра: ";
    read_valid_double_value(price);
    while (price <= 0)
    {
        cout << "Цената трябва да е по-голяма от 0.\nВъведете цена на компютъра: ";
        read_valid_double_value(price);
    }

    do
    {
        printf("Наличен статус на конфигурацията (%s/%s): ", CONFIGURATION_IN_SELL_STATUS.c_str(), CONFIGURATION_SOLD_STATUS.c_str());
        getline(cin, available_status);
    } while (available_status != CONFIGURATION_IN_SELL_STATUS && available_status != CONFIGURATION_SOLD_STATUS);
}

void process_print_all_configurations_request(Computer configurations[], int present_configurations_count)
{
    printf("===== ИНФОРМАЦИЯ ЗА ВСИЧКИ КОНФИГУРАЦИИ (%d) =====\n", present_configurations_count);

    if (present_configurations_count == 0)
    {
        cout << "\nНяма запазени конфигурации." << endl;
        return;
    }

    print_all_configurations(configurations, present_configurations_count);
}

void print_configurations_by_brand(Computer configurations[], int present_configurations_count, string& brand)
{
    bool is_brand_existing = false;
    for (int i = 0; i < present_configurations_count; i++)
    {
        Computer computer = configurations[i];
        if (compare_strings_case_insensitive(computer.brand, brand))
        {
            print_configuration(configurations[i]);
            is_brand_existing = true;
        }
    }
    if (!is_brand_existing) printf("\nКонфигурации с марката %s не са намерени.\n", brand.c_str());
}

void read_valid_integer_value(int& value)
{
    while (true) {
        if (cin >> value) {
            cin.clear();
            cin.ignore(LLONG_MAX, '\n');
            break;
        }
        else {
            cout << "Въведете валидна числена стойност: ";
            cin.clear();
            cin.ignore(LLONG_MAX, '\n');
        }
    }
}

void read_valid_double_value(double& value)
{
    while (true) {
        if (cin >> value) {
            cin.clear();
            cin.ignore(LLONG_MAX, '\n');
            break;
        }
        else {
            cout << "Въведете валидна числена стойност: ";
            cin.clear();
            cin.ignore(LLONG_MAX, '\n');
        }
    }
}

bool configuration_exists_by_id(string& id, Computer configurations[], int present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0) return true;
    }
    return false;
}

double find_max_processor_frequency(Computer configurations[], int present_configurations_count)
{
    double max = configurations[0].processor.frequency;
    for (int i = 1; i < present_configurations_count; i++)
    {
        if (configurations[i].processor.frequency > max) max = configurations[i].processor.frequency;
    }
    return max;
}

void print_configuraions_by_processor_frequency(Computer configurations[], int present_configurations_count, double processor_frequency)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].processor.frequency == processor_frequency) print_configuration(configurations[i]);
    }
}

void print_configurations_with_highest_processor_frequency(Computer configurations[], int present_configurations_count)
{
    cout << "===== ИЗВЕЖДАНЕ НА КОНФИГУРАЦИИТЕ С НАЙ-ГОЛЯМА ТАКТОВА ЧЕСТОТА НА ПРОЦЕСОРА =====\n";

    if (present_configurations_count == 0)
    {
        cout << "\nНяма запазени конфигурации.\n";
        return;
    }

    double highest_processor_frequency = find_max_processor_frequency(configurations, present_configurations_count);
    print_configuraions_by_processor_frequency(configurations, present_configurations_count, highest_processor_frequency);
}

void update_configuration(Computer configurations[], int present_configurations_count)
{
    cout << "===== АКТУАЛИЗИРАНЕ НА КОНФИГУРАЦИЯ ======\n\n";
    
    if (present_configurations_count == 0)
    {
        cout << "Няма запазени конфигурации.\n";
        return;
    }
    
    string id;
    do
    {
        cout << "Въведете сериен номер на конфигурацията за актуализиране: ";
        getline(cin, id);
    } while (is_string_empty(id));

    if (!configuration_exists_by_id(id, configurations, present_configurations_count))
    {
        cout << "\nНе съществува конфигурация с този сериен номер!\n";
        return;
    }

    if (!is_configuration_available(id, configurations, present_configurations_count))
    {
        cout << "\nКонфигурацията е продадена и не може да бъде редактирана!\n";
        return;
    }

    printf("\nКонфигурация със сериен номер %s е намерена успешно!\n\nДетайли за конфигурация:\n", id.c_str());
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);
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

        system("cls");
        switch (update_option)
        {
        case UPDATE_CONFIGURATION_ID_CHOICE:
            update_configuration_id(id, configurations, present_configurations_count);
            break;
        case UPDATE_CONFIGURATION_BRAND_CHOICE:
            update_configuration_brand(id, configurations, present_configurations_count);
            break;
        case UPDATE_CONFIGURATION_MODEL_CHOICE:
            update_configuration_model(id, configurations, present_configurations_count);
            break;
        case UPDATE_CONFIGURATION_RAM_CHOICE:
            update_configuration_ram(id, configurations, present_configurations_count);
            break;
        case UPDATE_CONFIGURATION_PRICE_CHOICE:
            update_configuration_price(id, configurations, present_configurations_count);
            break;
        case UPDATE_CONFIGURATION_AVAILABILITY_STATUS_CHOICE:
            update_configuration_status(id, configurations, present_configurations_count);
            break;
        case UPDATE_CONFIGURATION_PROCESSOR_CHOICE:
            update_processor(id, configurations, present_configurations_count);
            break;
        }
    } while (update_option != EXIT_FROM_CONFIGURATION_UPDATE_MENU_CHOICE);
}

void update_configuration_id(string& old_id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА СЕРИЕН НОМЕР НА КОНФИГУРАЦИЯ =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(old_id, configurations, present_configurations_count);
    print_configuration(computer);
    cout << endl;

    string updated_id;
    do
    {
        cout << "Въведете нов сериен номер на кофигурацията: ";
        getline(cin, updated_id);
    } while (is_string_empty(updated_id));

    if (configuration_exists_by_id(updated_id, configurations, present_configurations_count))
    {
        cout << "\nВече съществува конфигурация с този сериен номер.\n";
        return;
    }

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(old_id) == 0) 
        {
            configurations[i].id = updated_id;
            old_id = updated_id;
            cout << "\nСерийният номер е обновен успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

void update_configuration_brand(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА МАРКА НА КОНФИГУРАЦИЯ =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);
    cout << endl;

    string new_brand;
    do
    {
        cout << "Въведете нова марка на конфигурацията: ";
        getline(cin, new_brand);
    } while (is_string_empty(new_brand));

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].brand = new_brand;
            cout << "\nМарката е обновена успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

void update_configuration_model(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА МОДЕЛ НА КОНФИГУРАЦИЯ =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);
    cout << endl;

    string new_model;
    do
    {
        cout << "Въведете нов модел на конфигурацията: ";
        getline(cin, new_model);
    } while (is_string_empty(new_model));

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].model = new_model;
            cout << "\nМоделът е обновен успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

void update_configuration_ram(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА RAM СТОЙНОСТ НА КОНФИГУРАЦИЯ =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);

    cout << "\nВъведете нова RAM стойност на конфигурацията: ";
    double new_ram;
    read_valid_double_value(new_ram);
    while (new_ram <= 0)
    {
        cout << "RAM стойността трябва да е по-голяма от 0.\nВъведете нова RAM стойност на конфигурацията: ";
        read_valid_double_value(new_ram);
    }

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].ram = new_ram;
            cout << "\nRAM стойността е обновена успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

void update_configuration_price(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА ЦЕНА НА КОНФИГУРАЦИЯ =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);

    cout << "\nВъведете нова цена на конфигурацията: ";
    double new_price;
    read_valid_double_value(new_price);
    while (new_price <= 0)
    {
        cout << "Цената трябва да е по-голяма от 0.\nВъведете нова цена на конфигурацията: ";
        read_valid_double_value(new_price);
    }

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].price = new_price;
            cout << "\nЦената е обновена успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

void update_configuration_status(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА НАЛИЧЕН СТАТУС НА КОНФИГУРАЦИЯ =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);

    printf("\nВъведете нов наличен статус на конфигурацията (%s/%s): ", CONFIGURATION_IN_SELL_STATUS.c_str(), CONFIGURATION_SOLD_STATUS.c_str());
    string new_available_status;
    getline(cin, new_available_status);
    while (new_available_status != CONFIGURATION_IN_SELL_STATUS && new_available_status != CONFIGURATION_SOLD_STATUS)
    {
        printf("Наличен статус на конфигурацията (%s/%s): ", CONFIGURATION_IN_SELL_STATUS.c_str(), CONFIGURATION_SOLD_STATUS.c_str());
        getline(cin, new_available_status);
    }

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].available_status = new_available_status;
            cout << "\nНаличният статус е обновен успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

void update_processor(string& configuration_id, Computer configurations[], int present_configurations_count)
{
    cout << "===== АКТУАЛИЗИРАНЕ НА ПРОЦЕСОР =====\n";

    int update_option;
    do
    {
        printf("\nВъведете %d, за да промените производителя на процесора.\nВъведете %d, за да промeните модела на процесора.\nВъведете %d, за да промените честотата на процесора.\nВъведете %d, за да промените броят ядра на процесора.\nВъведете %d, за да прекратите корекцията на данни на процесора.\n",
            UPDATE_PROCESSOR_MANUFACTURER_CHOICE, UPDATE_PROCESSOR_MODEL_CHOICE, UPDATE_PROCESSOR_FREQUENCY_CHOICE, UPDATE_PROCESSOR_CORES_COUNT_CHOICE, EXIT_FROM_UPDATE_PROCESSOR_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", UPDATE_PROCESSOR_MANUFACTURER_CHOICE, EXIT_FROM_UPDATE_PROCESSOR_MENU_CHOICE);
            read_valid_integer_value(update_option);
        } while (update_option < UPDATE_PROCESSOR_MANUFACTURER_CHOICE || update_option > EXIT_FROM_UPDATE_PROCESSOR_MENU_CHOICE);

        system("cls");
        switch (update_option)
        {
        case UPDATE_PROCESSOR_MANUFACTURER_CHOICE:
            update_processor_manufacturer(configuration_id, configurations, present_configurations_count);
            break;
        case UPDATE_PROCESSOR_MODEL_CHOICE:
            update_processor_model(configuration_id, configurations, present_configurations_count);
            break;
        case UPDATE_PROCESSOR_FREQUENCY_CHOICE:
            update_configuration_frequency(configuration_id, configurations, present_configurations_count);
            break;
        case UPDATE_PROCESSOR_CORES_COUNT_CHOICE:
            update_configuration_cores(configuration_id, configurations, present_configurations_count);
            break;
        }
    } while (update_option != EXIT_FROM_UPDATE_PROCESSOR_MENU_CHOICE);
}

void update_processor_manufacturer(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА ПРОИЗВОДИТЕЛ НА ПРОЦЕСОР =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);
    cout << endl;

    string new_manufacturer;
    do
    {
        cout << "Въведете нов производител на процесора: ";
        getline(cin, new_manufacturer);
    } while (is_string_empty(new_manufacturer));

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].processor.manufacturer = new_manufacturer;
            cout << "\nПроизводителят е обновен успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

void update_processor_model(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА МОДЕЛ НА ПРОЦЕСОР =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);
    cout << endl;

    string new_model;    
    do
    {
        cout << "Въведете нов модел на процесора: ";
        getline(cin, new_model);
    } while (is_string_empty(new_model));

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].processor.model = new_model;
            cout << "\nМоделът е обновен успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

void update_configuration_frequency(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА ЧЕСТОТА НА ПРОЦЕСОР =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);

    cout << "\nВъведете нова честота на процесора: ";
    double new_frequency;
    read_valid_double_value(new_frequency);
    while (new_frequency <= 0)
    {
        cout << "Честотата трябва да е по-голяма от 0.\nВъведете нова честота на процесора: ";
        read_valid_double_value(new_frequency);
    }

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].processor.frequency = new_frequency;
            cout << "\nЧестотата е обновена успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

void update_configuration_cores(string& id, Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОМЯНА НА БРОЙ ЯДРА НА ПРОЦЕСОР =====\n\nДетайли за конфигурация:\n";
    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    print_configuration(computer);

    cout << "\nВъведете нов брой ядра на процесора: ";
    int new_cores;
    read_valid_integer_value(new_cores);
    while (new_cores <= 0)
    {
        cout << "Броят ядра трябва да е по-голям от 0.\nВъведете нов брой ядра на процесора: ";
        read_valid_integer_value(new_cores);
    }

    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].processor.cores = new_cores;
            cout << "\nБроят ядра е обновен успешно!\n\nОбновена конфигурация:\n";
            print_configuration(configurations[i]);
            return;
        }
    }
}

bool is_configuration_available(string& id, Computer configurations[], int present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0) return configurations[i].available_status == CONFIGURATION_IN_SELL_STATUS;
    }
    return false;
}

void sell_configuration(Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОДАЖБА НА КОНФИГУРАЦИЯ =====\n";

    if (present_configurations_count == 0)
    {
        cout << "\nНяма запазени конфигурации.\n";
        return;
    }

    int menu_choice;
    do
    {
        printf("\nВъведете %d, за да осъществите продажба по сериен номер.\nВъведете %d, за да осъществите продажба по определени характеристики.\nВъведете %d, за да напуснете продажното меню.\n",
            SELL_BY_SERIAL_NUMBER_CHOICE, SELL_BY_REQUIREMENTS_CHOICE, EXIT_FROM_SELLING_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", SELL_BY_SERIAL_NUMBER_CHOICE, EXIT_FROM_SELLING_MENU_CHOICE);
            read_valid_integer_value(menu_choice);
        } while (menu_choice < SELL_BY_SERIAL_NUMBER_CHOICE || menu_choice > EXIT_FROM_SELLING_MENU_CHOICE);

        system("cls");
        switch (menu_choice)
        {
        case SELL_BY_SERIAL_NUMBER_CHOICE:
            sell_configuration_by_id(configurations, present_configurations_count);
            break;

        case SELL_BY_REQUIREMENTS_CHOICE:
            sell_configuration_by_requirements(configurations, present_configurations_count);
            break;
        }
    } while (menu_choice != EXIT_FROM_SELLING_MENU_CHOICE);
}

void sell_configuration_by_id(Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОДАЖБА НА КОНФИГУРАЦИЯ ПО СЕРИЕН НОМЕР =====\n\n";

    string id;
    do
    {
        cout << "Въведете сериен номер на компютъра: ";
        getline(cin, id);
    } while (is_string_empty(id));
    

    if (!configuration_exists_by_id(id, configurations, present_configurations_count))
    {
        cout << "\nНе съществува конфигурация с този сериен номер!\n";
        return;
    }

    if (!is_configuration_available(id, configurations, present_configurations_count))
    {
        cout << "\nКонфигурацията не е налична за продажба!\n";
        return;
    }

    Computer computer = get_configuration_by_id(id, configurations, present_configurations_count);
    printf("\nКонфигурация със сериен номер %s е намерена успешно!\n\nДетайли за конфигурация:\n", id.c_str());
    print_configuration(computer);

    cout << "\nВъведете цена за покупка: ";
    double price_to_pay;
    read_valid_double_value(price_to_pay);
    while (price_to_pay <= 0)
    {
        cout << "Цената за покупка трябва да е по-голяма от 0.\nВъведете цена за покупка: ";
        read_valid_double_value(price_to_pay);
    }

    if (price_to_pay < computer.price)
    {
        printf("\nНедостатъчна сума пари!\nНеобходима сума: %.2f лв.\n", computer.price - price_to_pay);
        return;
    }

    change_configuration_availability_status(id, configurations, present_configurations_count);
    double money_change = price_to_pay - computer.price;
    printf("\nКонфигурацията е успешно продадена!\nРесто: %.2f лв.\n", money_change);
}

Computer get_configuration_by_id(string& id, Computer configurations[], int present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0) return configurations[i];
    }
}

void print_configuration(Computer& computer)
{
    Processor processor = computer.processor;
    printf("\nСериен номер: %s\nМарка: %s\nМодел: %s\nRAM памет: %.2f GB\nПроцесор:\n\tПроизводител: %s\n\tМодел: %s\n\tТактова честота: %.2f GHz\n\tБрой ядра: %d\nЦена: %.2f лв.\nНаличен статус: %s\n",
        computer.id.c_str(), computer.brand.c_str(), computer.model.c_str(), computer.ram, processor.manufacturer.c_str(), processor.model.c_str(), processor.frequency, processor.cores, computer.price, computer.available_status.c_str());
}

void change_configuration_availability_status(string& id, Computer configurations[], int present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].id.compare(id) == 0)
        {
            configurations[i].available_status = CONFIGURATION_SOLD_STATUS;
            return;
        }
    }
}

void sell_configuration_by_requirements(Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОДАЖБА НА КОНФИГУРАЦИЯ ПО ХАРАКТЕРИСТИКИ =====\n\n* Ако желаете да пропуснете характерстика, оставете съответното поле празно.\n\n";

    string processor_manufacturer, processor_model, processor_frequency, processor_cores, computer_brand, computer_model, computer_ram, computer_price;
    read_precessor_selling_data(processor_manufacturer, processor_model, processor_frequency, processor_cores);
    read_computer_selling_data(computer_brand, computer_model, computer_ram, computer_price);

    Computer found_configurations[MAX_NUMBER_OF_CONFIGURATIONS];
    int found_configurations_count = 0;
    int selected_features, actual_features;

    find_computers_with_requirements(configurations, present_configurations_count, found_configurations,
        found_configurations_count, processor_manufacturer, processor_model, processor_frequency,
        processor_cores, computer_brand, computer_model, computer_ram, computer_price, selected_features, actual_features);

    if (found_configurations_count == 0)
    {
        cout << "\nНе са намерени конфигурации отговарящи на посочените изисквания.\n";
        return;
    }
    
    cout << "\nПодходящи конфигурации:\n";
    print_all_configurations(found_configurations, found_configurations_count);

    cout << "\nЖелаете ли да осъществите продажба? (Y/N): ";
    char confirm;
    cin >> confirm;
    while (tolower(confirm) != 'y' && tolower(confirm) != 'n')
    {
        cout << "Въведете Y, за да влезете в режим на продажба.\nВъведете N, за да откажете осъществяването на продажба.\nВъведете избор (Y/N): ";
        cin >> confirm;
    }

    if (tolower(confirm) == 'y')
    {
        cout << endl;
        cin.clear();
        cin.ignore();
        sell_configuration_by_id(configurations, present_configurations_count);
    }
    else cout << "\nПродажба по въведени харакетеристики бе отказана.\n";
}

void read_precessor_selling_data(string& manufacturer, string& model, string& frequency, string& cores)
{
    cout << "Въведете желан производител на процесора: ";
    getline(cin, manufacturer);
    cout << "Въведете желан модел на процесора: ";
    getline(cin, model);
    cout << "Въведете желана минимална честота на процесора: ";
    getline(cin, frequency);
    cout << "Въведете желан минимален брой ядра на процесора: ";
    getline(cin, cores);
}

void read_computer_selling_data(string& brand, string& model, string& ram, string& price)
{
    cout << "Въведете желана марка на компютъра: ";
    getline(cin, brand);
    cout << "Въведете желан модел на компютъра: ";
    getline(cin, model);
    cout << "Въведете желана минимална RAM на компютъра: ";
    getline(cin, ram);
    cout << "Въведете максимална цена на компютъра: ";
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

void find_computers_with_requirements(Computer configurations[], int present_configurations_count, Computer found_configurations[],
    int& found_configurations_count, string& processor_manufacturer, string& processor_model, string& processor_frequency,
    string& processor_cores, string& computer_brand, string& computer_model, string& computer_ram, string& computer_price,
    int& selected_features, int& actual_features)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        selected_features = 0;
        actual_features = 0;
        Computer computer = configurations[i];

        if (!is_string_empty(processor_manufacturer))
        {
            selected_features++;
            if (compare_strings_case_insensitive(computer.processor.manufacturer, processor_manufacturer)) actual_features++;
        }

        if (!is_string_empty(processor_model))
        {
            selected_features++;
            if (compare_strings_case_insensitive(computer.processor.model, processor_model)) actual_features++;
        }

        if (!is_string_empty(processor_frequency))
        {
            selected_features++;
            if (computer.processor.frequency >= stod(processor_frequency)) actual_features++;
        }

        if (!is_string_empty(processor_cores))
        {
            selected_features++;
            if (computer.processor.cores >= stoi(processor_cores)) actual_features++;
        }

        if (!is_string_empty(computer_brand))
        {
            selected_features++;
            if (compare_strings_case_insensitive(computer.brand, computer_brand)) actual_features++;
        }

        if (!is_string_empty(computer_model))
        {
            selected_features++;
            if (compare_strings_case_insensitive(computer.model, computer_model)) actual_features++;
        }

        if (!is_string_empty(computer_ram))
        {
            selected_features++;
            if (computer.ram >= stod(computer_ram)) actual_features++;
        }

        if (!is_string_empty(computer_price))
        {
            selected_features++;
            if (computer.price <= stod(computer_price)) actual_features++;
        }

        if (selected_features == actual_features) found_configurations[found_configurations_count++] = computer;
    }
}

void make_configurations_audit(Computer configurations[], int present_configurations_count)
{
    cout << "===== ОДИТ НА КОНФИГУРАЦИИ =====\n";

    if (present_configurations_count == 0)
    {
        cout << "\nНяма запазени конфигурации.\n";
        return;
    }

    int menu_choice;
    do
    {
        printf("\nВъведете %d, за да изведете всички конфигурации, които са в продажба, сортирани по сериен номер.\nВъведете %d, за да изведете всички конфигурации с даден модел процесор и RAM памет, сортирани по цена от най-скъпия към най-евтиния.\nВъведете %d, за да изведете продадените конфигурации, сортирани по модел на процесора.\nВъведете %d, за да напуснете одитното меню.\n",
            PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_ID_CHOICE, PRINT_CONFIGURATIONS_BY_BRAND_AND_RAM_SORTED_BY_PRICE_DESC_CHOICE, PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_PROCESSOR_MODEL_CHOICE, EXIT_FROM_AUDIT_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_ID_CHOICE, EXIT_FROM_AUDIT_MENU_CHOICE);
            read_valid_integer_value(menu_choice);
        } while (menu_choice < PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_ID_CHOICE || menu_choice > EXIT_FROM_AUDIT_MENU_CHOICE);

        system("cls");
        switch (menu_choice)
        {
        case PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_ID_CHOICE:
            print_available_configurations_sorted_by_id(configurations, present_configurations_count);
            break;

        case PRINT_CONFIGURATIONS_BY_BRAND_AND_RAM_SORTED_BY_PRICE_DESC_CHOICE:
            print_configurations_by_brand_and_ram_sorted_by_price_desc(configurations, present_configurations_count);
            break;

        case PRINT_AVAILABLE_CONFIGURATIONS_SORTED_BY_PROCESSOR_MODEL_CHOICE:
            print_unavailable_configurations_sorted_by_processor_model(configurations, present_configurations_count);
            break;
        }
    } while (menu_choice != EXIT_FROM_AUDIT_MENU_CHOICE);
}

void print_available_configurations_sorted_by_id(Computer configurations[], int present_configurations_count)
{
    cout << "===== НАЛИЧНИ КОНФИГУРАЦИИ, СОРТИРАНИ ПО СЕРИЕН НОМЕР =====\n";
    if (present_configurations_count == 0)
    {
        cout << "\nНяма запазени конфигурации.\n";
        return;
    }

    if (!search_for_configurations_by_availability_status(configurations, present_configurations_count, CONFIGURATION_IN_SELL_STATUS))
    {
        cout << "\nНяма налични конфигурации.\n";
        return;
    }

    Computer sorted_configurations[MAX_NUMBER_OF_CONFIGURATIONS];
    sort_configurations_by_id(configurations, present_configurations_count, sorted_configurations);
    print_configurations_by_availability_status(sorted_configurations, present_configurations_count, CONFIGURATION_IN_SELL_STATUS);
}

bool search_for_configurations_by_availability_status(Computer configurations[], int present_configurations_count, string available_status)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].available_status == available_status) return true;
    }
    return false;
}

void sort_configurations_by_id(Computer configurations[], int present_configurations_count, Computer sorted_configurations[])
{
    for (int i = 0; i < present_configurations_count; i++) sorted_configurations[i] = configurations[i];

    for (int i = 0; i < present_configurations_count - 1; i++)
    {
        bool swapped = false;
        for (int j = 0; j < present_configurations_count - 1 - i; j++)
        {
            Computer first = sorted_configurations[j];
            Computer second = sorted_configurations[j + 1];
            int min_id_length = find_min_number(first.id.length(), second.id.length());

            for (int k = 0; k < min_id_length; k++)
            {
                if (first.id[k] > second.id[k])
                {
                    Computer temp = sorted_configurations[j];
                    sorted_configurations[j] = sorted_configurations[j + 1];
                    sorted_configurations[j + 1] = temp;
                    swapped = true;
                    break;
                }
            }
        }
        if (!swapped) break;
    }
}

void sort_configurations_by_price_desc(Computer configurations[], int present_configurations_count, Computer sorted_configurations[])
{
    for (int i = 0; i < present_configurations_count; i++) sorted_configurations[i] = configurations[i];

    for (int i = 0; i < present_configurations_count - 1; i++)
    {
        bool swapped = false;
        for (int j = 0; j < present_configurations_count - 1 - i; j++)
        {
            if (sorted_configurations[j].price < sorted_configurations[j + 1].price)
            {
                Computer temp = sorted_configurations[j];
                sorted_configurations[j] = sorted_configurations[j + 1];
                sorted_configurations[j + 1] = temp;
                swapped = true;
            }
        }
        if (!swapped) break;
    }
}

int find_min_number(int first, int second)
{
    if (first < second) return first;
    return second;
}

void print_configurations_by_availability_status(Computer configurations[], int present_configurations_count, string available_status)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (configurations[i].available_status == available_status) print_configuration(configurations[i]);
    }
}

bool configuration_exists_by_processor_model_and_ram(Computer configurations[], int present_configurations_count, string& processor_model, double ram)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (compare_strings_case_insensitive(configurations[i].processor.model, processor_model) && configurations[i].ram == ram) return true;
    }
    return false;
}

void print_configurations_by_brand_and_ram(Computer configurations[], int present_configurations_count, string& processor_model, double ram)
{
    for (int i = 0; i < present_configurations_count; i++)
    {
        if (compare_strings_case_insensitive(configurations[i].processor.model, processor_model) && configurations[i].ram == ram) print_configuration(configurations[i]);
    }
}

void print_configurations_by_brand_and_ram_sorted_by_price_desc(Computer configurations[], int present_configurations_count)
{
    cout << "===== КОНФИГУРАЦИИ С ДАДЕН МОДЕЛ ПРОЦЕСОР И RAM ПАМЕТ, СОРТИРАНИ ОТ НАЙ-СКЪПАТА КЪМ НАЙ-ЕВТИНАТА =====\n\n";

    if (present_configurations_count == 0)
    {
        cout << "\nНяма запазени конфигурации.\n";
        return;
    }

    string processor_model;
    do
    {
        cout << "Въведете желан модел процесор: ";
        getline(cin, processor_model);
    } while (is_string_empty(processor_model));

    cout << "Въведете RAM на компютъра (GB): ";
    double ram;
    read_valid_double_value(ram);
    while (ram <= 0)
    {
        cout << "RAM стойността трябва да е по-голяма от 0.\nВъведете RAM на компютъра (GB): ";
        read_valid_double_value(ram);
    }

    if (!configuration_exists_by_processor_model_and_ram(configurations, present_configurations_count, processor_model, ram))
    {
        printf("\nНе са намерени конфигурации с модел на процесора %s и RAM %.2f GB.\n", processor_model.c_str(), ram);
        return;
    }

    Computer sorted_configurations[MAX_NUMBER_OF_CONFIGURATIONS];
    sort_configurations_by_price_desc(configurations, present_configurations_count, sorted_configurations);
    print_configurations_by_brand_and_ram(sorted_configurations, present_configurations_count, processor_model, ram);
}

void sort_configurations_by_processor_model(Computer configurations[], int present_configurations_count, Computer sorted_configurations[])
{
    for (int i = 0; i < present_configurations_count; i++) sorted_configurations[i] = configurations[i];

    for (int i = 0; i < present_configurations_count - 1; i++)
    {
        bool swapped = false;
        for (int j = 0; j < present_configurations_count - 1 - i; j++)
        {
            Processor first = sorted_configurations[j].processor;
            Processor second = sorted_configurations[j + 1].processor;
            int min_model_length = find_min_number(first.model.length(), second.model.length());

            for (int k = 0; k < min_model_length; k++)
            {
                if (first.model[k] > second.model[k])
                {
                    Computer temp = sorted_configurations[j];
                    sorted_configurations[j] = sorted_configurations[j + 1];
                    sorted_configurations[j + 1] = temp;
                    swapped = true;
                    break;
                }
            }
        }
        if (!swapped) break;
    }
}

void print_unavailable_configurations_sorted_by_processor_model(Computer configurations[], int present_configurations_count)
{
    cout << "===== ПРОДАДЕНИ КОНФИГУРАЦИИ, СОРТИРАНИ ПО МОДЕЛ НА ПРОЦЕСОРА =====\n";

    if (present_configurations_count == 0)
    {
        cout << "\nНяма запазени конфигурации.\n";
        return;
    }

    if (!search_for_configurations_by_availability_status(configurations, present_configurations_count, CONFIGURATION_SOLD_STATUS))
    {
        cout << "\nНяма продадени конфигурации.\n";
        return;
    }

    Computer sorted_configurations[MAX_NUMBER_OF_CONFIGURATIONS];
    sort_configurations_by_processor_model(configurations, present_configurations_count, sorted_configurations);
    print_configurations_by_availability_status(sorted_configurations, present_configurations_count, CONFIGURATION_SOLD_STATUS);
}

void store_configurations_in_file(Computer configurations[], int present_configurations_count)
{
    if (present_configurations_count == 0)
    {
        cout << "Няма конфигурации за записване във файл.\n";
        return;
    }
    fstream configurations_data;
    configurations_data.open("configurations.dat", ios::out);

    if (configurations_data.fail())
    {
        cout << "Грешка в отварянето на файла.";
        return;
    }

    configurations_data.setf(ios::fixed);
    configurations_data.setf(ios::showpoint);
    configurations_data.precision(DIGITS_TO_PRINT_AFTER_DECIMAL_POINT);
    for (int i = 0; i < present_configurations_count; i++)
    {
        Computer computer = configurations[i];
        Processor processor = computer.processor;

        configurations_data
            << "Сериен номер: " << computer.id.c_str()
            << "\nМарка: " << computer.brand.c_str()
            << "\nМодел: " << computer.model.c_str()
            << "\nRAM памет: " << computer.ram << " GB"
            << "\nПроцесор: \n\tПроизводител: " << processor.manufacturer
            << "\n\tМодел: " << processor.model
            << "\n\tТактова честота: " << processor.frequency << " GHz"
            << "\n\tБрой ядра: " << processor.cores
            << "\nЦена: " << computer.price << " лв."
            << "\nНаличен статус: " << computer.available_status << "\n";

        if (i != present_configurations_count - 1) configurations_data << "\n";
    }
    configurations_data.close();
    printf("%d конфигурации бяха записани във файл.\n", present_configurations_count);
}

void read_configurations_from_file(Computer configurations[], int& present_configurations_count)
{
    fstream configurations_data;
    configurations_data.open("configurations.dat", ios::in);

    if (configurations_data.fail())
    {
        cout << "Няма запазени конфигурации, които да бъдат прочетени от файл.\n\n";
        return;
    }

    while (!configurations_data.eof())
    {
        Computer computer;
        Processor processor;

        string configuration_id, configuration_brand, configuration_model, configuration_ram, processor_manufacturer,
            processor_model, processor_frequency, processor_cores, configuration_price, configuration_availability;

        getline(configurations_data, configuration_id);
        computer.id = configuration_id.substr(14, configuration_id.length() - 14);

        getline(configurations_data, configuration_brand);
        computer.brand = configuration_brand.substr(7, configuration_brand.length() - 7);

        getline(configurations_data, configuration_model);
        computer.model = configuration_model.substr(7, configuration_model.length() - 7);

        getline(configurations_data, configuration_ram);
        computer.ram = stod(configuration_ram.substr(11, configuration_ram.length() - 14));

        getline(configurations_data, processor_manufacturer);
        getline(configurations_data, processor_manufacturer);
        processor.manufacturer = processor_manufacturer.substr(15, processor_manufacturer.length() - 15);

        getline(configurations_data, processor_model);
        processor.model = processor_model.substr(8, processor_model.length() - 8);

        getline(configurations_data, processor_frequency);
        processor.frequency = stod(processor_frequency.substr(18, processor_frequency.length() - 22));

        getline(configurations_data, processor_cores);
        processor.cores = stoi(processor_cores.substr(12, processor_cores.length() - 12));

        getline(configurations_data, configuration_price);
        computer.price = stod(configuration_price.substr(6, configuration_price.length() - 10));

        getline(configurations_data, configuration_availability);
        computer.available_status = configuration_availability.substr(16, configuration_availability.length() - 16);

        configurations_data.ignore();

        computer.processor = processor;
        configurations[present_configurations_count++] = computer;
    }

    configurations_data.close();
    printf("%d конфигурации бяха прочетени от файл.\n\n", present_configurations_count);
}

bool is_string_empty(string& input)
{
    if (input.empty()) return true;
    for (int i = 0; i < input.size(); i++)
    {
        if (input[i] != ' ' && input[i] != '\t') return false;
    }
    return true;
}
