#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>

#define BLKSIZE 1

int main(int argc, char *argv[])
{

  int readFd;
  int writeFd;
  int bytesCount;
  char buffer[BLKSIZE];

  if (argc != 3)
  {
    printf("Two arguments to the program expected!\n");
    exit(EXIT_FAILURE);
  }

  readFd = open(argv[1], O_RDONLY);
  if (readFd == -1)
  {
    perror("Error while opening for read.");
    exit(EXIT_FAILURE);
  }

  writeFd = open(argv[2], O_WRONLY | O_CREAT | O_TRUNC, 0755);
  if (writeFd == -1)
  {
    perror("Error while opening for write.");
    exit(EXIT_FAILURE);
  }

  while (bytesCount = read(readFd, buffer, BLKSIZE) > 0)
  {
    if (write(writeFd, buffer, BLKSIZE) != bytesCount)
    {
      perror("Error occured while writing!");
      exit(EXIT_FAILURE);
    }
  }

  if (bytesCount == -1)
  {
    perror("Error occured while reading.");
    exit(EXIT_FAILURE);
  }

  if (close(readFd) == -1)
  {
    perror("Error occured while closing.");
    exit(EXIT_FAILURE);
  }

  if (close(writeFd) == -1)
  {
    perror("Error occured while closing.");
    exit(EXIT_FAILURE);
  }

  return 0;
}
