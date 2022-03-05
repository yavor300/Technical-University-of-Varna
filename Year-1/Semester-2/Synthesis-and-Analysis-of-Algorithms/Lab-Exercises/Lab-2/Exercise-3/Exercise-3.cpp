#include <iostream>
using namespace std;

const int N = 10;

int fibonacci_dynamic(int n)
{
    int fib[N] = { 0 , 1 };
	for (int i = 2; i < N; i++)
	{
		fib[i] = fib[i - 2] + fib[i - 1];
	}
	return fib[n];
}

int main()
{
    cout << fibonacci_dynamic(11);
}
