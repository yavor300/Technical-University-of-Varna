#include <iostream>
using namespace std;

void main()
{
	int arr[5] = { 10, 20, 30, 40, 50 };
	int *pointer;
	pointer = arr;
	
	for (int i = 0; i < 5; i++)
	{
		cout << endl << arr[i] << '\t' << pointer[i];
	}
	
	for (int i = 0; i < 5; i++)
	{
		cout << endl << *(arr + i) << '\t' << *(pointer + i);
	}
}

