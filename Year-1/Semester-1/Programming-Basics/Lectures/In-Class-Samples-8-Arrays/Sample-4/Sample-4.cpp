#include <iostream>
using namespace std;

const int DECLARED_SIZE = 20;

void fill_array(int a[], int& number_used);
int search(const int a[], int number_used, int target);

void main()
{
	int arr[DECLARED_SIZE], list_size, target;
	fill_array(arr, list_size);

	char ans;
	int result;
	do
	{
		cout << "Enter a number to search for: ";
		cin >> target;

		result = search(arr, list_size, target);
		if (result == -1) cout << target << " is not on the list.\n";
		else cout << target << " is stored in array position "
			<< result << endl
			<< "(Reminder: The first position is 0.)\n";

		cout << "Search again? (y/n followed by Enter): ";
		cin >> ans;
	} while ((ans != 'n') && (ans != 'N'));
	
	cout << "End of program.\n";
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

int search(const int a[], int number_used, int target)
{
	int index = 0;
	bool found = false;
	while ((!found) && (index < number_used))
	{
		if (a[index] == target) return index;
		index++;
	}
	
	return -1;
}
