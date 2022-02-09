#include <iostream>
using namespace std;

int gcd(int a, int b);

void main()
{
    cout << gcd(36, 60);
    
}

int gcd(int a, int b)
{
    if (a == 0) return b;
    return gcd(b % a, a);
}
