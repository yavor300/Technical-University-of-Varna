#include <iostream>
using namespace std;

void write_vertical(int n);

int main()
{
    int n;
    cin >> n;
    write_vertical(n);
}

void write_vertical(int n) {
    if (n < 10) cout << n << endl;
    else
    {
        write_vertical(n / 10);
        cout << n % 10 << endl;
    }
}
