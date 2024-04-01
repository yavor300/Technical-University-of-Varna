#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <time.h>

int main()
{

  struct stat info;
  if (stat("myfile", &info) == -1)
  {
    perror("stat");
    exit(EXIT_FAILURE);
  }

  printf("File type: ");

  switch (info.st_mode & S_IFMT)
  {
    case S_IFBLK:
      printf("Block device\n");
      break;
    case S_IFCHR:
      printf("Character device\n");
      break;
    case S_IFDIR:
      printf("Directory\n");
      break;
    case S_IFIFO:
      printf("Pipe\n");
      break;
    case S_IFLNK:
      printf("Symbolic link\n");
      break;
    case S_IFREG:
      printf("Regular file\n");
      break;
    case S_IFSOCK:
      printf("Socket\n");
      break;
    default:
      break;
  }

  printf("I-node number: %d\n", info.st_ino);
  printf("Mode: %o\n", info.st_mode);
  printf("Links count: %d\n", info.st_nlink);
  printf("File size: %d bytes.\n", info.st_size);
  printf("Blocks allocated: %d\n", info.st_blocks);
  printf("File status changed: %s", ctime(&info.st_ctime));
  printf("File accessed: %s", ctime(&info.st_atime));
  printf("File modified: %s", ctime(&info.st_mtime));

  return 0;
}
