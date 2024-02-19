#include <stdio.h>
#include <shared.h>
#include <unistd.h>

int main()
{

	
	char *shared_memory = (char *)getmem(1577);
	sem_t sem1 = sem_init(1111);
	sem_t sem2 = sem_init(2222);

	for (char i = 'a'; i <= 'z'; i++)
	{
		PS(sem1);
		*shared_memory=i;
		VS(sem2);
	}
	return 0;
}


