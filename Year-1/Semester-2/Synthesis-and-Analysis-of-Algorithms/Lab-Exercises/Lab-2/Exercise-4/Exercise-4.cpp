#include <iostream>
using namespace std;

int ngod(int m, int n)
{
	if (n == 0) return m;
	else if (n > m) return ngod(n, m);
	else return ngod(n, m % n);
}

int main()
{
	cout << ngod(30, 15);
}
