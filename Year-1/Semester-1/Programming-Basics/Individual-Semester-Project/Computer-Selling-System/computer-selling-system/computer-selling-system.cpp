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

int main()
{
    cout << "Hello World!\n";
}
