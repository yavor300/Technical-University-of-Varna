#include <stdio.h>
#include <shared.h>

int main()
{

	int *counter, i, k, a;
	counter = (int *)getmem(1577);
	for (i = 1; i <= 1000000; i++)
	{
		a = *counter;
		a = a - 1;
		*counter = a;
		for (k = 0; k < 1000; k++);
	}
	printf("P2 process ended.\n");
}
