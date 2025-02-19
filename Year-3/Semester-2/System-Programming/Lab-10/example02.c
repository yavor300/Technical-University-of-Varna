#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdlib.h>
#define SHMSIZE 1

int main()
{
  pid_t pid;
  key_t key;
  int shmid;
  char *buff;
  char symbol;
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
    buff = shmat(shmid, NULL, 0);
    for (symbol = 'a'; symbol <= 'z'; symbol++)
    {
      usleep(50000);
      *buff = symbol;
      usleep(50000);
    }
    shmdt(buff);
  }
  else if (pid > 0)
  {
    int i;
    key = ftok(".", 1);
    shmid = shmget(key, SHMSIZE, IPC_CREAT | 0755);
    buff = shmat(shmid, NULL, 0);
    for (i = 0; i <= 'z' - 'a'; i++)
    {
      usleep(50000);
      symbol = *buff;
      printf("Received symbol: %c\n", symbol);
      usleep(50000);
    }
    shmdt(buff);
    wait(NULL);
    shmctl(shmid, IPC_RMID, NULL);
  }

  return 0;
}
