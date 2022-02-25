#include <iostream>
#include <iomanip>
#include <math.h>
using namespace std;

/*
* Задача 1
* 
* Да се състави програма, която създава квадратна спирална матрица.
*
* Пример:
* 
* 1 2 3
* 8 9 4
* 7 6 5
*/

struct Corner {
	int x;
	int y;
};

const int MATRIX_SIZE = 5;
const int FIELD_WIDTH = 4;

void fill_in_spiral_matrix(int matrix[][MATRIX_SIZE], Corner& up_left, Corner& up_right, Corner& down_left, Corner& down_right);
void adjust_corners(Corner& up_left, Corner& up_right, Corner& down_left, Corner& down_right);
void print_matrix(int matrix[][MATRIX_SIZE]);

int main()
{
	int matrix[MATRIX_SIZE][MATRIX_SIZE];
	Corner up_left = { 0, 0 };
	Corner up_right = { 0, MATRIX_SIZE - 1 };
	Corner down_left = { MATRIX_SIZE - 1, 0 };
	Corner down_right = { MATRIX_SIZE - 1, MATRIX_SIZE - 1 };

	fill_in_spiral_matrix(matrix, up_left, up_right, down_left, down_right);

	print_matrix(matrix);
}

void fill_in_spiral_matrix(int matrix[][MATRIX_SIZE], Corner& up_left, Corner& up_right, Corner& down_left, Corner& down_right)
{
	int counter = 1;
	while (counter <= pow(MATRIX_SIZE, 2))
	{
		for (int j = up_left.y; j <= up_right.y; j++)
		{
			matrix[up_left.x][j] = counter++;
		}

		for (int j = up_right.x + 1; j <= down_right.x; j++)
		{
			matrix[j][up_right.y] = counter++;
		}

		for (int j = down_right.y - 1; j >= down_left.y; j--)
		{
			matrix[down_right.x][j] = counter++;
		}

		for (int j = down_left.x - 1; j > up_right.x; j--)
		{
			matrix[j][down_left.y] = counter++;
		}

		adjust_corners(up_left, up_right, down_left, down_right);
	}
}

void adjust_corners(Corner& up_left, Corner& up_right, Corner& down_left, Corner& down_right)
{
	up_left.x++;
	up_left.y++;

	up_right.x++;
	up_right.y--;

	down_left.x--;
	down_left.y++;

	down_right.x--;
	down_right.y--;
}

void print_matrix(int matrix[][MATRIX_SIZE])
{
	for (int i = 0; i < MATRIX_SIZE; i++)
	{
		for (int j = 0; j < MATRIX_SIZE; j++)
		{
			cout << setw(FIELD_WIDTH) << left << matrix[i][j];
		}
		printf("\n");
	}
}
