#include <iostream>
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

struct ComputerConfiguration
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
void process_configration_request(int& present_configurations_count, int configurations[]);
bool is_configurations_count_valid(int configurations_count, int& present_configurations_count);
void printArray(int configurations[], int& present_configurations_count);

int main()
{
    int choice, present_configurations_count(INITIAL_CONFIGURATIONS_COUNT);
    int configurations[MAX_NUMBER_OF_CONFIGURATIONS];

    do
    {
        printf("Press %d to add ne configuration.\nPress %d to exit from the program.\n", ADD_NEW_CONFIGURATION_CHOICE, EXIT_FROM_MENU_CHOICE);

        do
        {
            printf("Enter a valid command number [%d - %d]: ", ADD_NEW_CONFIGURATION_CHOICE, EXIT_FROM_MENU_CHOICE);
            cin >> choice;
        } while (choice < ADD_NEW_CONFIGURATION_CHOICE || choice > EXIT_FROM_MENU_CHOICE);

        switch (choice)
        {
        case 1: process_configration_request(present_configurations_count, configurations); cin >> choice;
        }
    } while (choice != EXIT_FROM_MENU_CHOICE);

    printArray(configurations, present_configurations_count);
}

void process_configration_request(int& present_configurations_count, int configurations[])
{
    printf("Enter configurations count: ");
    int configurations_count;
    cin >> configurations_count;
    if (is_configurations_count_valid(configurations_count, present_configurations_count))
    {
        for (int i = 0; i < configurations_count; i++)
        {
            configurations[present_configurations_count++] = present_configurations_count + 1;
        }
    }
}

bool is_configurations_count_valid(int configurations_count, int& present_configurations_count)
{
    return configurations_count > 0 &&
        MAX_NUMBER_OF_CONFIGURATIONS - present_configurations_count >= configurations_count;
}

void printArray(int configurations[], int& present_configurations_count)
{
    for (int i = 0; i < present_configurations_count; i++)
    {  
        cout << configurations[i] << " ";
    }
}
