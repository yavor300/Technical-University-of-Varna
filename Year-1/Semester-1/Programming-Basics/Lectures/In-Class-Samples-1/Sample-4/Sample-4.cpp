#include <iostream>
#include <string>
using namespace std;

void main()
{
	string middle_name, pet_name;
	string alter_ego_name;

	cout << "Enter your middle name and then the name of your pet.\n";
	cin >> middle_name >> pet_name;

	alter_ego_name = pet_name + " " + middle_name;

	cout << "The name of your alter ego is: " << alter_ego_name << "." << endl;
}
