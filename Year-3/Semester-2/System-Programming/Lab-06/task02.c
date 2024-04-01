#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>

int main(void)
{

  int writeFd, resFd;
  writeFd = open("f", O_WRONLY | O_CREAT | O_TRUNC, 0755);
  if (writeFd == -1)
  {
    perror("Error while opening for write.");
    exit(EXIT_FAILURE);
  }
  if (dup2(writeFd, STDOUT_FILENO) == -1) {
    perror("dup2 failed");
    exit(EXIT_FAILURE);
  }

  pid_t pid, pid2;
  pid = fork();
  if (pid == -1)
  {
    perror("fork");
    exit(EXIT_FAILURE);
  } 
  else if (pid == 0)
  {
    pid2 = fork();
    if (pid2 == -1) 
    { 
      perror("fork");
      exit(EXIT_FAILURE);
    }
    else if (pid2 == 0) 
    {
      execl("/usr/bin/ls", "ls", NULL);
      perror("execl");
      exit(EXIT_FAILURE);
    }
    else
    {
      wait(NULL);
      resFd = open("res", O_WRONLY | O_CREAT | O_TRUNC, 0755);
      if (resFd == -1)
      {
        perror("Error while opening 'res' for write.");
        exit(EXIT_FAILURE);
      }
      if (dup2(resFd, STDOUT_FILENO) == -1)
      {
        perror("dup2 failed");
        exit(EXIT_FAILURE);
      }
      execl("/usr/bin/wc", "wc", "-w", "f", NULL);
      perror("execl");
      exit(EXIT_FAILURE);
    }
  }
  else 
  {
    wait(NULL);
  }

  if (close(writeFd) == -1)
  {
    perror("Failed to close the file");
    exit(EXIT_FAILURE);
  }

  return 0;
}
