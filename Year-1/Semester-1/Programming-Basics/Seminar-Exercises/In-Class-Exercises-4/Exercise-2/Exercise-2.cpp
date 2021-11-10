#include <iostream>
using namespace std;

int main()
{
	int count = 0;

	int number;
	cin >> number;

	while (number != 0) {
		if (number % 7 == 0 || number % 5 == 0) count++;
		cin >> number;
	}

	cout << count;
}

