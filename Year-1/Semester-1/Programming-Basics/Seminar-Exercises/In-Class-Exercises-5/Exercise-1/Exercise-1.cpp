#include <iostream>
#include <math.h>
using namespace std;

int main()
{
    setlocale(LC_ALL, "BG");

    cout << "Въведи три цели числа: ";
    int a, b, c;
    cin >> a >> b >> c;

    cout << pow(a, 2) << endl;
    cout << pow(b, 2) << endl;
    cout << pow(c, 2);
}

