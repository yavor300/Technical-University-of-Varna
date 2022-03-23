#include <iostream>
#include <queue>
#include <string.h>
using namespace std;

/*
* Задача 2
*
* Задача за стабилните бракове
*/

int ranking[505][505];
int man_pref[505][505];
int woman_pref[505][505];
int next_to_propose[505];
int matches[505];

int main()
{
	queue<int> free_men;
	int count, woman, man;
	cin >> count;

	/*
	* Initializing the preference list of each woman
	*/
	for (int i = 1; i <= count; i++)
	{
		cin >> woman;
		for (int j = 1; j <= count; j++)
		{
			cin >> woman_pref[woman][j];
		}
	}

	/*
	* Initializing the preference list of each man
	*/
	for (int i = 1; i <= count; i++)
	{
		cin >> man;
		for (int j = 1; j <= count; j++)
		{
			cin >> woman_pref[man][j];
		}
	}

	/*
	* Initializing the ranking
	*/
	for (int i = 1; i <= count; i++)
	{
		for (int j = 1; j <= count; j++)
		{
			ranking[i][woman_pref[i][j]] = j;
		}
	}

	memset(matches, 0, (count + 1) * sizeof(int));

	for (int i = 1; i <= count; i++)
	{
		free_men.push(i);
		next_to_propose[i] = 1;
	}

	while (!free_men.empty())
	{
		int current_man = free_men.front();
		int current_woman = man_pref[current_man][next_to_propose[current_man]];

		if (matches[current_woman] == 0)
		{
			matches[current_woman] = current_man;
			free_men.pop();
		}
		else if (ranking[current_woman][current_man] < ranking[current_woman][matches[current_woman]])
		{
			int ex_man = matches[current_woman];
			free_men.pop();
			matches[current_woman] = current_man;
			free_men.push(ex_man);
		}
		next_to_propose[current_man]++;
	}

	for (int i = 1; i <= count; i++) {
		printf("man : %d , woman : %d get married!\n", matches[i], i);
	}
}
