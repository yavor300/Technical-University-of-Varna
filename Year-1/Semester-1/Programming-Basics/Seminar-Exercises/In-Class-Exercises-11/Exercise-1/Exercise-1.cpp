#include <iostream>
using namespace std;

int factorial(int n);

int main()
{
	int n;
	cin >> n;
	cout << factorial(n);
}

int factorial(int n)
{
	if (n > 1) return n * factorial(n - 1);
	else return 1;
}
