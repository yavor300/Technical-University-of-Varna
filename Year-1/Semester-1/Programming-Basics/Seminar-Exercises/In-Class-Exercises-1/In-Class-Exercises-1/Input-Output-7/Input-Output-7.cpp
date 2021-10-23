#include <iostream>
using namespace std;

/*
* Задача 3.8
* Съставете С/C++ програма, която дава възможност за въвеждане на ред от символи и повтарянето им като „ехо” докато се натисне Enter.
*/
void main()
{
    cout << "Enter symbols to be repeated." << endl;
    char symbol;
    do
    {
        cin.get(symbol);
        cout.put(symbol);
    } while (symbol != '\n');
}
