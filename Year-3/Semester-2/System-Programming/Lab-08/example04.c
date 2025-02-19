#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>

int main()
{

  int fd1[2];
  pipe(fd1);
  pid_t pid1;
  pid1 = fork();

  if (pid1 == -1)
  {
    perror("fork");
    exit(EXIT_FAILURE);
  }
  else if (pid1 == 0)
  {
    close(fd1[0]);
    close(STDOUT_FILENO);
    dup2(fd1[1], STDOUT_FILENO);
    close(fd1[1]);
    execlp("ls", "ls", "-la", "/", NULL);
  }
  else if (pid1 > 0)
  {

    pid_t pid2;
    int fd2[2];
    wait(NULL);
    pipe(fd2);
    pid2 = fork();

    if (pid2 == -1)
    {
      perror("fork");
      exit(EXIT_FAILURE);
    }
    else if (pid2 == 0)
    {
      close(fd1[1]);
      close(STDIN_FILENO);
      dup2(fd1[0], STDIN_FILENO);
      close(fd1[0]);

      close(fd2[0]);
      close(STDOUT_FILENO);
      dup2(fd2[1], STDOUT_FILENO);
      close(fd2[1]);

      execlp("grep", "grep", "a", NULL);
    }
    else if (pid2 > 0)
    {
      close(fd1[0]);
      close(fd1[1]);
      close(fd2[1]);
      close(STDIN_FILENO);
      dup2(fd2[0], STDIN_FILENO);
      close(fd2[0]);
      wait(NULL);
      execlp("wc", "wc", "-l", NULL);
    }
  }

  return 0;
}
