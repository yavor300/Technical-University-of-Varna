#include <iostream>
#include <iomanip>
using namespace std;

/*
* Задача 1
*
* Да се състави програма, която създава квадратна матрица и я запълва отвън навътре.
* 
* Пример:
*
* 1 1 1 1 1
* 1 2 2 2 1
* 1 2 3 2 1
* 1 2 2 2 1
* 1 1 1 1 1
*/

struct Corner {
	int x;
	int y;
};

const int MATRIX_SIZE = 5;
const int FIELD_WIDTH = 3;

void fill_in_matrix_from_out_to_in(int matrix[][MATRIX_SIZE], Corner& up_left, Corner& up_right, Corner& down_left, Corner& down_right);
void adjust_corners(Corner& up_left, Corner& up_right, Corner& down_left, Corner& down_right);
void print_matrix(int matrix[][MATRIX_SIZE]);

int main()
{
	int matrix[MATRIX_SIZE][MATRIX_SIZE];
	Corner up_left = { 0, 0 };
	Corner up_right = { 0, MATRIX_SIZE - 1 };
	Corner down_left = { MATRIX_SIZE - 1, 0 };
	Corner down_right = { MATRIX_SIZE - 1, MATRIX_SIZE - 1 };

	fill_in_matrix_from_out_to_in(matrix, up_left, up_right, down_left, down_right);

	print_matrix(matrix);
}

void fill_in_matrix_from_out_to_in(int matrix[][MATRIX_SIZE], Corner& up_left, Corner& up_right, Corner& down_left, Corner& down_right)
{
	for (int i = 0; i < MATRIX_SIZE / 2 + 1; i++)
	{
		for (int j = up_left.y; j <= up_right.y; j++)
		{
			matrix[up_left.x][j] = i + 1;
		}

		for (int j = up_left.x; j <= down_left.x; j++)
		{
			matrix[j][up_left.y] = i + 1;
		}

		for (int j = up_left.y; j <= down_right.y; j++)
		{
			matrix[down_right.x][j] = i + 1;
		}

		for (int j = up_right.x; j <= down_right.x; j++)
		{
			matrix[j][up_right.y] = i + 1;
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
