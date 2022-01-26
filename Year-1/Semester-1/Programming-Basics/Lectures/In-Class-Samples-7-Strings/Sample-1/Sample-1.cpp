#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <string>
#include <cstring>
using namespace std;

int main()
{
	char my_message_1[30] = "Hello, World!";
	char my_message_2[] = "Hello, World!";
	char my_message_3[6] = { 'H', 'e', 'l', 'l', 'o', '\0' };
	char my_message_4[] = { 'H', 'e', 'l', 'l', 'o', '\0' };

	cout << my_message_1 << endl << my_message_2 << endl
		<< my_message_3 << endl << my_message_4 << endl;
	
	cout << my_message_4 << " has length of " << strlen(my_message_4) << endl;
	cout << my_message_4 << " has size of " << sizeof(my_message_4) << endl;

	for (int i = 0; i < strlen(my_message_4); i++)
	{
		my_message_4[i] = 'X';
	}
	cout << my_message_4;

	strcpy(my_message_4, my_message_3);
	cout << endl << my_message_4 << endl;
	strcpy(my_message_4, "    ");
	strncpy(my_message_4, my_message_3, 2);
	cout << my_message_4 << endl;

	if (strcmp(my_message_1, my_message_2)) cout << "The strings are NOT equal!" << endl;
	else cout << "The strings are equal!" << endl;

	if (strncmp(my_message_1, my_message_3, 4)) cout << "The first 4 characters are NOT equal!" << endl;
	else cout << "The first 4 characters are equal!" << endl;

	strcat(my_message_1, my_message_3);
	cout << my_message_1 << endl; // Using the available 30 bytes
	cout << strlen(my_message_1) << endl;
	strncat(my_message_1, my_message_3, 2);
	cout << my_message_1 << endl;
	cout << strlen(my_message_1) << endl;
}
