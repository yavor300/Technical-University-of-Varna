### Example 01

FIFO Creation and Opening:

In calculator.c, a FIFO named "myfifo" is created using mkFifo() and then opened for reading (O_RDONLY). This process will block, i.e., wait at the open() call until another process opens the same FIFO for writing.
In client.c, after the input arguments are processed, it opens the same FIFO ("myfifo") but for writing (O_WRONLY). This open() call on the client side will succeed as soon as the FIFO is opened for reading by the calculator.c, thereby establishing a connection.
Writing to FIFO:

The client (client.c) writes the operands and the operator to the FIFO. This includes writing the first integer, the operator string, and the second integer. The client then closes the write-end of the FIFO.
The server (calculator.c) begins to read data as soon as it's available. It reads the first integer, then attempts to read the operator. Because of the blocking nature of the FIFO reads, calculator.c will block and wait for data to be available each time it issues a read.
Reading from FIFO and Writing Results:

After processing the operation, calculator.c reopens the FIFO for writing (again, this open operation will block until a client opens it for reading). It then writes the result back into the FIFO and closes it.
Meanwhile, client.c reopens the FIFO for reading to get the result. It reads the result as soon as calculator.c writes it and closes the FIFO after reading.
