#include <iostream>
using namespace std;

const int N = 8;

void print_solution(int board[][N]);
bool is_safe(int board[][N], int row, int col);
bool solve_n_queen_util(int board[][N], int col);
bool solve_n_queen();
void fill_in_board(int board[][N]);

void main()
{
	solve_n_queen();
}

void print_solution(int board[][N])
{
	for (int i = 0; i < N; i++)
	{
		for (int j = 0; j < N; j++)
		{
			printf(" %d ", board[i][j]);
		}
		printf("\n");
	}
}

bool is_safe(int board[][N], int row, int col)
{
	int i, j;

	for (i = 0; i < col; i++)
	{
		if (board[row][i]) return false;
	}

	for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
	{
		if (board[i][j]) return false;
	}

	for (i = row, j = col; j >= 0 && i < N; i++, j--)
	{
		if (board[i][j]) return false;
	}

	return true;
}

bool solve_n_queen_util(int board[][N], int col)
{
	if (col >= N) return true;

	for (int i = 0; i < N; i++)
	{
		if (is_safe(board, i, col))
		{
			board[i][col] = 1;
			if (solve_n_queen_util(board, col + 1)) return true;
			board[i][col] = 0;
		}
	}

	return false;
}

void fill_in_board(int board[][N])
{
	for (int i = 0; i < N; i++)
	{
		for (int j = 0; j < N; j++)
		{
			board[i][j] = 0;
		}
	}
}


bool solve_n_queen()
{
	int board[N][N];
	fill_in_board(board);
	
	if (!solve_n_queen_util(board, 0))
	{
		printf("Solution does not exist!");
		return false;
	}

	print_solution(board);
	return true;
}
