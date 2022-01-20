#include <iostream>
using namespace std;

int power(int x, int n);

int main()
{
    for (int i = 0; i < 4; i++)
    {
        cout << "3 to the power " << i
            << " is " << power(3, i) << endl;
    }
}

int power(int x, int n)
{
    if (n < 0)
    {
        cout << "Illegal argument to power.\n";
        exit(1);
    }
    if (n > 0) return power(x, n - 1) * x;
    else return 1;
}
