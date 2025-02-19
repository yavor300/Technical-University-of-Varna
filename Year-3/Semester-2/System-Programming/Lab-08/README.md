### Example 01

Synchronization Details

Blocking Behavior: The synchronization between the read and write operations relies on the blocking behavior of the read() and write() calls. The read() in the child will block until there is data to read, ensuring the child only processes data after the parent has written it. Similarly, write() might block if the pipe's buffer is full (not typically the case for small writes).

Data Consistency: Since the pipe is a byte-stream mechanism without built-in message boundaries, ensuring that complete messages (like an int in this case) are written and read as intended is crucial. Proper closing of file descriptors helps manage the flow of data and signals between the processes.