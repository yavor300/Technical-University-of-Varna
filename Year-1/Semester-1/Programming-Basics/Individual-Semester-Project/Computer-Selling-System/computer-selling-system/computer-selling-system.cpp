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
const int EXIT_FROM_MENU_CHOICE = 3;

/*
* Prototypes
*/
void process_configration_request(int& present_configurations_count, Computer configurations[]);
bool is_configurations_count_valid(int configurations_count, int& present_configurations_count);
void read_precessor_data(string& manufacturer, string& model, double& frequency, int& cores);
void read_computer_data(string& id, string& brand, string& model, double& ram, double& price, string& status);
Processor create_processor(string& processor_manufacturer, string& processor_model, double& processor_frequency, int& processor_cores);
Computer create_computer(string& id, string& brand, string& model, double& ram, double& price, string& status, Processor processor);

void printArray(Computer configurations[], int& present_configurations_count);

int main()
{
    setlocale(LC_ALL, "BG");

    int choice, present_configurations_count(INITIAL_CONFIGURATIONS_COUNT);
    Computer configurations[MAX_NUMBER_OF_CONFIGURATIONS];

    do
    {
        printf("Въведете %d, за да добавите нова конфигурация.\nВъведете %d, за да спрете програмата.\n", ADD_NEW_CONFIGURATION_CHOICE, EXIT_FROM_MENU_CHOICE);

        do
        {
            printf("Въведете валидна меню опция [%d - %d]: ", ADD_NEW_CONFIGURATION_CHOICE, EXIT_FROM_MENU_CHOICE);
            cin >> choice;
        } while (choice < ADD_NEW_CONFIGURATION_CHOICE || choice > EXIT_FROM_MENU_CHOICE);

        switch (choice)
        {
        case 1: process_configration_request(present_configurations_count, configurations);
        }
    } while (choice != EXIT_FROM_MENU_CHOICE);

    printArray(configurations, present_configurations_count);
}

void process_configration_request(int& present_configurations_count, Computer configurations[])
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
            read_precessor_data(processor_manufacturer, processor_model, processor_frequency, processor_cores);
            Processor processor = create_processor(processor_manufacturer, processor_model, processor_frequency, processor_cores);
            cin.ignore();
            read_computer_data(computer_id, computer_brand, computer_model, computer_ram, computer_price, computer_availability_status);
            Computer computer = create_computer(computer_id, computer_brand, computer_model, computer_ram, computer_price, computer_availability_status, processor);
            configurations[present_configurations_count++] = computer;
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

void printArray(Computer configurations[], int& present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {  
        cout << configurations[i].brand << " ";
    }
}
