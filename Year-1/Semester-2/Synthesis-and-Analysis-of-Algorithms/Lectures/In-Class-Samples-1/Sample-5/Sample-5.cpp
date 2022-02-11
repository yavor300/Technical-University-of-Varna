#include <iostream>
using namespace std;

const int N = 9;

int fib();

int main()
{
    cout << fib();
}

int fib()
{
    int f[N + 2];
    f[0] = 0;
    f[1] = 1;

    for (int i = 2; i <= N; i++)
    {
        f[i] = f[i - 1] + f[i - 2];
    }

    return f[N];
}
