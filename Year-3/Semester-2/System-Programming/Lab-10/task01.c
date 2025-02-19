#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/shm.h>
#define SHMSIZE 4

int main(void)
{

  key_t key;
  int *buff;
  int shmid;
  key = ftok(".", 1);
  shmid = shmget(key, SHMSIZE, IPC_CREAT | 0755);
  buff = shmat(shmid, NULL, 0);
  *buff = 10;
  pid_t pid;
  pid = fork();

  if (pid == -1)
  {
    perror("fork");
    exit(EXIT_FAILURE);
  }
  else if (pid == 0)
  {
    for (int i = 0; i < 100; i++)
    {
      usleep(1);
      int a = *buff;
      a++;
      *buff = a;
      usleep(1);
    }
    shmdt(buff);
  }
  else
  {
    for (int i = 0; i < 100; i++)
    {
      usleep(1);
      int a = *buff;
      a--;
      *buff = a;
      usleep(1);
    }
    printf("%d", *buff);
    wait(NULL);
    shmdt(buff);
    shmctl(shmid, IPC_RMID, NULL);
  }
  return 0;
}
