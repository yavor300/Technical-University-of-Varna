### Example 01
File descriptors or file handles are integer identifiers that specify data structures.<br>
The Linux kernel refers to these structures as file structs since they describe open files.<br>
The index of a file struct is the file handle.<br>
In Linux, each process has its own set of open files.<br>
Hence, each process also has its own table of file structs that the kernel keeps track of.<br>
Notably, each file struct is allocated in kernel memory.<br>
As such, the kernel can only allocate so many file structs until the system runs out of memory.<br>

![Result of Example 01](./screenshots/image.png)

### Example 02
`info.st_mode & S_IFMT`: The bitwise AND operation is used here to mask out the file<br>
type bits from st_mode. The result of this operation is a value that represents<br>
the file type, which can then be compared against other constants<br>
(like S_IFREG for regular files, S_IFDIR for directories, etc.) to determine<br>
the type of the file.

![Result of Example 02](./screenshots/image-2.png)

### Task 01

When you execute `dup2(writeFd, STDOUT_FILENO)`, it does the following:

- Closes `STDOUT_FILENO` if Necessary: If `STDOUT_FILENO` is already open (which it almost<br>
always is, as it's opened by default for any process), dup2 will close it before<br> duplicating. This step ensures that the standard output stream is ready to be replaced<br> without leaking file descriptors.

- Duplicates `writeFd` onto `STDOUT_FILENO`: It makes STDOUT_FILENO refer to the same open<br>
file table entry as writeFd. This means any write operations that would normally go to the<br>
standard output now go to the file writeFd is associated with.

- Result: After this operation, any output (e.g., from printf, puts, or any other library<br>
function that writes to stdout) will go directly to the file you opened and referred to by<br>
writeFd instead of appearing on the terminal.


`saveStdout` is used to save the original STDOUT_FILENO before any redirection.<br>
Later, `dup2(saveStdout, STDOUT_FILENO)` is used to restore stdout to its original state. 

![Result of Task 01](./screenshots/image-3.png)

### Task 02

![Result of Task 02](./screenshots/image-4.png)
