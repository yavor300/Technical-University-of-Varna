#include <iostream>
using namespace std;

const int N = 5;

void fill_in_matrix(int matrix[][N]);
void print_matrix(const int matrix[][N]);

int main()
{
    int matrix[N][N];
	fill_in_matrix(matrix);
	print_matrix(matrix);
}

void fill_in_matrix(int matrix[][N])
{
	for (int i = 0; i < N; i++)
	{
		for (int j = 0; j < N; j++)
		{
			if (i == j || i > j) matrix[i][j] = 0;
			else matrix[i][j] = i + 1;
		}
	}
}

void print_matrix(const int matrix[][N])
{
	for (int i = 0; i < N; i++)
	{
		for (int j = 0; j < N; j++)
		{
			printf("%d ", matrix[i][j]);
		}
		printf("\n");
	}
}
