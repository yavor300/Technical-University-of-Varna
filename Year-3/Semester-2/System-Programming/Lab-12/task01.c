#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/sem.h>
#include <sys/shm.h>
#include <sys/wait.h>
#define SHMSIZE 4

int main(void) {

  struct semun {
      int val;
      struct semid_ds *buf;
      unsigned int *array;
  };

   key_t key;
   int *buff;
   int shmid;
   int semid;
   int symbol;
   key=ftok(".", 1);
   shmid = shmget(key, SHMSIZE, IPC_CREAT | 0755);
   semid = semget(key, 1, IPC_CREAT | 0755);
   semctl(semid, 0, SETVAL, 1);
   
   struct sembuf popCS;
   popCS.sem_num = 0;
   popCS.sem_op = -1;
   popCS.sem_flg = 0;
   
   struct sembuf vopCS;
   vopCS.sem_num = 0;
   vopCS.sem_op = 1;
   vopCS.sem_flg = 0;
   
   pid_t pid;

printf("Before fork");
   pid = fork();


  if (pid == -1) {
    perror("fork");
    exit(EXIT_FAILURE);
  } else if (pid == 0) {
      for (int i = 0; i < 1000000; i++) {
	printf("Incrementing");
          semop(semid, &popCS, 1);
         int a = *buff;
         usleep(1);
         a++;
         usleep(1);
         *buff = a;
         semop(semid, &vopCS, 1);
	printf("Incrementing Done");
      }
      shmdt(buff);
     exit(0);
  } else {
      for (int i = 0; i < 1000000; i++) {
printf("Decrementing");
          semop(semid, &popCS, 1);
          int a = *buff;
          usleep(1);
          a--;
          usleep(1);
          *buff = a;
          semop(semid, &vopCS, 1);
printf("Decrementing Done");
      }
        wait(NULL);
        printf("%d", *buff);
       shmdt(buff);
      shmctl(shmid, IPC_RMID, NULL);
  }
  return 0;
}
