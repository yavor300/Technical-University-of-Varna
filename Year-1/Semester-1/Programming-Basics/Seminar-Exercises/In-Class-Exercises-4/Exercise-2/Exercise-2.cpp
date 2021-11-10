#include <iostream>
using namespace std;

int main()
{
	int count = 0;

	int number;
	cin >> number;

	while (true) {
		if (number == 0) break; // if (!number) break; //exit; //return;

		if (number % 7 == 0 || number % 5 == 0) count++;
		cin >> number;
	}

	for (;;) // bezkraen for
	{
		if (number == 0) break;

		if (number % 7 == 0 || number % 5 == 0) count++;
		cin >> number;
	}

	cout << count;
}

