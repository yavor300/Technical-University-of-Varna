#include <iostream>
#include <string>
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
const int EXIT_FROM_MENU_CHOICE = 4;

/*
* Prototypes
*/
void process_add_configuration_request(int& present_configurations_count, Computer configurations[]);
bool is_configurations_count_valid(int configurations_count, int& present_configurations_count);
void read_precessor_data(string& manufacturer, string& model, double& frequency, int& cores);
void read_computer_data(string& id, string& brand, string& model, double& ram, double& price, string& status);
Processor create_processor(string& processor_manufacturer, string& processor_model, double& processor_frequency, int& processor_cores);
Computer create_computer(string& id, string& brand, string& model, double& ram, double& price, string& status, Processor processor);
void print_configurations(Computer configurations[], int& present_configurations_count, int& configurations_count_to_print);
void sort_configurations_by_processor_frequency_desc(Computer configurations[], int& present_configurations_count, Computer sorted_configurations[]);
void read_integer_value(string message, int& value);

int main()
{
    setlocale(LC_ALL, "BG");

    int choice, present_configurations_count(INITIAL_CONFIGURATIONS_COUNT);
    Computer configurations[MAX_NUMBER_OF_CONFIGURATIONS];

    do
    {
        printf("Въведете %d, за да добавите нова конфигурация.\nВъведете %d, за да изведете всички конфигурации.\nВъведете %d, за да изведете желан брой конфигурации с най-голяма тактова честота на процесора.\nВъведете %d, за да спрете програмата.\n",
            ADD_NEW_CONFIGURATION_CHOICE, PRINT_ALL_CONFIGURATIONS_CHOICE, PRINT_SORTED_CONFIGURATIONS_BY_PROCESSOR_FREQUENCY_DESC_CHOICE, EXIT_FROM_MENU_CHOICE);
        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", ADD_NEW_CONFIGURATION_CHOICE, EXIT_FROM_MENU_CHOICE);
            cin >> choice;
        } while (choice < ADD_NEW_CONFIGURATION_CHOICE || choice > EXIT_FROM_MENU_CHOICE);

        switch (choice)
        {
        case ADD_NEW_CONFIGURATION_CHOICE:
            process_add_configuration_request(present_configurations_count, configurations);
            break;
        case PRINT_ALL_CONFIGURATIONS_CHOICE:
            print_configurations(configurations, present_configurations_count, present_configurations_count);
            break;
        case PRINT_SORTED_CONFIGURATIONS_BY_PROCESSOR_FREQUENCY_DESC_CHOICE:
            Computer sorted_configurations[MAX_NUMBER_OF_CONFIGURATIONS];
            int configurations_to_print;

            sort_configurations_by_processor_frequency_desc(configurations, present_configurations_count, sorted_configurations);
            read_integer_value("Въведете брой конфигурации за извеждане: ", configurations_to_print);
            print_configurations(sorted_configurations, present_configurations_count, configurations_to_print);

            break;
        }
    } while (choice != EXIT_FROM_MENU_CHOICE);
}

void process_add_configuration_request(int& present_configurations_count, Computer configurations[])
{
    printf("Въведете брой конфигурации: ");
    int configurations_count;
    cin >> configurations_count;
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
            Processor processor = create_processor(processor_manufacturer, processor_model, processor_frequency, processor_cores);

            cout << endl << "--- ИНФОРМАЦИЯ ЗА КОМПЮТЪР ---" << endl;
            cin.ignore();
            read_computer_data(computer_id, computer_brand, computer_model, computer_ram, computer_price, computer_availability_status);
            Computer computer = create_computer(computer_id, computer_brand, computer_model, computer_ram, computer_price, computer_availability_status, processor);

            configurations[present_configurations_count++] = computer;

            cout << endl << "КОНФИГУРАЦИЯТА Е ДОБАВЕНА УСПЕШНО!" << endl << endl;
        }
    }
}

Processor create_processor(string& processor_manufacturer, string& processor_model, double& processor_frequency, int& processor_cores)
{
    Processor processor;
    processor.manufacturer = processor_manufacturer;
    processor.model = processor_model;
    processor.frequency = processor_frequency;
    processor.cores = processor_cores;
    return processor;
}

Computer create_computer(string& id, string& brand, string& model, double& ram, double& price, string& status, Processor processor)
{
    Computer computer;
    computer.id = id;
    computer.brand = brand;
    computer.model = model;
    computer.ram = ram;
    computer.price = price;
    computer.status = status;
    computer.processor = processor;
    return computer;
}

bool is_configurations_count_valid(int configurations_count, int& present_configurations_count)
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
    cin >> frequency;
    cout << "Въведете брой ядра на процесора: ";
    cin >> cores;
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
    cin >> ram;
    cout << "Въведете цена на компютъра: ";
    cin >> price;
    cin.ignore();
    cout << "Въведете наличен статус на компютъра: ";
    getline(cin, status);
}

void print_configurations(Computer configurations[], int& present_configurations_count, int& configurations_count_to_print)
{
    if (configurations_count_to_print > present_configurations_count)
    {
        cout << "Въведенения брой конфигурации надвишава броят на запазените конфигурации." << endl;
        return;
    }

    cout << endl << "--- ИНФОРМАЦИЯ ЗА ВСИЧКИ КОНФИГУРАЦИИ ---" << endl;
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

void read_integer_value(string message, int& value)
{
    cout << message;
    cin >> value;
}
