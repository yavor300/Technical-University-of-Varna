#pragma warning(disable:4996)
#include <iostream>
#include <stdio.h>
#include <Windows.h>
using namespace std;

double massiv[10] = { 1.34, 18.67, 1009.674, 666.88, 12.8,
 0.0, 34.86, 22.222, 1.00001, 0.7777 };

void main()
{
	setlocale(LC_ALL, "BG");
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);

	int i;
	FILE *fp;
	if ((fp = fopen("out_massiv.dat", "wb")) == NULL)
	{
		cout << endl << "Грешка при отваряне на файла";
		exit(1);
	}

	for (int i = 0; i < 10; i++)
	{
		if (fwrite(&massiv[i], sizeof(double), 1, fp) != 1)
		{
			cout << endl << "Грешка при запис" << endl;
			exit(1);
		}
	}
	fclose(fp);

	if ((fp = fopen("out_massiv.dat", "rb")) == NULL)
	{
		cout << endl << "Файлът не се отваря";
		exit(1);
	}

	for (i = 0; i < 10; i++)
	{
		massiv[i] = 0.0;
	}

	for (i = 0; i < 10; i++)
	{
		if (fread(&massiv[i], sizeof(double), 1, fp) != 1)
		{
			cout << endl << "Грешка при четене" << endl;
			exit(1);
		}
	}
	fclose(fp);

	for (i = 0; i < 10; i++) // Извеждане на масива
		cout << endl << massiv[i];
}