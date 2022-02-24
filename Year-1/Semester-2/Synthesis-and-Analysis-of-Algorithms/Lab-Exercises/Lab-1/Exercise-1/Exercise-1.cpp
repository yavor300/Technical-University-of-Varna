#include <iostream>
using namespace std;

const int N = 100;

void fill_in_array(bool numbers[]);
void mark_non_prime_numbers(bool numbers[]);
void print_prime_numbers(const bool numbers[]);

int main()
{
	bool numbers[N];
	fill_in_array(numbers);
	mark_non_prime_numbers(numbers);
	print_prime_numbers(numbers);
}

void fill_in_array(bool numbers[])
{
	for (int i = 0; i < N; i++)
	{
		numbers[i] = 1;
	}
}

void mark_non_prime_numbers(bool numbers[])
{
	for (int i = 2; i < N; i++)
	{
		if (numbers[i])
		{
			for (int j = i; j * i < N; j++)
			{
				numbers[i * j] = 0;
			}
		}
	}
}

void print_prime_numbers(const bool numbers[])
{
	for (int i = 1; i < N; i++)
	{
		if (numbers[i]) cout << i << " ";
	}
}
