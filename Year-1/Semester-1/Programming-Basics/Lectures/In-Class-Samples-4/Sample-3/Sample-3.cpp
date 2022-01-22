#include <iostream>
using namespace std;

const char file_name[9] = "data.txt";

void main()
{
	cout << "Enter a line of input and I will echo it:\n";
	
	char symbol;
	do
	{
		cin.get(symbol);
		cout.put(symbol);
	} while (symbol != '\n');
	
	cout << "That's all for this demonstration.";
}
