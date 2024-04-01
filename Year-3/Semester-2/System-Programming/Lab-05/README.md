### Example 01

`memset(&sa, 0, sizeof(sa));` initializes all bytes of the struct sigaction sa to 0.<br>
This is a common technique in C programming to ensure that all fields of a structure are<br>
initialized to a known value before the structure is used. In the context of setting up<br>
signal handling with sigaction, initializing the struct sigaction to zero is particularly<br>
important because it ensures that all unused flags and fields within the structure are set to 0,<br>
preventing unexpected behavior due to uninitialized values.

![Result of Example 01](./screenshots/image.png)

### Example 02
![Result of Example 02](./screenshots/image-2.png)

### Example 03
![Result of Example 03](./screenshots/image-3.png)

### Task 02
![Result of Task 02](./screenshots/image-4.png)

### Task 03
![Result of Task 02](./screenshots/image-5.png)
