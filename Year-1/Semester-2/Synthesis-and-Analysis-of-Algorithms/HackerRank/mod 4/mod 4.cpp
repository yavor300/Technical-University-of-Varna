#include <iostream>
using namespace std;

/*
* Напишете програма, която по зададени цели положителни числа x, y и n,
* изчислява по ефективен начин x^y mod n (операцията ^ означава повдигане на степен,
* а не изключващо или - XOR).
*/

int calculate_power_mod(long x, long y, int n);

int main()
{
	long C, x, y, n, last;
	cin >> C;
	for (int i = 0; i < C; i++)
	{
		cin >> x >> y >> n;
		cout << calculate_power_mod(x, y, n) << endl;
	}
	cin >> last;
	return 0;
}

int calculate_power_mod(long x, long y, int n)
{
	if (x % n == 0) return 0;

	int res = 1;
	while (y > 0)
	{
		if (y % 2 != 0) res = (res * x) % n;
		y /= 2;
		x = (x * x) % n;
	}

	return res;
}
