#include <iostream>
using namespace std;

struct CustomStack
{
	int key;
	CustomStack* next;
};
CustomStack* start = NULL;

void push(int n)
{
	CustomStack* p = start;
	start = new CustomStack;
	start->key = n;
	start->next = p;
}

int pop(int& n)
{
	if (start)
	{
		n = start->key;
		CustomStack* p = start;
		start = start->next;
		delete p;
		return 1;
	}
	return 0;
}

int main()
{
	int num;
	while (cin >> num)
	{
		push(num);
	}
	while (pop(num))
	{
		cout << num << " ";
	}
}
