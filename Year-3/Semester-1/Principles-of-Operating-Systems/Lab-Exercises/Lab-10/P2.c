#include <stdio.h>
#include <shared.h>

int main() {



	typedef struct shared_mem {
		volatile int counter;
		volatile int lock;
	} memory;


	memory *data;
	int i, a;
	data = (memory *)getmem(1577);
	
	for (i = 1; i <= 1000000; i++)
	{
		while (testandset(&(data->lock)));
		a = data->counter;
		a = a - 1;
		data->counter = a;
		data->lock = 0;
	}
	printf("P2 process ended.\n");
}
