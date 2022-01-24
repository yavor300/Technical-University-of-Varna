#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <fstream>
#include <string>
#include <Windows.h>
using namespace std;

int main()
{
	setlocale(LC_ALL, "BG");
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);

	char string_to_save[80];
	double number_1, number_2;
	fstream binary_file;

	binary_file.open("numbers.dat", ios::binary | ios::out);
	if (binary_file.fail())
	{
		cout << endl << "Файлът не може да се отвори" << endl;
		exit(1);
	}

	cout << endl << "Въведете низ: ";
	cin.getline(string_to_save, 80);
	int string_length = strlen(string_to_save);
	binary_file.write(string_to_save, string_length);
	
	cout << endl << "Въведете число 1: ";
	cin >> number_1;
	binary_file.write((char*)&number_1, sizeof(double));
	cout << endl << "Въведете число 2: ";
	cin >> number_2;
	binary_file.write((char*)&number_2, sizeof(double));

	binary_file.close();
	strcpy(string_to_save, "");
	number_1 = number_2 = 0,0;

	binary_file.open("numbers.dat", ios::binary | ios::in);
	if (binary_file.fail())
	{
		cout << endl << "Файлът не може да се отвори" << endl;
		exit(1);
	}
	binary_file.read(string_to_save, string_length);
	binary_file.read((char*)&number_1, sizeof(double));
	binary_file.read((char*)&number_2, sizeof(double));
	binary_file.close();
	
	cout << endl << string_to_save;
	cout << endl << number_1 << " " << number_2;

	return 0;
}
