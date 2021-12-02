#include <iostream>
using namespace std;

const int N = 25;
void enter(int arr[], int& k);
double sredno(int arr[], int k, int nechet[], int& brnech);
void sortirane(int arr[], int k, int sortarr[]);
void print(int arr[], int k);
int main()
{
	int c[N], cn[N], cs[N];
	int k, brnech;
	double sredna_stoinost;
	enter(c, k);

}

void print(int arr[], int k)
{
	for (int i = 0; i < k; i++) cout << arr[i] << " ";

}

void sortirane(int arr[], int k, int sortarr[])
{
	for (int i = 0; i < k; i++) sortarr[i] = arr[i];

	for (int i = 0; i < k - 1; i++)
	{
		bool swapped = false;
		for (int j = 0; j < k - 1 - i; j++)
		{
			if (sortarr[j] < sortarr[j + 1])
			{
				int temp = sortarr[j];
				sortarr[j] = sortarr[j + 1];
				sortarr[j + 1] = temp;
				swapped = true;
			}
		}
		if (!swapped) break;
	}
}

double sredno(int arr[], int k, int nechet[], int& brnech)
{
	brnech = 0;
	double sum = 0;

	for (int i = 0; i < k; i++)
	{
		if (arr[i] % 2 != 0)
		{
			nechet[brnech++] = arr[i];
			sum += arr[i];
		}
	}

	if (brnech == 0) return 0;

	return sum / brnech;
}

void enter(int arr[], int& k)
{
	do
	{
		cout << "Въведи число за брой елементи: ";
		cin >> k;
	} while (k <= 0 || k > N);

	for (int i = 0; i < k; i++)
	{
		cin >> arr[i];
	}
}

