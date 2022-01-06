#include <iostream>
using namespace std;

const int ROWS = 6;
const int COLS = 6;

void fill_in_matrix(int B[ROWS][COLS]);
void print_matrix(int B[ROWS][COLS]);
void create_new_bool_array(int B[ROWS][COLS], bool Bnew[]);
void print_array(bool Bnew[]);
void print_array(int pro[]);
void proizvedenie(int B[ROWS][COLS], int proizvedenie[]);

int calculate_main_diagonal(int B[][COLS]);
int calculate_second_diagonal(int B[][COLS]);
int main()
{
	int B[ROWS][COLS];
	fill_in_matrix(B);
	print_matrix(B);
	int main = calculate_main_diagonal(B);
	int second = calculate_second_diagonal(B);
	cout << main << " " << second;
}

int calculate_main_diagonal(int B[][COLS])
{
	int result = 0;
	for (int i = 0; i < ROWS; i++)
	{
		for (int j = 0; j < COLS; j++)
		{
			if (i == j)
			{
				result += B[i][j];
			}
		}
	}
	return result;
}

int calculate_second_diagonal(int B[][COLS])
{
	int result = 0;
	for (int i = 0; i < ROWS; i++)
	{
		for (int j = 0; j < COLS; j++)
		{
			if (i + j == ROWS - 1)
			{
				result += B[i][j];
			}
		}
	}
	return result;
}

void fill_in_matrix(int B[ROWS][COLS])
{
	srand(time(0));
	int min = 1;
	int max = 20;

	for (int i = 0; i < ROWS; i++)
	{
		for (int j = 0; j < COLS; j++)
		{
			B[i][j] = (min + (rand() % (max - min + 1)));
		}
	}
}

void print_matrix(int B[ROWS][COLS])
{
	for (int i = 0; i < ROWS; i++)
	{
		for (int j = 0; j < COLS; j++)
		{
			cout << B[i][j] << " ";
		}
		cout << endl;
	}
}

void create_new_bool_array(int B[ROWS][COLS], bool Bnew[])
{
	for (int i = 0; i < ROWS; i++)
	{
		for (int j = 0; j < COLS; j++)
		{
			if (B[i][j] < 0)
			{
				Bnew[i] = true;
				break;
			}
		}
	}
}

void print_array(bool Bnew[])
{
	for (int i = 0; i < ROWS; i++)
	{
		cout << boolalpha << Bnew[i] << " ";
	}
}
void proizvedenie(int B[ROWS][COLS], int proizvedenie[])
{
	for (int i = 0; i < COLS; i++)
	{
		for (int j = 0; j < ROWS; j++)
		{
			if (B[j][i] > 0)
			{
				proizvedenie[i] *= B[j][i];
			}
		}
	}
}

void print_array(int pro[])
{
	for (int i = 0; i < COLS; i++)
	{
		cout << pro[i] << " ";
	}
}
