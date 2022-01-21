#pragma warning(disable:4996)
#include <iostream>
#include <stdio.h>
#include <Windows.h>
using namespace std;

int main()
{
	setlocale(LC_ALL, "BG");
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);

	char str[80] = "Моят първи файл на C\n";
	FILE *fp;
	char *p, ch;

	if ((fp = fopen("out.txt", "w")) == NULL)
	{
		cout << endl << "Грешка при отварянето на файла";
		exit(1);
	}

	p = str;
	while (*p)
	{
		if (fputc(*p, fp) == EOF)
		{
			cout << endl << "Грешка при запис във файла";
			exit(1);
		}
		p++;
	}
	fclose(fp);

	if ((fp = fopen("out.txt", "r")) == NULL)
	{
		cout << endl << "Грешка при отварянето на файла";
		exit(1);
	}
	
	while ((ch = fgetc(fp)) != '\n')
	{
		putchar(ch);
	}
	fclose(fp);
}
