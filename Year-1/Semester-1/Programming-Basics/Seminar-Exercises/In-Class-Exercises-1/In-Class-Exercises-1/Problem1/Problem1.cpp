#include <iostream>
using namespace std;

void main()
{
	const int A = 7;
	const int C = 8;

	int x, y;
	cin >> x;

	if (x <= 4) y = x - A;
	else if (x < 20) y = x + A;
	else y = x - C;

	cout << y;
}
