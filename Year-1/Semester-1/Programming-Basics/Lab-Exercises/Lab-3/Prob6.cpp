#include <iostream>
using namespace std;
int main()
{
    int a, b, c;
    cin >> a >> b >> c;

    if (!(a + b > c)) {
        cout << "Ivalid sides";
        return 0;
    }

    cout << "Perimeter: " << a + b + c;
}

