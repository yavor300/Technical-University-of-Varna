#include <stdio.h>
#include <shared.h>
#include <unistd.h>

int main()
{

	
	sem_t sem1=sem_init(1111);
	sem_t sem2=sem_init(2222);
	sem_t sem3=sem_init(3333);
	sem_t sem4=sem_init(4444);

	char *letter = (char *)getmem(1577);
	char data;
	char *shared = (char *)getmem(2577);

	for (int i = 0; i < 26; i++)
	{
		PS(sem2);
		data = *letter;
		VS(sem1);

		PS(sem4);
		*shared = data - 32;
		VS(sem3);
	}
	return 0;
}


