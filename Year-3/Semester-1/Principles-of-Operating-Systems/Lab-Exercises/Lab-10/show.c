#include <stdio.h>
#include <shared.h>

int main()
{
	int *counter;
	counter = (int *)getmem(1577);
	printf("The common value is %d.\n", *counter);
}
