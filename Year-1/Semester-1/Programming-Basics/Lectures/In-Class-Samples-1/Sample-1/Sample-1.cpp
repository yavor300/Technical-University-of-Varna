#include <iostream>
using namespace std;

void main()
{
    int broj, ed_cena, total;
    
    cout << "Въведи брой закупени продукти\n";
    cin >> broj;
    
    cout << endl << "Въведи единична цена";
    cin >> ed_cena;

    total = broj * ed_cena;
    
    cout << endl << "Ако вие сте закупили " << broj << " продукти и те струват " << ed_cena << "лева" << endl
        << "то вие сте платили " << total << " лева";
}
