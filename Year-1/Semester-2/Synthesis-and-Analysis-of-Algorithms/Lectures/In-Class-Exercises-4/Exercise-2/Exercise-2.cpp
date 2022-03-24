#include <iostream>
using namespace std;

const int s_size = 10;
int st[s_size];
int i = 0;

void push(int n)
{
	st[i++] = n;
}

int pop()
{
	return st[--i];
}

int main()
{
	int num;
	while (cin >> num)
	{
		push(num);
	}
	while (i > 0)
	{
		cout << pop() << " ";
	}
}
