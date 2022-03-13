#include <iostream>
using namespace std;

/*
* Exercise 1
* 
* A Maze is given as N*N binary matrix of blocks where source block is the upper left most block i.e.,
* maze[0][0] and destination block is lower rightmost block i.e., maze[N-1][N-1].
* A rat starts from source and has to reach the destination.
* The rat can move only in two directions: forward and down. 
* In the maze matrix, 0 means the block is a dead end and 1 means the block can be
* used in the path from source to destination. 
*/
const int MAZE_SIZE = 4;

void print_solution(int maze[][MAZE_SIZE])
{
	for (int i = 0; i < MAZE_SIZE; i++)
	{
		for (int j = 0; j < MAZE_SIZE; j++)
		{
			printf(" %d ", maze[i][j]);
		}
		printf("\n");
	}
}

bool is_safe(int x, int y, int maze[][MAZE_SIZE])
{
	return x >= 0 && x < MAZE_SIZE && y >= 0 && y < MAZE_SIZE
		&& maze[x][y];
}

bool solve_maze_util(int maze[][MAZE_SIZE], int x, int y, int solution[][MAZE_SIZE])
{
	if (x == MAZE_SIZE - 1 && y == MAZE_SIZE - 1 && maze[x][y])
	{
		solution[x][y] = 1;
		return true;
	}

	if (is_safe(x, y, maze))
	{
		if (solution[x][y]) return false;

		solution[x][y] = 1;
		
		if (solve_maze_util(maze, x + 1, y, solution)) return true;
		if (solve_maze_util(maze, x, y + 1, solution)) return true;

		solution[x][y] = 0;
		return false;
	}

	return false;
}

bool solve_maze(int maze[][MAZE_SIZE])
{
	int solution[MAZE_SIZE][MAZE_SIZE] = {
		{0, 0, 0, 0},
		{0, 0, 0, 0},
		{0, 0, 0, 0},
		{0, 0, 0, 0}
	};

	if (!solve_maze_util(maze, 0, 0, solution))
	{
		printf("Solution does not exist!");
		return false;
	}

	print_solution(solution);
	return true;
}

void main()
{
	int maze[MAZE_SIZE][MAZE_SIZE] = {
		{1, 1, 0, 0},
		{0, 1, 1, 1},
		{0, 1, 0, 0},
		{1, 1, 1, 1}
	};

	solve_maze(maze);
}
