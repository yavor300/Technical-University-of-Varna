#include <stdio.h>
#include <shared.h>
#include <unistd.h>

int main()
{
	char *uppercaseLetter = (char *)getmem(2577);
	sem_t sem3, sem4;
	sem3 = sem_init(3333);
	sem4 = sem_init(4444);
	
	for (int i = 0; i < 26; i++)
	{
		PS(sem3);
		printf("%c ", *uppercaseLetter);
		VS(sem4);
	}
}

