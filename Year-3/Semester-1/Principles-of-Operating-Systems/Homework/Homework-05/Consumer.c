#include <stdio.h>
#include <shared.h>
#include <unistd.h>

int main()
{
	char *letter;
	sem_t sem_producer, sem_consumer;
	sem_producer = sem_init(1577);
	sem_consumer = sem_init(1578);
	
	for (int i = 97; i <= 122; i++)
	{
		PS(sem_consumer);
		letter = (char *) getmem(3577);
		printf("%c ", *letter);
		VS(sem_producer);
	}
	printf("\nConsumer process ended.\n");
	sem_remove(sem_producer);
	sem_remove(sem_consumer);
}

