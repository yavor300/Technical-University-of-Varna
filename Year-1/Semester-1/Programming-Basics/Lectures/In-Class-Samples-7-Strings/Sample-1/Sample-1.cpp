#include <iostream>
#include <string>
#include <cstring>
using namespace std;

int main()
{
	char my_message_1[20] = "Hello, World!";
	char my_message_2[] = "Hello, World!";
	char my_message_3[6] = { 'H', 'e', 'l', 'l', 'o', '\0' };
	char my_message_4[] = { 'H', 'e', 'l', 'l', 'o', '\0' };

	cout << my_message_1 << endl << my_message_2 << endl
		<< my_message_3 << endl << my_message_4 << endl;
	
	cout << my_message_4 << " has length of " << strlen(my_message_3) << endl;
	cout << my_message_4 << " has size of " << sizeof(my_message_4) << endl;

	for (int i = 0; i < strlen(my_message_4); i++)
	{
		my_message_4[i] = 'X';
	}
		
	cout << my_message_4;
}
