#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/msg.h>

typedef struct msgbuf
{
  long type;
  int mtext;
  int pid;
} message;

int main(void)
{

  message msg;
  msg.type = 1;
  key_t key;
  key = ftok(".", 1);
  int msgid = msgget(key, IPC_CREAT | 0755);

  pid_t pid;
  pid = fork();

  if (pid == -1)
  {
    perror("fork");
    exit(EXIT_FAILURE);
  }
  else if (pid == 0)
  {
    msg.pid = getpid();
    for (int i = 0; i < 10; i++)
    {
      msg.mtext = i;
      msgsnd(msgid, (message *)&msg, sizeof(msg), 0);
    }
    exit(0);
  }
  else
  {
    pid_t pid2;
    pid2 = fork();
    if (pid2 == -1)
    {
      perror("fork");
      exit(EXIT_FAILURE);
    }
    else if (pid2 == 0)
    {
      msg.pid = getpid();
      for (int i = 10; i < 20; i++)
      {
        msg.mtext = i;
        msgsnd(msgid, (message *)&msg, sizeof(msg), 0);
      }
      exit(0);
    }
    else
    {
      for (int i = 0; i < 20; i++)
      {
        msgrcv(msgid, (message *)&msg, sizeof(msg), 1, 0);
        printf("Message received: %d\n", msg.mtext);
        printf("Child PID: %d\n", msg.pid);
      }
      wait(NULL);
      wait(NULL);
      exit(0);
    }
  }
  return 0;
}
