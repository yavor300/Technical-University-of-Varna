#include <iostream>
using namespace std;

int main()
{
    int points;
    cin >> points;

    if (points <= 49) cout << "Slab 2.0";
    else if (points >= 50 && points <= 61) cout << "Sreden 3";
    else if (points >= 62 && points <= 74) cout << "Dobyr 4";
    else if (points >= 75 && points <= 88) cout << "Mn. Dobyr 5";
    else if (points >= 89) cout << "Otlichen 6";
}
