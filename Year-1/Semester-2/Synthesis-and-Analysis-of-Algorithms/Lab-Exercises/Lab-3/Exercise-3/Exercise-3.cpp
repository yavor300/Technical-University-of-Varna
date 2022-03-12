#include <iostream>
#include <iomanip>
using namespace std;

/*
* Задача 3
*
* Обхождане на шахматна дъска с ходовете на коня
*/

const int N = 8;

bool is_safe(int x, int y, int board[][N])
{
	return x >= 0 && x < N && y >= 0 && y < N
		&& !board[x][y];
}

void print_solution(int board[][N])
{
	for (int i = 0; i < N; i++)
	{
		for (int j = 0; j < N; j++)
		{
			cout << setw(3) << board[i][j];
		}
		cout << endl;
	}
}

int solve_knight_tour_util(int x, int y, int move_index, int board[][N],
	int x_move[8], int y_move[8])
{
	if (move_index == N * N) return 1;

	for (int i = 0; i < 8; i++)
	{
		int next_x = x + x_move[i];
		int next_y = y + y_move[i];
		if (is_safe(next_x, next_y, board))
		{
			board[next_x][next_y] = move_index;
			if (solve_knight_tour_util(next_x, next_y, move_index + 1, board, x_move, y_move) == 1) return 1;
			else board[next_x][next_y] = 0;
		}
	}
	return 0;
}

void solve_knight_tour()
{
	int board[N][N];
	for (int i = 0; i < N; i++)
	{
		for (int j = 0; j < N; j++)
		{
			board[i][j] = 0;
		}
	}

	int x_move[8] = { 2, 1, -1, -2, -2, -1, 1, 2 };
	int y_move[8] = { 1, 2, 2, 1, -1, -2, -2, -1 };

	int start_x, start_y;
	cin >> start_x >> start_y;

	board[start_x][start_y] = 1;

	if (solve_knight_tour_util(start_x, start_y, 2, board, x_move, y_move) == 0) cout << "Solution does not exist!";
	else print_solution(board);
}

void main()
{
	solve_knight_tour();
}
