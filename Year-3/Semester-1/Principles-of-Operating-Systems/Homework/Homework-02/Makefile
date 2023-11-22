
CC=gcc

all: sort_program

sort_program: main.o input.o sort.o output.o
	$(CC) -o sort_program main.o input.o sort.o output.o

main.o: main.c
	$(CC) -c main.c

input.o: input.c
	$(CC) -c input.c

sort.o: sort.c
	$(CC) -c sort.c

output.o: output.c
	$(CC) -c output.c

clean:
	rm -f *.o sort_program

run:
	./sort_program