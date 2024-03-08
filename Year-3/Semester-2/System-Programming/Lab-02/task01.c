#include <stdio.h>
#include <unistd.h>

int main(void)
{
    printf("PID: %d, PPID: %d\n", getpid(), getppid());
    return 0;
}
