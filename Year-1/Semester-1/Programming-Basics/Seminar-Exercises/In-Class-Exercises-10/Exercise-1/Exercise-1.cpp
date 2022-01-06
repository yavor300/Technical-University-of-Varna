#include <iostream>
using namespace std;

const int ROWS = 4;
const int COLS = 5;

void fill_in_matrix(int B[ROWS][COLS]);
void print_matrix(int B[ROWS][COLS]);
void create_new_bool_array(int B[ROWS][COLS], bool Bnew[]);
void print_array(bool Bnew[]);
void print_array(int pro[]);
void proizvedenie(int B[ROWS][COLS], int proizvedenie[]);


int main()
{
	bool Bnew[ROWS] = { false, false, false, false };
	int proizvedenies[COLS] = { 1, 1, 1 , 1, 1 };
    int B[ROWS][COLS];
	fill_in_matrix(B);
	print_matrix(B);
	create_new_bool_array(B, Bnew);
	print_array(Bnew);
	proizvedenie(B, proizvedenies);
	print_array(proizvedenies);
}

void fill_in_matrix(int B[ROWS][COLS])
{
	srand(time(0));
	int min = -100;
	int max = 100;

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
