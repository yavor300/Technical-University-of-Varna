#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main(int argc, char *argv[], char *envp[])
{
  printf("Starting process with pid: %d\n", getpid());
  char* args[] = { "bash", "./script.sh", NULL};
  char* envs[] = { "MYVAR=Yavor" };
  execve("/bin/bash", args, envs);
  return 0;
}
