#include <iostream>
using namespace std;

int main()
{
	char my_message_1[20] = "Hello, World!";
	char my_message_2[] = "Hello, World!";
	char my_message_3[6] = { 'H', 'e', 'l', 'l', 'o', '\0' };
	char my_message_4[] = { 'H', 'e', 'l', 'l', 'o', '\0' };

	cout << my_message_1 << endl << my_message_2 << endl
		<< my_message_3 << endl << my_message_4;
}
