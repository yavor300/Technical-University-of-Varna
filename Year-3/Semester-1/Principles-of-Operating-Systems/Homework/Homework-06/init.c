#include <stdio.h>
#include <shared.h>
#include <unistd.h>

int main()
{
	sem_t sem1, sem2, sem3, sem4;
	sem1 = sem_init(1111);
	sem2 = sem_init(2222);
	sem3 = sem_init(3333);
	sem4 = sem_init(4444);
	sem_set(sem1, 1);
	sem_set(sem2, 0);
	sem_set(sem3, 0);
	sem_set(sem4, 1);
}

