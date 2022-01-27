#include <iostream>
using namespace std;

const int MAX_NUMBER_SCORES = 10;

void fill_array(int a[], int& number_used);
double compute_average(const int a[], int number_used);
void show_difference(const int a[], int number_used);

void main()
{
	int score[MAX_NUMBER_SCORES], number_used;

	cout << "This program reads golf scores and shows\n"
		<< "how much each differs from the average.\n";

	cout << "Enter golf scores:\n";
	fill_array(score, number_used);
	show_difference(score, number_used);
}

void fill_array(int a[], int& number_used)
{
	cout << "Enter up to " << MAX_NUMBER_SCORES << " nonnegative whole numbers.\n"
		<< "Mark the end of the list with a negative number.\n";
	int next, index = 0;
	cin >> next;
	while ((next >= 0) && (index < MAX_NUMBER_SCORES))
	{
		a[index] = next;
		index++;
		cin >> next;
	}
	number_used = index;
}

double compute_average(const int a[], int number_used)
{
	if (number_used <= 0)
	{
		cout << "ERROR: number of elements is 0 in compute_average.\n"
			<< "compute_average returns 0.\n";
		return 0;
	}

	double total = 0;
	for (int i = 0; i < number_used; i++)
	{
		total += a[i];
	}

	return total / number_used;
}

void show_difference(const int a[], int number_used)
{
	double average = compute_average(a, number_used);
	cout << "Average of the " << number_used
		<< " scores = " << average << endl
		<< "The scores are:\n";
	for (int i = 0; i < number_used; i++)
	{
		cout << a[i] << " differs from average by "
			<< a[i] - average << endl;
	}
}
