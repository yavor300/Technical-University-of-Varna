#include <iostream>
#include <iomanip>
#include <time.h>
#include <Windows.h>
using namespace std;

const int SQUARE_MATRIX_SIZE = 6;

const int MIN_NUMBER_IN_MATRIX = -20;
const int MAX_NUMBER_IN_MATRIX = 20;

void fill_in_matrix(int B[][SQUARE_MATRIX_SIZE]);
void print_matrix(int B[][SQUARE_MATRIX_SIZE]);
void print_zones(int B[][SQUARE_MATRIX_SIZE]);
int get_max_number_in_zones(int B[][SQUARE_MATRIX_SIZE]);

/*
* Условие на задача: https://drive.google.com/file/d/1NkLAu5IKk-_k_9LZINpjNXVTp7NCYLVr/view?usp=sharing
*/
int main()
{
	setlocale(LC_ALL, "BG");
	SetConsoleOutputCP(1251);

	int B[SQUARE_MATRIX_SIZE][SQUARE_MATRIX_SIZE];
	fill_in_matrix(B);

	cout << "Генерирана матрица:\n\n";
	print_matrix(B);

	cout << "\nЧисла в зоните:\n";
	print_zones(B);

	int max = get_max_number_in_zones(B);
	printf("\nНай-голямото число в посочените зони е: %d\n", max);
}

void fill_in_matrix(int B[][SQUARE_MATRIX_SIZE])
{
	srand(time(NULL));
	for (int i = 0; i < SQUARE_MATRIX_SIZE; i++)
	{
		for (int j = 0; j < SQUARE_MATRIX_SIZE; j++)
		{
			B[i][j] = (MIN_NUMBER_IN_MATRIX + (rand() % (MAX_NUMBER_IN_MATRIX - MIN_NUMBER_IN_MATRIX + 1)));
		}
	}
}

void print_matrix(int B[][SQUARE_MATRIX_SIZE])
{
	for (int i = 0; i < SQUARE_MATRIX_SIZE; i++)
	{
		for (int j = 0; j < SQUARE_MATRIX_SIZE; j++)
		{
			cout << setw(3) << B[i][j] << " ";
		}
		cout << endl;
	}
}

void print_zones(int B[][SQUARE_MATRIX_SIZE])
{
	for (int i = 0; i < SQUARE_MATRIX_SIZE; i++)
	{
		for (int j = 0; j < SQUARE_MATRIX_SIZE; j++)
		{
			if (i > j && i + j < SQUARE_MATRIX_SIZE - 1)
			{
				cout << setw(3) << B[i][j] << " ";
			}
			else if (j > i && i + j >= SQUARE_MATRIX_SIZE)
			{
				cout << setw(3) << B[i][j] << " ";
			}
			else
			{
				cout << setw(3) << "   " << " ";
			}
		}
		cout << endl;
	}
}

int get_max_number_in_zones(int B[][SQUARE_MATRIX_SIZE])
{
	int max = INT_MIN;
	for (int i = 1; i < SQUARE_MATRIX_SIZE - 1; i++)
	{
		for (int j = 0; j < SQUARE_MATRIX_SIZE; j++)
		{
			if (i > j && i + j < SQUARE_MATRIX_SIZE - 1)
			{
				if (B[i][j] > max) max = B[i][j];
			}
			else if (j > i && i + j >= SQUARE_MATRIX_SIZE)
			{
				if (B[i][j] > max) max = B[i][j];
			}
		}
	}
	return max;
}
