#include <iostream>
#include <fstream>
#include <string>
#include <Windows.h>
using namespace std;

void main()
{
	setlocale(LC_ALL, "BG");
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);

	char symbols[] = "Varna e mojat roden grad";
	char symbol;
	long position;

	fstream file_stream;
	file_stream.open("sentence.dat", ios::out | ios::binary);
	if (file_stream.fail())
	{
		cout << endl << "Файлът не се отваря";
		exit(1);
	}

	file_stream.write(symbols, strlen(symbols));
	file_stream.close();

	cout << endl << "Въведете коя позиция ще промените (старт от 0): ";
	cin >> position;

	file_stream.open("sentence.dat", ios::in | ios::binary | ios::out);
	if (file_stream.fail())
	{
		cout << endl << "Файлът не се отваря";
		exit(1);
	}

	file_stream.seekg(position, ios::beg);
	file_stream.get(symbol);
	cout << "Символът е " << symbol;

	cout << endl << "Въведете новия символ: ";
	cin >> symbol;
	file_stream.seekg(-1L, ios::cur);
	file_stream.put(symbol);
	file_stream.close();

	file_stream.open("sentence.dat", ios::in | ios::binary);
	if (file_stream.fail())
	{
		cout << endl << "Файлът не се отваря";
		exit(1);
	}
	file_stream.read(symbols, strlen(symbols));
	file_stream.close();
	cout << endl << "Променен стринг: " << symbols;
}
