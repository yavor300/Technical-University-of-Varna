#include <iostream>
using namespace std;

const int NUMBER_OF_EMPLOYEES = 3;
const int ADDITIONAL_DAYS = 5;

// Returns old days plus ADDITIONAL_DAYS  
int adjust_days(int old_days);

void main()
{
	int vacation[NUMBER_OF_EMPLOYEES], number;

	cout << "Enter allowed vacation days for employees 1"
		<< " through " << NUMBER_OF_EMPLOYEES << ":\n";
	for (number = 0; number < NUMBER_OF_EMPLOYEES; number++)
	{
		cin >> vacation[number];
	}

	for (number = 0; number < NUMBER_OF_EMPLOYEES; number++)
	{
		vacation[number] = adjust_days(vacation[number]);
	}

	cout << "The revised number of vacation days are:\n";
	for (number = 0; number < NUMBER_OF_EMPLOYEES; number++)
	{
		cout << "Employee number " << number + 1
			<< " vacation days = " << vacation[number] << endl;
	}
}

int adjust_days(int old_days)
{
	return old_days + ADDITIONAL_DAYS;
}
