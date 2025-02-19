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

int main(void) {

   key_t key;
   int *buff;
   int shmid;
   int symbol;
   key=ftok(".", 1);
   shmid=shmget(key,SHMSIZE,IPC_CREAT | 0755);
   buff = shmat(shmid, NULL, 0);
   *buff = 0;
   pid_t pid;
   pid = fork();

  if (pid == -1) {
    perror("fork");
    exit(EXIT_FAILURE);
  } else if (pid == 0) {
      for (int i = 0; i < 1000000; i++) {
          int a = *buff;
          usleep(1);
          a++;
          usleep(1);
          *buff = a;
      }
      shmdt(buff);
     exit(0);
  } else {
      for (int i = 0; i < 1000000; i++) {
          int a = *buff;
          usleep(1);
          a--;
          usleep(1);
          *buff = a;
      }
        wait(NULL);
        printf("%d", *buff);
       shmdt(buff);
      shmctl(shmid, IPC_RMID, NULL);
  }
  return 0;
}