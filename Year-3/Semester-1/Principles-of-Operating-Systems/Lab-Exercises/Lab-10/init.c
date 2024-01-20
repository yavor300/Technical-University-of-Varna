#include <stdio.h>
#include <shared.h>

int main()
{
	int *counter;
	counter = (int *)getmem(1577);
	*counter = 0;
	printf("The initial value is %d.\n", *counter);
}
