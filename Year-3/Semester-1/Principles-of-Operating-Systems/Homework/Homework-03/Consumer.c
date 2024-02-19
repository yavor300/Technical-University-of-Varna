#include <stdio.h>
#include <shared.h>
#include <unistd.h>

int main()
{
	char  *letter;
	int i;

	for (i = 97; i <= 122; i++)
	{
		letter=(char *)getmem(1577);
		printf("%c ", *letter);
		sleep(1);
	}
	printf("\nConsumer process ended.\n");
}
