#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main(int argc, char *argv[], char *envp[])
{
  printf("Starting process with pid: %d\n", getpid());
  while (*envp) {
    printf("%s\n", *envp);
    envp++;
  }
  return 0;
}
