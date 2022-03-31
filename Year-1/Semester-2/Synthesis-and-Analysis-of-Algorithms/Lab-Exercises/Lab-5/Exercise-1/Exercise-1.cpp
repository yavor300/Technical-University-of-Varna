#include <iostream>
using namespace std;

/*
* Задача 1
* Програмно да се реализира динамичен стек S от цели положителни числа,
* който да се преобразува в два други стека –P и Q, съдържащи съответно
* четните и нечетните стойности на S. Съдържанието на двата стека да се изведе на екрана.
*/

struct CustomStack
{
	int key;
	CustomStack* next;
};

void push(CustomStack*& stack, int n)
{
	CustomStack* p = stack;
	stack = new CustomStack;
	stack->key = n;
	stack->next = p;
}

int pop(CustomStack*& stack, int& n)
{
	if (stack)
	{
		n = stack->key;
		CustomStack* p = stack;
		stack = stack->next;
		delete p;
		return 1;
	}
	return 0;
}

void main()
{
	CustomStack* main = NULL;
	CustomStack* even = NULL;
	CustomStack* odd = NULL;

	int num;

	while (cin >> num)
		push(main, num);

	while (pop(main, num))
	{
		if (num % 2 == 0) push(even, num);
		else push(odd, num);
	}

	while (pop(even, num))
		cout << num << " ";
	while (pop(odd, num))
		cout << num << " ";
}
