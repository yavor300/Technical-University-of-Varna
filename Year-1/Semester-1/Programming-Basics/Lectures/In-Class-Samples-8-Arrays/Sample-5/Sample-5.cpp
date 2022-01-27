#include <iostream>
using namespace std;

const int DECLARED_SIZE = 10;

void fill_array(int a[], int& number_used);
void sort(int a[], int number_used);
void swap_values(int& v1, int& v2);
int index_of_smallest(const int a[], int start_index, int number_used);

void main()
{
	cout << "This program sorts numbers from lowest to highest.\n";

	int sample_array[DECLARED_SIZE], number_used;
	fill_array(sample_array, number_used);
	sort(sample_array, number_used);
	
	cout << "In sorted order the numbers are:\n";
	for (int i = 0; i < number_used; i++)
	{
		cout << sample_array[i] << " ";
	}
}

void fill_array(int a[], int& number_used)
{
	cout << "Enter up to " << DECLARED_SIZE << " nonnegative whole numbers.\n"
		<< "Mark the end of the list with a negative number.\n";
	int next, index = 0;
	cin >> next;
	while ((next >= 0) && (index < DECLARED_SIZE))
	{
		a[index] = next;
		index++;
		cin >> next;
	}
	number_used = index;
}

void swap_values(int& v1, int& v2)
{
	int temp;
	temp = v1;
	v1 = v2;
	v2 = temp;
}

int index_of_smallest(const int a[], int start_index, int number_used)
{
	int min = a[start_index], index_of_min = start_index;
	for (int i = start_index + 1; i < number_used; i++)
	{
		if (a[i] < min)
		{
			min = a[i];
			index_of_min = i;
		}
	}
	return index_of_min;
}

void sort(int a[], int number_used)
{
	int index_of_next_smallest;
	for (int i = 0; i < number_used - 1; i++)
	{
		index_of_next_smallest = index_of_smallest(a, i, number_used);
		swap_values(a[i], a[index_of_next_smallest]);
	}
}
