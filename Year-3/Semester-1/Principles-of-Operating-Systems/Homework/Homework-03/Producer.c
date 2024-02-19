#include <stdio.h>
#include <shared.h>
#include <unistd.h>

int main()
{
	int *ascii, i, k;

	ascii=(int *)getmem(1577);

	for (i = 97; i <= 122; i++)
	{
		*ascii = i;
		sleep(1);
	}
	printf("Producer process ended.\n");
}
