@ Да се напише подпрограма, която да връща в регистър R0 индекса на подадената
@ пак там дума в сортирания във възходящ ред масив от думи със знак с начален
@ адрес, записан в R1 и брой елементи, записан в R2.
@ Ако не е намерено съвпадение, в R0 се записва -1.

.global  FindElementIndex
FindElementIndex:
  LDR R1,=NUMBERS  @ Начален адрес на масива
  MOV R2,#9        @ Последен индекс на масива
  MOV R3,#11       @ Числото (думата), което търсим
  MOV R0,#-1       @ Инициализиране на резултата
  MOV R4,#0        @ Първи индекс в масива
  
  loop:
    CMP R4,R2
    BHI not_found
    
    LDR R5,[R1,R4,LSL #2]
    CMP R3,R5
    BEQ found
    
    ADD R4,R4,#1
    B loop
  not_found:
    BX LR
  
  found:
    MOV R0,R4
    BX LR

.data
  NUMBERS: .word 1,3,5,7,9,11,13,15,17,19

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@@@ OPEN INPUT FILE, READ INTEGER FROM FILE, PRINT IT, CLOSE INPUT FILE
  .equ SWI_Open, 0x66   @ open a file
  .equ SWI_Close,0x68   @ close a file
  .equ SWI_PrChr,0x00   @ Write an ASCII char to Stdout
  .equ SWI_PrStr, 0x69   @ Write a null-ending string
  .equ SWI_PrInt,0x6b   @ Write an Integer
  .equ SWI_RdInt,0x6c   @ Read an Integer from a file
  .equ Stdout, 1     @ Set output target to be Stdout
  .equ SWI_Exit, 0x11   @ Stop execution
  .global _start
  .text  
_start:


StartingAddress:

  mov  R4,#1000

  mov R0, #Stdout   @ prind a message for end of read
  ldr R1, =BeginAddress
  swi SWI_PrStr

  mov R0,#Stdout     @ print an initial message
  ldr R1, =inR0    @ load address of Message1 label
  swi SWI_PrStr     @ display message to Stdout

  mov r1,r4     @ R1 = integer to print
  mov R0,#Stdout     @ target is Stdout
  swi SWI_PrInt

  mov R0,#Stdout     @ print Colon
  ldr r1, =CRLF
  swi SWI_PrStr

  mov  r0,r4
  bl  sequence  @ coll user subroutine

  mov R0, #Stdout   @ prind a message for end of read
  ldr R1, =Output
  swi SWI_PrStr


  mov r10, #6    @ read counter

ReadLoop:

@ from memory
  ldr  r2,[r4],#4    @read S[i] from memory

@ print the integer to Stdout
  mov r1,r2      @ R1 = integer to print
  mov R0,#Stdout     @ target is Stdout
  swi SWI_PrInt


@ check of read numbers
  subs r10, r10, #1
  beq  Exit    

  mov R0,#Stdout     @ print new line
  ldr r1, =CRLF
  swi SWI_PrStr
  bal ReadLoop     @ keep reading till end of file


Exit:
  Swi SWI_Exit @ stop executing


      .data
      .align
@InFileHandle:    .skip 4
@InFileHandle:    .word 0
@InFileName:    .asciz "input integer.txt"
@FileOpenInpErrMsg:  .asciz "Failed to open input file \n"
@EndOfFileMsg:    .asciz "End of file reached! Not enough number!\n"
BeginAddress:    .asciz "Begin address:\n"
Output:      .asciz "Order of Silvester:\n\n"
comma:      .asciz ", "   @ comma
Colon:      .asciz ":"   @colon - двуеточие
R3R2:      .asciz " R3:R2 = "
inR0:      .asciz "\n R0 = "
CRLF:      .asciz "\n\n"
      .end








@ == Open an input file for reading =============================
@ if problems, print message to Stdout and exit
  ldr r0,=InFileName   @ set Name for input file
  mov r1,#0     @ mode is input
  swi SWI_Open     @ open file for input
  bcs InFileError   @ Check Carry-Bit (C): if= 1 then ERROR
@ Save the file handle in memory:
  ldr r1,=InFileHandle   @ if OK, load input file handle
  str r0,[r1]     @ save the file handle

@ == Read integers until end of file =============================
RLoop:

@ check of read numbers
  subs r10, r10, #1
  beq  ReadNumbers    

  ldr r0,=InFileHandle   @ load input file handle
  ldr r0,[r0]
  swi SWI_RdInt     @ read the integer into R0
  bcs EofReached     @ Check Carry-Bit (C): if= 1 then EOF reached

@ to stack
  stmfd r13!, {r0}

  bal RLoop     @ keep reading till end of file

@ == End of file ===============================================
EofReached:
  mov R0, #Stdout   @ print last message
  ldr R1, =EndOfFileMsg
  swi SWI_PrStr

@ == Close a file ===============================================
  ldr R0, =InFileHandle   @ get address of file handle
  ldr R0, [R0]     @ get value at address
  swi SWI_Close
  bal Exit     @ go to end

ReadNumbers:
@  mov R0, #Stdout   @ prind a message for end of read
@  ldr R1, =EnoughNumbers
@  swi SWI_PrStr

@ == Close a file ===============================================
  ldr R0, =InFileHandle   @ get address of file handle
  ldr R0, [R0]     @ get value at address
  swi SWI_Close



@ == Read integers from stack =============================

@  ldmfd r13!, {r4, r5, r6, r7}
  ldmfd r13!, {r4, r5}


@ R3:R2

@  mov R0,#Stdout     @ print an initial message
@  ldr R1, =R3R2    @ load address of Message1 label
@  swi SWI_PrStr     @ display message to Stdout
@
@  mov r1,r7     @ R1 = integer to print
@  mov R0,#Stdout     @ target is Stdout
@  swi SWI_PrInt
@
@  mov R0,#Stdout     @ print colon
@  ldr r1, =Colon
@  swi SWI_PrStr
@
@  mov r1,r6     @ R1 = integer to print
@  mov R0,#Stdout     @ target is Stdout
@  swi SWI_PrInt

@ R1:R0

@  mov R0,#Stdout     @ print an initial message
@  ldr R1, =R1R0    @ load address of Message1 label
@  swi SWI_PrStr     @ display message to Stdout

  mov r1,r5     @ R1 = integer to print
  mov R0,#Stdout     @ target is Stdout
  swi SWI_PrInt

  mov R0,#Stdout     @ print Colon
  ldr r1, =Colon
  swi SWI_PrStr

  mov r1,r4     @ R1 = integer to print
  mov R0,#Stdout     @ target is Stdout
  swi SWI_PrInt



@@@@@@@@@@@@@@@@@@



  mov r0,r4 
  mov r1,r5
@  mov r2,r6 
@  mov r3,r7 

@  bl  sequence  @ coll user subroutine

@  stmfa r13!,{r0,r1}
  stmfa r13!,{r0}


@  mov R0,#Stdout     @print an initial message
@  ldr R1, =outputR1R0    @ load address of Message1 label
@  swi SWI_PrStr     @ display message to Stdout

@  mov r10, #2    @ load read counter
  mov r10, #1    @ load read counter

dLoop:

@ from stack
  ldmfa r13!, {r0}

@ print the integer to Stdout
  mov r1,r0     @ R1 = integer to print
  mov R0,#Stdout     @ target is Stdout
  swi SWI_PrInt

@ check of read numbers
  subs r10, r10, #1
  beq  Exit    

  mov R0,#Stdout     @ print new line
  ldr r1, =Colon
  swi SWI_PrStr
  bal dLoop     @ keep reading till end of file

Exit:
  Swi SWI_Exit @ stop executing
InFileError:
  mov R0, #Stdout
  ldr R1, =FileOpenInpErrMsg
  swi SWI_PrStr
  bal Exit @ give up, go to end


      .data
      .align
@InFileHandle:    .skip 4
InFileHandle:    .word 0
InFileName:    .asciz "input integer.txt"
FileOpenInpErrMsg:  .asciz "Failed to open input file \n"
EndOfFileMsg:    .asciz "End of file reached! Not enough number!\n"
BeginAddress:    .asciz "Begin address:\n"
Output:      .asciz "Order of Sylvester:\n\n"
comma:      .asciz ", "   @ comma
Colon:      .asciz ":"   @colon - двуеточие
R3R2:      .asciz " R3:R2 = "
inR0:      .asciz "\n R0 = "
CRLF:      .asciz "\n\n"
      .end
