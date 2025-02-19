#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdlib.h>
#define SHMSIZE 4

int main()
{
  pid_t pid;
  key_t key;
  int shmid;
  int *ptr;
  pid = fork();

  if (pid == -1)
  {
    perror("fork");
    exit(EXIT_FAILURE);
  }
  else if (pid == 0)
  {
    key = ftok(".", 1);
    shmid = shmget(key, SHMSIZE, IPC_CREAT | 0755);
    ptr = shmat(shmid, NULL, 0);
    *ptr = 123;
    shmdt(ptr);
  }
  else if (pid > 0)
  {
    key = ftok(".", 1);
    wait(NULL);
    shmid = shmget(key, SHMSIZE, 0755);
    ptr = shmat(shmid, NULL, 0);
    printf("Shared memory value: %d\n", *ptr);
    shmdt(ptr);
    shmctl(shmid, IPC_RMID, NULL);
  }
  
  return 0;
}
