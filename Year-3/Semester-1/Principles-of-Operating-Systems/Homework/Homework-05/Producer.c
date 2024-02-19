#include <stdio.h>
#include <shared.h>
#include <unistd.h>

int main()
{

	
	sem_t sem_producer, sem_consumer;
	int *ascii;
	ascii=(int *)getmem(3577);
	sem_producer = sem_init(1577);
	sem_consumer = sem_init(1578);
	sem_set(sem_producer, 1);
	sem_set(sem_consumer, 0);
	for (int i = 97; i <= 122; i++)
	{
		PS(sem_producer);
		*ascii = i;
		VS(sem_consumer);
	}
	printf("Producer process ended.\n");
	sem_remove(sem_producer);
	sem_remove(sem_consumer);
}


