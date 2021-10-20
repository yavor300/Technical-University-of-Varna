#include <iostream>
using namespace std;
int main()
{
    int bonbonsMaria, bonbonsSister = 0;
    cin >> bonbonsMaria;

    if (bonbonsMaria > 3)
    {
        bonbonsSister += bonbonsMaria;
    }
    else if (bonbonsMaria % 2 == 0)
    { 
        bonbonsSister += bonbonsMaria / 2;
    }
    else if (bonbonsMaria % 2 != 0) 
    {
        bonbonsSister += ((bonbonsMaria - 1) / 2);
    }
    else 
    { 
        bonbonsSister = 0;
    };

    cout << "Bonbons of Maria's sister: " << bonbonsMaria;
}
