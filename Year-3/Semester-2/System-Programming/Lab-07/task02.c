#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <string.h>

int main(void)
{
  float numbers[10] = {14.0, 9.0, 11.0, 7.0, 3.0, 5.0, 4.0, 3.0, 7.0, 1.0};
  float numbers2[10] = {10.0, 5.0, 1.0, 3.0, 2.0, 5.0, 4.0, 8.0, 2.0, 1.0};

  mkfifo("myfifo", 0755);
  pid_t pid;
  pid = fork();

  if (pid == -1)
  {
    perror("fork");
    exit(EXIT_FAILURE);
  }
  else if (pid == 0)
  {
    int fd = open("myfifo", O_WRONLY);
    printf("Starting to write numbers from first process.\n");
    for (int i = 0; i < 10; i++)
    {
      write(fd, &numbers[i], sizeof(float));
    }
    close(fd);
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
      int fd = open("myfifo", O_WRONLY);
      printf("Starting to write numbers from second process.\n");
      for (int i = 0; i < 10; i++)
      {
        write(fd, &numbers2[i], sizeof(float));
      }
      close(fd);
      exit(0);
    }
    else
    {
      printf("Parent waiting for children to write...\n");
      int fd = open("myfifo", O_RDONLY);
      wait(NULL); // Wait for the first child
      wait(NULL); // Wait for the second child
      printf("Starting to print numbers from parent process.\n");
      float num;
      for (int i = 0; i < 20; i++)
      { // Read 20 floats now
        if (read(fd, &num, sizeof(float)) > 0)
          printf("%f\n", num);
      }
      close(fd);
      unlink("myfifo"); // Clean up FIFO
      exit(0);
    }
  }
  return 0;
}