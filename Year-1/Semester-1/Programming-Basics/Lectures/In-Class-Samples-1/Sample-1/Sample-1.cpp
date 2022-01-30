#include <iostream>
using namespace std;

void main()
{
    setlocale(LC_ALL, "BG");

    int broj, ed_cena, total;
    
    cout << "Въведи брой закупени продукти\n";
    cin >> broj;
    
    cout << "Въведи единична цена\n";
    cin >> ed_cena;

    total = broj * ed_cena;
    
    cout << "Ако вие сте закупили " << broj << " продукти и те струват " << ed_cena << " лева"
        << " то вие сте платили " << total << " лева";
}
