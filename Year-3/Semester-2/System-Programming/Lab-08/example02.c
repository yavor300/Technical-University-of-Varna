#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <string.h>

int main()
{

  int fd[2];
  pipe(fd);
  pid_t pid;
  pid = fork();

  if (pid == -1)
  {
    perror("fork");
    exit(EXIT_FAILURE);
  }
  else if (pid == 0)
  {
    int i;
    char *strs[] = {"bbb\n", "aaa\n", "ccc\n"};
    close(fd[0]);
    for (int i = 0; i < 3; i++)
    {
      write(fd[1], strs[i], strlen(strs[i]) * sizeof(char));
    }
  } else if (pid > 0) {
    close(fd[1]);
    close(STDIN_FILENO);
    dup2(fd[0], STDIN_FILENO);
    close(fd[0]);
    execlp("sort", "sort", NULL);
    wait(NULL);
  }

  return 0;
}
