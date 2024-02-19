#include <stdio.h>
#include <shared.h>
#include <unistd.h>

int main()
{

	
	typedef struct shared_mem {
		volatile char letter;
		volatile int lock;
	} memory;

	memory *data;
	int i;

	data=(memory *)getmem(3577);

	for (i = 97; i <= 122; i++)
	{
		while (testandset(&data->lock));
		data->letter = (char)i;
		data->lock = 0;
		sleep(1);
	}
	printf("Producer process ended.\n");
}


