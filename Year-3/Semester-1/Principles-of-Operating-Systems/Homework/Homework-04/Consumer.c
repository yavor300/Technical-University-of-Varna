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
	data = (memory *)getmem(3577);
	int i;

	for (i = 97; i <= 122; i++)
	{
		while(testandset(&data->lock));		
		printf("%c ",data->letter);
		data->lock=0;
		sleep(1);
	}
	printf("\nConsumer process ended.\n");
}

