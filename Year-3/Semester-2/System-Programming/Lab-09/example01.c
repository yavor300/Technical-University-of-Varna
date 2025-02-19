#include <stdio.h>
#include <unistd.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <stdlib.h>
#include <string.h>

typedef struct msgbuf
{
  long mtype;
  char mtext[100];
  int pid;
} message;

int main()
{
  pid_t pid;
  pid = fork();
  key_t key;

  if (pid == -1)
  {
    perror("fork");
    exit(EXIT_FAILURE);
  }
  else if (pid == 0)
  {
    message msg;
    msg.mtype = 1;
    msg.pid = getpid();
    strcpy(msg.mtext, "Hello");
    key = ftok(".", 1);
    int msgid = msgget(key, IPC_CREAT | 0755);
    msgsnd(msgid, (message *)&msg, sizeof(msg), 0);
  }
  else if (pid > 0)
  {
    message msg;
    key_t key = ftok(".", 1);
    int msgid = msgget(key, IPC_CREAT | 0755);
    msgrcv(msgid, (message *)&msg, sizeof(msg), 1, 0);
    printf("Message received: %s\n", msg.mtext);
    printf("Child PID: %d\n", msg.pid);
  }
}
