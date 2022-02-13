#include <iostream>
using namespace std;

const int MAX_WEIGHT = 10;
const int NUMBER_OF_ITEMS = 4;
const int values[NUMBER_OF_ITEMS] = { 10, 40, 30, 50 };
const int weight[NUMBER_OF_ITEMS] = { 5, 4, 6, 3 };

void main()
{
	int arr[MAX_WEIGHT + 1];
	for (int i = 0; i < MAX_WEIGHT + 1; i++)
	{
		arr[i] = 0;
	}

	for (int item = 0; item < NUMBER_OF_ITEMS; item++)
	{
		for (int capacity = MAX_WEIGHT; capacity > 0; capacity--)
		{
			int max_value_without_curr = arr[capacity];

			int max_value_with_curr = 0;
			int weightOfCurr = weight[item];
			if (capacity >= weightOfCurr)
			{
				max_value_with_curr = values[item];
				int remainingCapacity = capacity - weightOfCurr;
				max_value_with_curr += arr[remainingCapacity];
			}

			if (max_value_without_curr > max_value_with_curr) arr[capacity] = max_value_without_curr;
			else arr[capacity] = max_value_with_curr;
		}
	}

	cout << arr[MAX_WEIGHT];
}
