#include <iostream>
using namespace std;

int main()
{
	int a, b, c;
	cin >> a >> b >> c;

	if (a < 0 || b < 0 || c < 0)
	{
		cout << "Enter positive numbers";
		return 0;
	}

	int max = a;
	if (b > max) max = b;
	if (c > max) max = c;

	int min = a;
	if (b < min) min = b;
	if (c < min) min = c;

	cout << "Min: " << min << " Max: " << max;
}

